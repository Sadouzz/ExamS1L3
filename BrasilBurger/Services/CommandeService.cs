using System.Linq;
using System.Collections.Generic;
using Models;
using Data;
using Microsoft.EntityFrameworkCore;

namespace Services
{
    public class CommandeService
    {
        private readonly AppDbContext _db;

        public CommandeService(AppDbContext db)
        {
            _db = db;
        }

        public Commande GetById(long id)
        {
            return _db.Commandes
            .Where(c => c.Id == id)
                          .Include(c => c.CommandeItems)
                          .ThenInclude(ci => ci.Burger)
                          .Include(c => c.CommandeItems)
                          .ThenInclude(ci => ci.Menu)
                          .Include(c => c.CommandeItems)
                          .ThenInclude(ci => ci.Complement)
                          .FirstOrDefault();
        }

        public List<Commande> GetByUserId(long id)
        {
            return _db.Commandes
            .Where(c => c.ClientId == id)
                          .Include(c => c.CommandeItems)
                          .ThenInclude(ci => ci.Burger)
                          .Include(c => c.CommandeItems)
                          .ThenInclude(ci => ci.Menu)
                          .Include(c => c.CommandeItems)
                          .ThenInclude(ci => ci.Complement)
                          .ToList();
        }

        public List<Commande> GetAll()
        {
            return _db.Commandes
                          .Include(c => c.CommandeItems)
                          .ThenInclude(ci => ci.Burger)
                          .Include(c => c.CommandeItems)
                          .ThenInclude(ci => ci.Menu)
                          .Include(c => c.CommandeItems)
                          .ThenInclude(ci => ci.Complement)
                          .ToList();
        }
    }
}
