FROM php:8.4-cli

WORKDIR /app

RUN apt-get update && apt-get install -y \
    git \
    unzip \
    libicu-dev \
    libzip-dev \
    libpng-dev \
    libonig-dev \
    libxml2-dev \
    default-mysql-client \
    && docker-php-ext-install \
        intl \
        pdo \
        pdo_mysql \
        zip \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

COPY . .

ENV APP_ENV=prod
ENV APP_DEBUG=0

RUN composer install \
    --no-dev \
    --optimize-autoloader \
    --no-interaction

RUN php bin/console cache:clear \
    && php bin/console cache:warmup

EXPOSE 10000

CMD php -S 0.0.0.0:$PORT -t public
