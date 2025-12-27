<?php

namespace App\Controller;

use App\DTO\ComplementDTO;
use App\DTO\ComplementSearchDTO;
use App\Form\ComplementSearchType;
use App\Repository\ComplementRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Attribute\Route;

final class ComplementController extends AbstractController
{
    public function __construct(private readonly ComplementRepository $complementRepository)
    {
    }

    #[Route('/complement', name: 'app_complement')]
    public function index(Request $request): Response
    {
        $filtre = [];

        $searchFormDto = new ComplementSearchDTO();
        $form = $this->createForm(ComplementSearchType::class, $searchFormDto, [
            'method' => 'GET',
            'csrf_protection' => false,
        ]);
        $form->handleRequest($request);

        if ($form->isSubmitted()) {

            if ($searchFormDto->typeComplement !== null) {
                $filtre['typeComplement'] = $searchFormDto->typeComplement;
            }

            if ($searchFormDto->isArchived !== null) {
                $filtre['isArchived'] = $searchFormDto->isArchived;
            }

            $filtered = true;
        }
        else {
            $filtered = false;
        }

        $page = $request->query->getInt('page', 1);
        $limit = $this->getParameter('LIMIT_PER_PAGE');
        $offset = ($page - 1) * $limit;

        $count = $this->complementRepository->count($filtre);
        $nbrePages = ceil($count / $limit);

        $complements = $this->complementRepository->findBy(
            $filtre,
            ['id' => 'asc'],
            $limit,
            $offset
        );

        $complementsDto = ComplementDto::fromEntities($complements);
        return $this->render('complement/index.html.twig', [
            'complements' => $complementsDto,
            'count' => $count,
            'pageEnCours' => $page,
            'nbrePages' => $nbrePages,
            'limit' => $limit,
            'filtered' => $filtered,
            'formSearchComplement' => $form->createView(),
        ]);
    }
}
