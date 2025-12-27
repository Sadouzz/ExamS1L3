<?php

namespace App\Controller;

use App\Repository\BurgerRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class BurgerController extends AbstractController
{
    public function __construct(private readonly BurgerRepository $burgerRepository)
    {
    }

    #[Route('/burger', name: 'app_burger')]
    public function index(): Response
    {
        $burgers = $this->burgerRepository->findAll();
        return $this->render('burger/index.html.twig', [
            'burgers' => $burgers,
            'controller_name' => 'BurgerController',
        ]);
    }
}
