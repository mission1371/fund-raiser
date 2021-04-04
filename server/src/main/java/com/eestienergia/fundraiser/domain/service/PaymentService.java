package com.eestienergia.fundraiser.domain.service;

import com.eestienergia.fundraiser.domain.Basket;
import com.eestienergia.fundraiser.domain.Product;
import com.eestienergia.fundraiser.domain.exception.InsufficientBalanceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final ProductService productService;

    public BigDecimal cashPayment(final Basket basket, final BigDecimal paidAmount) {
        if (paidAmount.compareTo(basket.getTotal()) < 0) {
            throw new InsufficientBalanceException();
        }
        productService.reduceStock(basket);
        return paidAmount.subtract(basket.getTotal());
    }

}
