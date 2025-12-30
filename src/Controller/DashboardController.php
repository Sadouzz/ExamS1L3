<?php

namespace App\Controller;

use App\Entity\Enum\StatutCommande;
use App\Repository\CommandeItemRepository;
use App\Repository\CommandeRepository;
use DateTimeImmutable;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class DashboardController extends AbstractController
{
    public function __construct(private readonly CommandeRepository $commandeRepository, private readonly CommandeItemRepository $commandeItemRepository)
    {
    }

    #[Route('/dashboard', name: 'app_dashboard')]
    public function index(): Response
    {
        $start = new DateTimeImmutable('today');
        $end   = new DateTimeImmutable('tomorrow');
        $commandesToday = $this->commandeRepository->findByDate($start, $end);
        $commandesEnCours = 0;
        $commandesAnnulees = 0;
        $commandesPayees = [];

        foreach ($commandesToday as $commande) {
            if ($commande->getStatut() === StatutCommande::EN_ATTENTE || $commande->getStatut() === StatutCommande::VALIDEE) {
                $commandesEnCours++;
            }
            if ($commande->getStatut() === StatutCommande::ANNULEE) {
                $commandesAnnulees++;
            }

            if ($commande->isPaid())
            {
                $commandesPayees[] = $commande;
            }
        }

        $recettes = 0;
        foreach ($commandesPayees as $com) {
            $recettes += $com->getMontantHorsLivraison();
        }

        $repartition = $this->triRepartitionCommandes($commandesPayees);
        $repartitionSorted = [
            'burgers' => array_slice($repartition['burgers'], 0, 3, true),
            'menus' => array_slice($repartition['menus'], 0, 3, true),
            'complements' => array_slice($repartition['complements'], 0, 3, true),
        ];
        $burgerLabels = array_keys($repartitionSorted['burgers']);
        $burgerValues = array_values($repartitionSorted['burgers']);
        $menuLabels = array_keys($repartitionSorted['menus']);
        $menuValues = array_values($repartitionSorted['menus']);

        return $this->render('dashboard/index.html.twig', [
            'commandesEnCours' => $commandesEnCours,
            'commandesAnnulees' => $commandesAnnulees,
            'recettes' => $recettes,
            'commandesPayees' => count($commandesPayees),
            'repartition' => $repartitionSorted,
            'burgersLabels' => $burgerLabels,
            'burgersValues' => $burgerValues,
            'menusLabels' => $menuLabels,
            'menusValues' => $menuValues,
        ]);
    }

    public function triRepartitionCommandes($commandes): array
    {
        $menus = [];
        $burgers = [];
        $complements = [];
        foreach ($commandes as $commande) {
            $commandeItems = $this->commandeItemRepository->findBy(['commande' => $commande->getId()]);
            foreach ($commandeItems as $commandeItem) {
                if($commandeItem->getMenu() != null)
                {
                    $menus[] = $commandeItem->getMenu();
                }
                elseif($commandeItem->getBurger() != null)
                {
                    $burgers[] = $commandeItem->getBurger();
                }
                else{
                    $complements[] = $commandeItem->getComplement();
                }
            }
        }

        $burgerSorted = [];

        foreach ($burgers as $burger) {
            $libelle = $burger->getLibelle();

            if (!isset($burgerSorted[$libelle])) {
                $burgerSorted[$libelle] = 0;
            }

            $burgerSorted[$libelle]++;
        }

        arsort($burgerSorted);

        $menuSorted = [];

        foreach ($menus as $menu) {
            $libelle = $menu->getLibelle();

            if (!isset($menuSorted[$libelle])) {
                $menuSorted[$libelle] = 0;
            }

            $menuSorted[$libelle]++;
        }

        arsort($burgerSorted);


        return [
            'menus' => $menuSorted,
            'burgers' => $burgerSorted,
            'complements' => $complements,
        ];
    }

}
