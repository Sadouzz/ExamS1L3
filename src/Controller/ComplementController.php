<?php

namespace App\Controller;

use App\Repository\ComplementRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class ComplementController extends AbstractController
{
    public function __construct(private readonly ComplementRepository $complementRepository)
    {
    }

    #[Route('/complement', name: 'app_complement')]
    public function index(): Response
    {
        $complements = $this->complementRepository->findAll();
        return $this->render('complement/index.html.twig', [
            'complements' => $complements,
            'controller_name' => 'ComplementController',
        ]);
    }
}
