using Microsoft.AspNetCore.Mvc;
using Models;
using Services;
using Data;
using Microsoft.EntityFrameworkCore;
using System;
using System.Linq;
using System.Security.Claims;

public class CommandeController : Controller
{
    private readonly BurgerService _burgerService;
    private readonly MenuService _menuService;
    private readonly ComplementService _complementService;
    private readonly AppDbContext _db;

    public CommandeController(
        BurgerService burgerService,
        MenuService menuService,
        ComplementService complementService,
        AppDbContext db)
    {
        _burgerService = burgerService;
        _menuService = menuService;
        _complementService = complementService;
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
            return BadRequest("Type d'article inconnu");
        
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
}
