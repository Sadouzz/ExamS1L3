<?php

namespace App\Controller;

use App\DTO\MenuDTO;
use App\DTO\MenuSearchDTO;
use App\Form\MenuSearchType;
use App\Repository\MenuRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class MenuController extends AbstractController
{
    public function __construct(private readonly MenuRepository $menuRepository)
    {
    }

    #[Route('/menu', name: 'app_menu')]
    public function index(Request $request): Response
    {
        $filtre = [];

        $searchFormDto = new MenuSearchDTO();
        $form = $this->createForm(MenuSearchType::class, $searchFormDto, [
            'method' => 'GET',
            'csrf_protection' => false,
        ]);
        $form->handleRequest($request);

        if ($form->isSubmitted()) {

            if ($searchFormDto->isArchived !== null) {
                $filtre['isArchived'] = $searchFormDto->isArchived;
            }

            $filtered = true;
        }
        else {
            $filtered = false;
        }

        $page = $request->query->getInt('page', 1);
        $limit = $this->getParameter('LIMIT_PER_PAGE');
        $offset = ($page - 1) * $limit;

        $count = $this->menuRepository->count($filtre);
        $nbrePages = ceil($count / $limit);

        $menus = $this->menuRepository->findBy(
            $filtre,
            ['id' => 'DESC'],
            $limit,
            $offset
        );

        $menusDTO = MenuDTO::fromEntities($menus);
        return $this->render('menu/index.html.twig', [
            'menus'=> $menusDTO,
            'count' => $count,
            'pageEnCours' => $page,
            'nbrePages' => $nbrePages,
            'limit' => $limit,
            'filtered' => $filtered,
            'formSearchMenu' => $form->createView(),
        ]);
    }
}
