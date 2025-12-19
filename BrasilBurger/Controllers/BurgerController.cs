using Microsoft.AspNetCore.Mvc;
using Services;

namespace Controllers
{
    [Route("burger")]
    public class BurgerController : Controller
    {
        private readonly BurgerService burgerService;

        public BurgerController(BurgerService _burgerService)
        {
            burgerService = _burgerService;
        }

        [HttpGet("{id:long}")]
        public IActionResult Details(long id)
        {
            var burger = burgerService.GetById(id);
            if (burger == null)
                return NotFound();

            return View(burger);
        }
    }
}
