<?php

namespace App\Form;

use App\DTO\BurgerSearchFormDTO;
use App\Entity\BurgerCategorie;
use App\Entity\Enum\StatutCommande;
use App\Entity\Enum\TypeRetrait;
use App\Entity\Quartier;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class BurgerSearchType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('burgerCategorie', EntityType::class, [
                'class' => BurgerCategorie::class,
                'choice_label' => 'nom',
                'required' => false,
                'placeholder' => 'Toutes les catégories',
                'label' => 'Categorie',
                'attr' => ['class' => 'form-select'],
            ])

            ->add('isArchived', ChoiceType::class, [
                'required' => false,
                'label' => 'Actif',
                'attr' => [
                    'class' => 'form-select',
                ],
                'choices' => [
                    'Actif' => false,
                    'Archivé' => true,
                ],
                'data' => false,
            ]);
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => BurgerSearchFormDTO::class,
            'method' => 'GET',
            'csrf_protection' => false,
        ]);
    }
}
