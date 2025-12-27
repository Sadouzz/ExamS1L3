<?php

namespace App\Form;

use App\DTO\ComplementSearchDTO;
use App\Entity\Enum\TypeComplement;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ComplementSearchType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('typeComplement', ChoiceType::class, [
                'choices' => TypeComplement::cases(),
                'choice_label' => fn (TypeComplement $choice) => ucfirst($choice->value),
                'choice_value' => fn (?TypeComplement $choice) => $choice?->value,
                'placeholder' => 'Choisir un type',
                'required' => false,
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
            'data_class' => ComplementSearchDTO::class,
            'method' => 'GET',
            'csrf_protection' => false,
        ]);
    }
}
