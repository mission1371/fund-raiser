package com.eestienergia.fundraiser.domain.service;

import com.eestienergia.fundraiser.domain.Product;
import com.eestienergia.fundraiser.domain.exception.ProductNotFoundException;
import com.eestienergia.fundraiser.domain.exception.ProductOutOfStockException;
import com.eestienergia.fundraiser.domain.exception.StockIncreaseException;
import com.eestienergia.fundraiser.domain.exception.StockUpdateException;
import com.eestienergia.fundraiser.persistence.ProductEntity;
import com.eestienergia.fundraiser.persistence.ProductEntityConverter;
import com.eestienergia.fundraiser.persistence.ProductEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductEntityRepository repository;
    private final ProductEntityConverter converter;

    public List<Product> getProducts() {
        return repository.findAll().stream().map(converter::convert).collect(toList());
    }

    public List<Product> getByProductType(final Long type) {
        return repository.findByType(type).stream().map(converter::convert).collect(toList());
    }

    public Map<String, Product> getProductsById(final List<String> codes) {
        final HashSet<String> uniqueCodes = new HashSet<>(codes);
        final List<ProductEntity> products = repository.findByCodeIn(uniqueCodes);
        if (products.size() != uniqueCodes.size()) {
            throw new ProductNotFoundException(uniqueCodes);
        }
        return products.stream()
                .map(converter::convert)
                .collect(Collectors.toMap(Product::getCode, Function.identity()));
    }

    public Product getByCode(final String code) {
        return repository.findByCode(code).map(converter::convert).orElseThrow(() -> new ProductNotFoundException(code));
    }

    public Product reduceStock(final Product product, final long quantity) {
        final ProductEntity entity = getEntityByCode(product.getCode());
        if (entity.getStock() < quantity) {
            throw new ProductOutOfStockException(product.getName());
        }
        entity.setStock(entity.getStock() - quantity);
        return converter.convert(repository.save(entity));
    }

    public Product increaseStock(final Product product, final long quantity) {
        if (quantity < 0) {
            throw new StockIncreaseException(quantity);
        }
        final ProductEntity entity = getEntityByCode(product.getCode());
        entity.setStock(entity.getStock() + quantity);
        return converter.convert(repository.save(entity));
    }

    public Product updateStock(final Product product, final long quantity) {
        if (quantity < 0) {
            throw new StockUpdateException(quantity);
        }
        final ProductEntity entity = getEntityByCode(product.getCode());
        entity.setStock(quantity);
        return converter.convert(repository.save(entity));
    }

    private ProductEntity getEntityByCode(final String code) {
        return repository.findByCode(code).orElseThrow(() -> new ProductNotFoundException(code));
    }
}
