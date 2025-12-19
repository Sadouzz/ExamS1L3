using System.Linq;
using System.Collections.Generic;
using Models;
using Data;
using Microsoft.EntityFrameworkCore;

namespace Services
{
    public class BurgerService
    {
        private readonly AppDbContext _db;

        public BurgerService(AppDbContext db)
        {
            _db = db;
        }

        public Burger GetById(long id)
        {
            return _db.Burgers
                      .Include(b => b.BurgerCategorie)
                      .FirstOrDefault(b => b.Id == id && !b.IsArchived);
        }

        public List<Burger> GetAll()
        {
            return _db.Burgers
                      .Include(b => b.BurgerCategorie)
                      .Where(b => !b.IsArchived)
                      .ToList();
        }
    }
}
