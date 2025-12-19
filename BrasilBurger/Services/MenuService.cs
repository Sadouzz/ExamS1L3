using System.Collections.Generic;
using System.Linq;
using Data;
using Models;
using Microsoft.EntityFrameworkCore;

namespace Services
{
    public class MenuService
    {
        private readonly AppDbContext _db;

        public MenuService(AppDbContext db)
        {
            _db = db;
        }

        public Menu GetById(long id)
        {
            var menu = _db.Menus
                          .FirstOrDefault(m => m.Id == id && !m.IsArchived);

            if (menu == null) return null;

            // Inclure les burgers et les complements
            menu.MenuBurgers = _db.MenuBurgers
                                  .Include(mb => mb.Burger)
                                  .Where(mb => mb.MenuId == menu.Id)
                                  .ToList();

            menu.MenuComplements = _db.MenuComplements
                                      .Include(mc => mc.Complement)
                                      .Where(mc => mc.MenuId == menu.Id)
                                      .ToList();

            return menu;
        }

        public List<Menu> GetAll()
        {
            var menus = _db.Menus
                           .Where(m => !m.IsArchived)
                           .ToList();

            var menuIds = menus.Select(m => m.Id).ToList();

            var menuBurgers = _db.MenuBurgers
                                 .Include(mb => mb.Burger)
                                 .Where(mb => menuIds.Contains(mb.MenuId))
                                 .ToList();

            var menuComplements = _db.MenuComplements
                                     .Include(mc => mc.Complement)
                                     .Where(mc => menuIds.Contains(mc.MenuId))
                                     .ToList();

            foreach (var menu in menus)
            {
                menu.MenuBurgers = menuBurgers
                    .Where(mb => mb.MenuId == menu.Id)
                    .ToList();

                menu.MenuComplements = menuComplements
                    .Where(mc => mc.MenuId == menu.Id)
                    .ToList();
            }

            return menus;
        }

    }
}
