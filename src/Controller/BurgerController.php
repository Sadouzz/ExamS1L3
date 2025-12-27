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

        $burgers = $this->burgerRepository->findBy(
            $filtre,
            ['id' => 'asc']
        );

        $burgersDto = BurgerDto::fromEntities($burgers);
        return $this->render('burger/index.html.twig', [
            'burgers' => $burgersDto,
            'formSearchBurger' => $form->createView(),
        ]);
    }
}
