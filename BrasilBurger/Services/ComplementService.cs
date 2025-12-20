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

        public Complement GetById(long id)
        {
            return _db.Complements
                      .FirstOrDefault(c => c.Id == id && !c.IsArchived);
        }

        public List<Complement> GetAll()
        {
            return _db.Complements
                      .Where(c => !c.IsArchived)
                      .ToList();
        }
    }
}
