<?php

namespace App\Controller;

use App\DTO\CommandeDTO;
use App\DTO\CommandeSearchFormDto;
use App\Entity\Commande;
use App\Entity\Enum\StatutCommande;
use App\Form\CommandeSearchType;
use App\Repository\CommandeRepository;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Component\HttpFoundation\RedirectResponse;
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

    #[Route('/commande/{id}/terminer', name: 'app_commande_terminer', methods: ['POST'])]
    public function terminer(Commande $commande, EntityManagerInterface $em, Request $request): RedirectResponse {
        if (!$this->isCsrfTokenValid('terminer_commande_' . $commande->getId(), $request->request->get('_token'))) {
            throw $this->createAccessDeniedException();
        }
        $commande->setStatut(StatutCommande::TERMINEE);
        $em->flush();
        $this->addFlash('success', 'Commande terminée avec succès.');

        return $this->redirectToRoute('app_commande', [
            'page' => $request->query->get('page', 1),
            'search' => $request->query->get('search', ''),
        ]);
    }

    #[Route('/commande/{id}/annuler', name: 'app_commande_annuler', methods: ['POST'])]
    public function annuler(Commande $commande, EntityManagerInterface $em, Request $request): RedirectResponse {
        if (!$this->isCsrfTokenValid('annuler_commande_' . $commande->getId(), $request->request->get('_token'))) {
            throw $this->createAccessDeniedException();
        }
        $commande->setStatut(StatutCommande::ANNULEE);
        $em->flush();
        $this->addFlash('success', 'Commande annulée avec succès.');

        return $this->redirectToRoute('app_commande', [
            'page' => $request->query->get('page', 1),
            'search' => $request->query->get('search', ''),
        ]);
    }
}
