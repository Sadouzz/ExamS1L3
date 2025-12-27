<?php

namespace App\DTO;
use App\Entity\Burger;
use \DateTimeImmutable;
class BurgerDTO
{
    public int $id;
    public string $categorieName;
    public string $libelle;
    public string $description;
    public float $prix;
    public string $imageUrl;
    public ?bool $isArchived = null;
    public DateTimeImmutable $createdAt;

    public static function fromEntity(Burger $entity): BurgerDTO
    {
        $dto = new BurgerDTO();
        $dto->id = $entity->getId();
        $dto->categorieName = $entity->getBurgerCategorie()->getNom();
        $dto->prix = $entity->getPrix();
        $dto->description = $entity->getDescription();
        $dto->imageUrl = $entity->getImageUrl();
        $dto->libelle = $entity->getLibelle();
        $dto->isArchived = $entity->isArchived();
        return $dto;
    }

    public static function fromEntities(array $entities): array
    {
        return array_map(function (Burger $entity) {
            return self::fromEntity($entity);
        }, $entities);
    }
}
