<?php

namespace App\DTO;
use App\Entity\Complement;
use \DateTimeImmutable;
class ComplementDTO
{
    public int $id;
    public string $typeComplement;
    public string $libelle;
    public float $prix;
    public string $imageUrl;
    public ?bool $isArchived = null;
    public DateTimeImmutable $createdAt;

    public static function fromEntity(Complement $entity): ComplementDTO
    {
        $dto = new ComplementDTO();
        $dto->id = $entity->getId();
        $dto->typeComplement = $entity->getTypeComplement()->name;
        $dto->prix = $entity->getPrix();
        $dto->imageUrl = $entity->getImageUrl();
        $dto->libelle = $entity->getLibelle();
        $dto->isArchived = $entity->isArchived();
        return $dto;
    }

    public static function fromEntities(array $entities): array
    {
        return array_map(function (Complement $entity) {
            return self::fromEntity($entity);
        }, $entities);
    }
}
