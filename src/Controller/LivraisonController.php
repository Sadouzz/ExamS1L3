<?php

namespace App\Controller;

use App\Entity\LivraisonAffectation;
use App\Repository\LivraisonAffectationRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class LivraisonController extends AbstractController
{
    public function __construct(private readonly LivraisonAffectationRepository $livraisonAffectationRepository)
    {
    }

    #[Route('/livraison', name: 'app_livraison')]
    public function index(): Response
    {
        $livraisons = $this->livraisonAffectationRepository->findAll();
        return $this->render('livraison/index.html.twig', [
            'livraisons' => $livraisons,
            'controller_name' => 'LivraisonController',
        ]);
    }
}
