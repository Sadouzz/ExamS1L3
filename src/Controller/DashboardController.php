<?php

namespace App\Controller;

use App\Entity\Enum\StatutCommande;
use App\Repository\CommandeItemRepository;
use App\Repository\CommandeRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class DashboardController extends AbstractController
{
    public function __construct(private readonly CommandeRepository $commandeRepository)
    {
    }

    #[Route('/', name: 'app_dashboard')]
    public function index(): Response
    {
        $start = new \DateTimeImmutable('today');
        $end   = new \DateTimeImmutable('tomorrow');
        $commandesToday = $this->commandeRepository->findByDate($start, $end);
        $commandesEnCours = 0;
        $commandesAnnulees = 0;


        foreach ($commandesToday as $commande) {
            if ($commande->getStatut() === StatutCommande::EN_ATTENTE || $commande->getStatut() === StatutCommande::VALIDEE) {
                $commandesEnCours++;
            }
            if ($commande->getStatut() === StatutCommande::ANNULEE) {
                $commandesAnnulees++;
            }
        }

        return $this->render('dashboard/index.html.twig', [
            'commandesEnCours' => $commandesEnCours,
            'commandesAnnulees' => $commandesAnnulees,
        ]);
    }
}
