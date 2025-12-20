using System.Collections.Generic;
using System.Linq;
using Data;
using Models;
using Microsoft.EntityFrameworkCore;

namespace Services
{
    public class PaiementService
    {
        private readonly AppDbContext _db;

        public PaiementService(AppDbContext db)
        {
            _db = db;
        }

        public Paiement GetById(long id)
        {
            var paiement = _db.Paiements
                          .FirstOrDefault(m => m.Id == id);

            if (paiement == null) return null;

            return paiement;
        }

        public List<Paiement> GetAll()
        {
            var paiements = _db.Paiements
                           .ToList();

            return paiements;
        }

    }
}
