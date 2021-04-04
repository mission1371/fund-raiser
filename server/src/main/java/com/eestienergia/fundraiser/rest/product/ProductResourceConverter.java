package com.eestienergia.fundraiser.rest.product;

import com.eestienergia.fundraiser.domain.Product;
import org.springframework.stereotype.Component;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@Component
class ProductResourceConverter {

    ProductResource convert(final Product product) {
        return ProductResource.builder()
                .code(product.getCode())
                .name(product.getName())
                .description(product.getName())
                .avatarUrl(fromMethodCall(on(ProductRestController.class).getProductImage(product.getCode())).build().getPath())
                .stock(product.getStock())
                .price(product.getPrice())
                .currencyCode(product.getCurrencyCode())
                .build();
    }
}
