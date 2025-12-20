using Microsoft.AspNetCore.Mvc;
using Models;
using Services;
using Data;
using Microsoft.EntityFrameworkCore;
using System;
using System.Linq;
using System.Security.Claims;
using Microsoft.AspNetCore.Mvc.ActionConstraints;

public class CommandeController : Controller
{
    private readonly BurgerService _burgerService;
    private readonly MenuService _menuService;
    private readonly ComplementService _complementService;
    private readonly QuartierService _quartierService;
    private readonly ZoneService _zoneService;
    private readonly CommandeService _commandeService;
    private readonly AppDbContext _db;

    public CommandeController(
        BurgerService burgerService,
        MenuService menuService,
        ComplementService complementService,
        QuartierService quartierService,
        ZoneService zoneService,
        CommandeService commandeService,
        AppDbContext db)
    {
        _burgerService = burgerService;
        _menuService = menuService;
        _complementService = complementService;
        _quartierService = quartierService;
        _zoneService = zoneService;
        _commandeService = commandeService;
        _db = db;
    }

    [HttpGet]
    public IActionResult Commander(string type, long id)
    {
        ViewBag.Type = type;

        if (type == "burger")
        {
            var burger = _burgerService.GetById(id);
            if (burger == null) return NotFound();
            ViewBag.Item = burger;
            ViewBag.ItemName = burger.Libelle;  
            ViewBag.ItemId = burger.Id;         
            return RedirectToAction("ChoixComplements", new { id });
        }
        else if (type == "menu")
        {
            var menu = _menuService.GetById(id);
            if (menu == null) return NotFound();
            ViewBag.Item = menu;
            ViewBag.ItemName = menu.Libelle;
            ViewBag.ItemId = menu.Id;       
            return RedirectToAction("InfosLivraison", new { type = "menu", itemId = id });
        }
        else
        {
            return BadRequest("Type d'article inconnu");
        }
    }

    [HttpGet]
    public IActionResult ChoixComplements(long id)
    {
        var burger = _burgerService.GetById(id);
        if (burger == null) return NotFound();

        ViewBag.Burger = burger;
        ViewBag.Complements = _complementService.GetAll();

        return View();
    }

    [HttpPost, HttpGet]
    public IActionResult InfosLivraison(
    string type,
    long itemId,
    long[] selectedComplements)
    {
        ViewBag.Type = type;
        ViewBag.ItemId = itemId;

        List<Complement> complements = new List<Complement>();

        if (selectedComplements != null)
        {
            complements = selectedComplements
                .Select(id => _complementService.GetById(id))
                .Where(c => c != null)
                .ToList();
        }

        ViewBag.SelectedComplements = complements;
        object mainItem;
        if(type == "menu")
        {
            mainItem = _menuService.GetById(itemId);
        }
        else
        {
            mainItem = _burgerService.GetById(itemId);
        }
        ViewBag.Quartiers = _quartierService.GetAll();
        ViewBag.MainItem = mainItem;

        return View();
    }

    [HttpPost]
    public IActionResult Create(string type, long itemId, TypeRetrait typeRetrait, long quartier, int quantite = 1, long[] selectedComplements = null, string adresse = "")
    {
        var userIdClaim = User.FindFirst(ClaimTypes.NameIdentifier);
        var userId = long.Parse(userIdClaim.Value);
        var commande = new Commande
        {
            Adresse = adresse,
            CreatedAt = DateTime.UtcNow,
            UpdatedAt = DateTime.UtcNow,
            IsPaid = false,
            Statut = StatutCommande.EN_ATTENTE,
            TypeRetrait = typeRetrait,
            QuartierId = quartier,
            ClientId = userId
        };

        _db.Commandes.Add(commande);
        _db.SaveChanges();

        double total = 0;

        if (type == "burger")
        {
            var burger = _burgerService.GetById(itemId);
            var item = new CommandeItem
            {
                CommandeId = commande.Id,
                BurgerId = burger.Id,
                Quantite = quantite,
                PrixTotal = burger.Prix * quantite
            };
            total += item.PrixTotal;
            _db.CommandeItems.Add(item);
        }
        else if (type == "menu")
        {
            var menu = _menuService.GetById(itemId);
            var item = new CommandeItem
            {
                CommandeId = commande.Id,
                MenuId = menu.Id,
                Quantite = quantite,
                PrixTotal = menu.Prix * quantite
            };
            total += item.PrixTotal;
            _db.CommandeItems.Add(item);
        }

        if (selectedComplements != null)
        {
            foreach (var compId in selectedComplements)
            {
                var comp = _complementService.GetById(compId);
                var item = new CommandeItem
                {
                    CommandeId = commande.Id,
                    ComplementId = comp.Id,
                    Quantite = 1,
                    PrixTotal = comp.Prix
                };
                total += comp.Prix;
                _db.CommandeItems.Add(item);
            }
        }

        commande.MontantHorsLivraison = total;
        double taxes = 0;
        if (typeRetrait == TypeRetrait.LIVRAISON)
        {
            taxes = _zoneService.GetById(_quartierService.GetById(quartier).ZoneId).PrixLivraison;
        }
        commande.MontantTotal = total + taxes;

        _db.SaveChanges();

        return RedirectToAction("Payer", "Paiement", new { id = commande.Id });

    }

    public IActionResult Annuler(long id)
    {
        var commande = _commandeService.GetById(id);

        if (commande == null)
            return NotFound();

        commande.Statut = StatutCommande.ANNULEE;
        _db.SaveChanges();

        return RedirectToAction("UserCommandes");
    }


    public IActionResult UserCommandes()
    {
        var userIdClaim = User.FindFirst(ClaimTypes.NameIdentifier);
        var userId = long.Parse(userIdClaim.Value);
        var commandes = _commandeService.GetByUserId(userId);

        ViewBag.Commandes = commandes;
        return View();
    }
}
