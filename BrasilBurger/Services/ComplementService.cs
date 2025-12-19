using System.Collections.Generic;
using System.Linq;
using Data;
using Models;
using Microsoft.EntityFrameworkCore;

namespace Services
{
    public class ComplementService
    {
        private readonly AppDbContext _db;

        public ComplementService(AppDbContext db)
        {
            _db = db;
        }

        // Récupérer un complément par Id
        public Complement GetById(long id)
        {
            return _db.Complements
                      .FirstOrDefault(c => c.Id == id && !c.IsArchived);
        }

        // Récupérer tous les compléments non archivés
        public List<Complement> GetAll()
        {
            return _db.Complements
                      .Where(c => !c.IsArchived)
                      .ToList();
        }
    }
}
