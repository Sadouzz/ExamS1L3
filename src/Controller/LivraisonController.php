<?php

namespace App\Controller;

use App\DTO\ComplementSearchDTO;
use App\DTO\LivraisonDTO;
use App\DTO\LivraisonSearchDTO;
use App\Entity\LivraisonAffectation;
use App\Form\ComplementSearchType;
use App\Form\LivraisonSearchType;
use App\Repository\CommandeRepository;
use App\Repository\LivraisonAffectationRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class LivraisonController extends AbstractController
{
    public function __construct(private readonly LivraisonAffectationRepository $livraisonAffectationRepository, private readonly CommandeRepository $commandeRepository)
    {
    }

    #[Route('/livraison', name: 'app_livraison')]
    public function index(Request $request): Response
    {
        $filtre = [];

        $searchFormDto = new LivraisonSearchDTO();
        $form = $this->createForm(LivraisonSearchType::class, $searchFormDto, [
            'method' => 'GET',
            'csrf_protection' => false,
        ]);
        $form->handleRequest($request);

        if ($form->isSubmitted()) {

            if ($searchFormDto->statut !== null) {
                $filtre['statut'] = $searchFormDto->statut;
            }

            $filtered = true;
        }
        else {
            $filtered = false;
        }

        $page = $request->query->getInt('page', 1);
        $limit = $this->getParameter('LIMIT_PER_PAGE');
        $offset = ($page - 1) * $limit;

        $count = $this->livraisonAffectationRepository->count($filtre);
        $nbrePages = ceil($count / $limit);


        $livraisons = $this->livraisonAffectationRepository->findBy(
            $filtre,
            ['id' => 'DESC'],
            $limit,
            $offset,
        );

        $livraisonsDTO = LivraisonDTO::fromEntities($livraisons, $this->commandeRepository);
        return $this->render('livraison/index.html.twig', [
            'livraisons' => $livraisonsDTO,
            'count' => $count,
            'pageEnCours' => $page,
            'nbrePages' => $nbrePages,
            'limit' => $limit,
            'filtered' => $filtered,
            'formSearchLivraison' => $form->createView(),
        ]);
    }
}
