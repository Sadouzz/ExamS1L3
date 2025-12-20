using System.Linq;
using System.Collections.Generic;
using Models;
using Data;
using Microsoft.EntityFrameworkCore;

namespace Services
{
    public class QuartierService
    {
        private readonly AppDbContext _db;

        public QuartierService(AppDbContext db)
        {
            _db = db;
        }

        public Quartier GetById(long id)
        {
            return _db.Quartiers
                      .Include(q => q.Zone)
                      .FirstOrDefault(q => q.Id == id );
        }

        public List<Quartier> GetAll()
        {
            return _db.Quartiers
                      .Include(q => q.Zone)
                      .ToList();
        }
    }
}
