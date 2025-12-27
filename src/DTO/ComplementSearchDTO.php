<?php

namespace App\DTO;

use App\Entity\Enum\TypeComplement;

class ComplementSearchDTO
{
    public ?TypeComplement $typeComplement = null;
    public ?bool $isArchived = null;
}
