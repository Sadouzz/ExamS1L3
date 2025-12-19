using Microsoft.AspNetCore.Mvc;
using Models;
using Services;

public class CatalogController : Controller
{
    private readonly BurgerService burgerService;
    private readonly MenuService menuService;

    public CatalogController(BurgerService _burgerService, MenuService _menuService)
    {
        burgerService = _burgerService;
        menuService = _menuService;
    }

    public IActionResult Index()
    {
        var catalog = new Catalog
        {
            Burgers = burgerService.GetAll(),
            Menus = menuService.GetAll()
        };

        return View(catalog);
    }
}
