<?php

namespace App\Controller;

use App\DTO\LivraisonDTO;
use App\Entity\LivraisonAffectation;
use App\Repository\CommandeRepository;
use App\Repository\LivraisonAffectationRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class LivraisonController extends AbstractController
{
    public function __construct(private readonly LivraisonAffectationRepository $livraisonAffectationRepository, private readonly CommandeRepository $commandeRepository)
    {
    }

    #[Route('/livraison', name: 'app_livraison')]
    public function index(): Response
    {
        $livraisons = $this->livraisonAffectationRepository->findAll();
        $livraisonsDTO = LivraisonDTO::fromEntities($livraisons, $this->commandeRepository);
        return $this->render('livraison/index.html.twig', [
            'livraisons' => $livraisonsDTO,
            'controller_name' => 'LivraisonController',
        ]);
    }
}
