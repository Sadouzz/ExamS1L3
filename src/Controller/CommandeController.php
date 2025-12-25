<?php

namespace App\Controller;

use App\DTO\CommandeDTO;
use App\DTO\CommandeSearchFormDto;
use App\Form\CommandeSearchType;
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
        $filtre = [];

        $searchFormDto = new CommandeSearchFormDto();
        $form = $this->createForm(CommandeSearchType::class, $searchFormDto, [
            'method' => 'GET',
            'csrf_protection' => false,
        ]);
        $form->handleRequest($request);

        if ($form->isSubmitted()) {

            if ($searchFormDto->quartier !== null) {
                $filtre['quartier'] = $searchFormDto->quartier;
            }

            if ($searchFormDto->statut !== null) {
                $filtre['statut'] = $searchFormDto->statut;
            }

            if ($searchFormDto->typeRetrait !== null) {
                $filtre['typeRetrait'] = $searchFormDto->typeRetrait;
            }

            if ($searchFormDto->isPaid !== null) {
                $filtre['isPaid'] = $searchFormDto->isPaid;
            }

            $filtered = true;
        }
        else {
            $filtered = false;
        }


        $page = $request->query->getInt('page', 1);
        $limit = $this->getParameter('LIMIT_PER_PAGE');
        $offset = ($page - 1) * $limit;

        $count = $this->commandeRepository->count($filtre);
        $nbrePages = ceil($count / $limit);

        $commandes = $this->commandeRepository->findBy(
            $filtre,
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
            'limit' => $limit,
            'filtered' => $filtered,
            'formSearchCommande' => $form->createView(),
        ]);
    }
}
