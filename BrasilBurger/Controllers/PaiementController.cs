using Microsoft.AspNetCore.Mvc;
using Services;
using Models;
using Data;

namespace Controllers
{
    public class PaiementController : Controller
    {
        private readonly PaiementService paiementService;
        private readonly CommandeService commandeService;
        private readonly AppDbContext db;

        public PaiementController(PaiementService _paiementService, CommandeService _commandeService, AppDbContext _db)
        {
            paiementService = _paiementService;
            commandeService = _commandeService;
            db = _db;
        }

        public IActionResult Payer(long ID)
        {
            var commande = commandeService.GetById(ID);

            if (commande == null)
                return NotFound();

            ViewBag.Commande = commande;
            return View();
        }

        [HttpPost]
        public IActionResult PayerPlusTard(long commandeId)
        {
            return RedirectToAction("UserCommandes", "Commande");
        }

        [HttpPost]
        public IActionResult PayerWave(long commandeId)
        {
            CreatePaiement(MoyenPaiement.WAVE, commandeId);
            return RedirectToAction("UserCommandes", "Commande");
        }

        [HttpPost]
        public IActionResult PayerOrangeMoney(long commandeId)
        {
            CreatePaiement(MoyenPaiement.OM, commandeId);
            return RedirectToAction("UserCommandes", "Commande");
        }

        public void CreatePaiement(MoyenPaiement moyenPaiement, long commandeId)
        {
            var commande = commandeService.GetById(commandeId);
            var paiement = new Paiement
            {
                Montant = commande.MontantTotal,
                RefTransaction = RefTransactionGenerator(),
                Date = DateTime.UtcNow,
                CommandeId = commandeId,
                MoyenPaiement = moyenPaiement
            };
            commande.IsPaid = true;
            commande.Statut = StatutCommande.VALIDEE;
            db.Paiements.Add(paiement);
            db.SaveChanges();
        }

        public string RefTransactionGenerator()
        {
            return "RP"+paiementService.GetAll().Count().ToString();
        }


    }
}
