<?php

namespace App\Controller;

use App\DTO\MenuDTO;
use App\Repository\MenuRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class MenuController extends AbstractController
{
    public function __construct(private readonly MenuRepository $menuRepository)
    {
    }

    #[Route('/menu', name: 'app_menu')]
    public function index(): Response
    {
        $menus = $this->menuRepository->findAll();
        $menusDTO = MenuDTO::fromEntities($menus);
        return $this->render('menu/index.html.twig', [
            'menus'=> $menusDTO,
        ]);
    }
}
