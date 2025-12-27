<?php

namespace App\Controller;

use App\DTO\BurgerSearchFormDTO;
use App\DTO\BurgerDTO;
use App\Form\BurgerSearchType;
use App\Repository\BurgerRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class BurgerController extends AbstractController
{
    public function __construct(private readonly BurgerRepository $burgerRepository)
    {
    }

    #[Route('/burger', name: 'app_burger')]
    public function index(Request $request): Response
    {
        $filtre = [];

        $searchFormDto = new BurgerSearchFormDTO();
        $form = $this->createForm(BurgerSearchType::class, $searchFormDto, [
            'method' => 'GET',
            'csrf_protection' => false,
        ]);
        $form->handleRequest($request);

        if ($form->isSubmitted()) {

            if ($searchFormDto->burgerCategorie !== null) {
                $filtre['burgerCategorie'] = $searchFormDto->burgerCategorie;
            }

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

        $count = $this->burgerRepository->count($filtre);
        $nbrePages = ceil($count / $limit);

        $burgers = $this->burgerRepository->findBy(
            $filtre,
            ['id' => 'asc'],
            $limit,
            $offset
        );

        $burgersDto = BurgerDto::fromEntities($burgers);
        return $this->render('burger/index.html.twig', [
            'burgers' => $burgersDto,
            'count' => $count,
            'pageEnCours' => $page,
            'nbrePages' => $nbrePages,
            'limit' => $limit,
            'filtered' => $filtered,
            'formSearchBurger' => $form->createView(),
        ]);
    }
}
