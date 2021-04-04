package com.eestienergia.fundraiser.rest.payment;

import com.eestienergia.fundraiser.domain.Basket;
import com.eestienergia.fundraiser.domain.Product;
import com.eestienergia.fundraiser.domain.service.PaymentService;
import com.eestienergia.fundraiser.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/payment")
public class PaymentRestController {

    private final PaymentService paymentService;
    private final ProductService productService;

    @PostMapping("/cash")
    public CashPaymentResponseResource cashPayment(@RequestBody @Valid final CashPaymentRequestResource request) {
        final Map<String, Product> products = productService.getProductsById(request.getProductCodes());
        final BigDecimal change = paymentService.cashPayment(convertToBasket(request, products), request.getAmount());
        return CashPaymentResponseResource.of(change);
    }

    private Basket convertToBasket(final CashPaymentRequestResource request, final Map<String, Product> products) {
        final Basket basket = new Basket();
        for (final String productId : request.getProductCodes()) {
            basket.add(products.get(productId));
        }
        return basket;
    }
}
