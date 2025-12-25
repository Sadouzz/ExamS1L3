FROM php:8.4-apache

WORKDIR /var/www/html

RUN a2enmod rewrite headers

RUN apt-get update && apt-get install -y \
    git \
    unzip \
    libicu-dev \
    libonig-dev \
    libzip-dev \
    libpng-dev \
    libxml2-dev \
    libpq-dev \
    default-mysql-client \
    && docker-php-ext-install \
        intl \
        pdo \
        pdo_mysql \
        zip \
        opcache \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

COPY . .

RUN chown -R www-data:www-data var vendor \
    && chmod -R 775 var

RUN composer install --no-interaction --prefer-dist

ENV APACHE_DOCUMENT_ROOT=/var/www/html/public
RUN sed -ri 's!/var/www/html!${APACHE_DOCUMENT_ROOT}!g' \
    /etc/apache2/sites-available/*.conf \
    /etc/apache2/apache2.conf

EXPOSE 80

CMD ["apache2-foreground"]
