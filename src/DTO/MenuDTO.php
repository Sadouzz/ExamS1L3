<?php

namespace App\DTO;

use App\Entity\Menu;

class MenuDTO
{
    public int $id;
    public string $libelle;
    public ?string $imageUrl;
    public float $prix;
    public bool $isArchived;
    public $burgers = [];
    public $complements = [];

    public static function fromEntity(Menu $entity): MenuDTO
    {
        $dto = new MenuDTO();
        $dto->id = $entity->getId();
        $dto->libelle = $entity->getLibelle();
        $dto->prix = $entity->getPrix();
        $dto->imageUrl = $entity->getImageUrl();
        $dto->isArchived = $entity->isArchived();
        foreach ($entity->getMenuBurgers() as $burger) {
            $dto->burgers[] = $burger;
        }
        foreach ($entity->getMenuComplements() as $complement) {
            $dto->complements[] = $complement;
        }
        return $dto;
    }

    public static function fromEntities(array $entities): array
    {
        return array_map(function (Menu $entity) {
            return self::fromEntity($entity);
        }, $entities);
    }
}
