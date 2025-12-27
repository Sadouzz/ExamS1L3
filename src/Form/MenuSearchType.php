<?php

namespace App\Form;

use App\DTO\MenuSearchDTO;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class MenuSearchType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
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
            'data_class' => MenuSearchDTO::class,
            'method' => 'GET',
            'csrf_protection' => false,
        ]);
    }
}
