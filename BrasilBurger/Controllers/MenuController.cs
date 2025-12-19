using Microsoft.AspNetCore.Mvc;
using Services;

namespace Controllers
{
    [Route("menu")]
    public class MenuController : Controller
    {
        private readonly MenuService menuService;

        public MenuController(MenuService _menuService)
        {
            menuService = _menuService;
        }

        [HttpGet("{id:long}")]
        public IActionResult Details(long id)
        {
            var menu = menuService.GetById(id);
            if (menu == null)
                return NotFound();

            return View(menu);
        }
    }
}
