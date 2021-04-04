package com.eestienergia.fundraiser.domain;

public class BasketBuilder {

    public static Basket aBasket(final Product... products) {
        final Basket basket = new Basket();
        for (final Product product : products) {
            basket.add(product);
        }
        return basket;
    }

}