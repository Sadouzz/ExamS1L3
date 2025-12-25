<?php

namespace App\Controller;

use App\DTO\CommandeDTO;
use App\Repository\CommandeRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class CommandeController extends AbstractController
{
    public function __construct(private readonly CommandeRepository $commandeRepository)
    {
    }

    #[Route('/commande', name: 'app_commande')]
    public function index(): Response
    {
        $commandes = $this->commandeRepository->findAll();
        $commandesDto = CommandeDto::fromEntities($commandes);
        return $this->render('commande/index.html.twig', [
            'commandes' => $commandesDto,
            'controller_name' => 'CommandeController',
        ]);
    }
}
