<?php

namespace App\DTO;

use App\Entity\LivraisonAffectation;
use App\Repository\CommandeRepository;

class LivraisonDTO
{
    public int $id;
    public ?string $zone = null;
    public ?string $quartier = null;
    public ?string $statut = null;

    public function __construct()
    {
    }

    public static function fromEntity(LivraisonAffectation $entity, CommandeRepository $commandeRepository): self
    {
        $dto = new self();
        $dto->id = $entity->getId();
        $dto->zone = $entity->getZone()?->getNom();

        $commande = $commandeRepository->find($entity->getCommande()->getId());
        $dto->quartier = $commande?->getQuartier()->getNom();
        $dto->statut = $entity->getStatut()->name;

        return $dto;
    }

    public static function fromEntities(array $entities, CommandeRepository $commandeRepository): array
    {
        return array_map(fn(LivraisonAffectation $entity) => self::fromEntity($entity, $commandeRepository), $entities);
    }
}
