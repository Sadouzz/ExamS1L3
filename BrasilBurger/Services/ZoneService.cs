using System.Linq;
using System.Collections.Generic;
using Models;
using Data;
using Microsoft.EntityFrameworkCore;

namespace Services
{
    public class ZoneService
    {
        private readonly AppDbContext _db;

        public ZoneService(AppDbContext db)
        {
            _db = db;
        }

        public Zone GetById(long id)
        {
            return _db.Zones
                      .FirstOrDefault(z => z.Id == id);
        }

        public List<Zone> GetAll()
        {
            return _db.Zones
                      .ToList();
        }
    }
}
