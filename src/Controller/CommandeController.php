<?php

namespace App\Controller;

use App\DTO\CommandeDTO;
use App\Repository\CommandeRepository;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class CommandeController extends AbstractController
{
    public function __construct(private readonly CommandeRepository $commandeRepository)
    {
    }

    #[Route('/commande', name: 'app_commande')]
    public function index(Request $request): Response
    {
        $page = $request->query->getInt('page', 1);
        $limit = $this->getParameter('LIMIT_PER_PAGE');
        $offset = ($page - 1) * $limit;

        $count = $this->commandeRepository->count([]);
        $nbrePages = ceil($count / $limit);

        $commandes = $this->commandeRepository->findBy(
            [],
            ['id' => 'asc'],
            $limit,
            $offset
        );

        $commandesDto = CommandeDto::fromEntities($commandes);
        return $this->render('commande/index.html.twig', [
            'commandes' => $commandesDto,
            'count' => $count,
            'pageEnCours' => $page,
            'nbrePages' => $nbrePages,
            'controller_name' => 'CommandeController',
        ]);
    }
}
