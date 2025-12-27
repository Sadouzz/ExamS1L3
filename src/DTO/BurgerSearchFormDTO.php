<?php

namespace App\DTO;

use App\Entity\BurgerCategorie;

class BurgerSearchFormDTO
{
    public ?BurgerCategorie $burgerCategorie = null;
    public ?bool $isArchived = null;
}
