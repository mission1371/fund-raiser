package com.eestienergia.fundraiser.persistence;

import com.eestienergia.fundraiser.domain.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityConverter {

    public Product convert(final ProductEntity entity) {
        return Product.builder()
                .code(entity.getCode())
                .name(entity.getName())
                .imageRelativePath(entity.getImageRelativePath())
                .stock(entity.getStock())
                .price(entity.getPrice())
                .currencyCode(entity.getCurrencyCode())
                .build();
    }

}
