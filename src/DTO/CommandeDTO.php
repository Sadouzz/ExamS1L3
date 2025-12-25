<?php

namespace App\DTO;
use App\Entity\Commande;
use App\Entity\Enum\StatutCommande;
use App\Entity\Enum\TypeRetrait;
use \DateTimeImmutable;
class CommandeDTO
{
    public int $id;
    public string $quartierName;
    public float $montantHT;
    public float $montantTotal;
    public string $statut;
    public string $typeRetrait;
    public ?bool $isPaid = null;
    public DateTimeImmutable $createdAt;

    public static function fromEntity(Commande $entity): CommandeDTO
    {
        $dto = new CommandeDTO();
        $dto->id = $entity->getId();
        $dto->montantHT = $entity->getMontantHorsLivraison();
        $dto->montantTotal = $entity->getMontantTotal();
        $dto->statut = $entity->getStatut()->name;
        $dto->typeRetrait = $entity->getTypeRetrait()->name;
        $dto->quartierName = $entity->getQuartier()->getId();
        $dto->isPaid = $entity->isPaid();
        $dto->createdAt = $entity->getCreatedAt();
        return $dto;
    }

    public static function fromEntities(array $entities): array
    {
        return array_map(function (Commande $entity) {
            return self::fromEntity($entity);
        }, $entities);
    }
}
