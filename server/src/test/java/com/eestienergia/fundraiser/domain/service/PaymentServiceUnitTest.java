package com.eestienergia.fundraiser.domain.service;

import com.eestienergia.fundraiser.domain.Basket;
import com.eestienergia.fundraiser.domain.Product;
import com.eestienergia.fundraiser.domain.exception.InsufficientBalanceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static com.eestienergia.fundraiser.domain.BasketBuilder.aBasket;
import static com.eestienergia.fundraiser.domain.ProductBuilder.aProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PaymentServiceUnitTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private PaymentService service;

    @Test
    void shouldReturnChange() {
        // given
        final BigDecimal givenPaidAmount = BigDecimal.TEN;
        final BigDecimal givenPrice = BigDecimal.ONE;
        final BigDecimal expectedChange = givenPaidAmount.subtract(givenPrice);
        final Product givenProduct = aProduct().price(givenPrice).build();
        final Basket givenBasket = aBasket(givenProduct);

        // when
        final BigDecimal change = service.cashPayment(givenBasket, givenPaidAmount);

        // then
        assertThat(change).isEqualTo(expectedChange);
    }

    @Test
    void shouldReduceStockOfEachProductInBasket() {
        // given
        final Product givenProduct1 = aProduct().code("P1").build();
        final Product givenProduct2 = aProduct().code("P2").build();
        final Basket givenBasket = aBasket(givenProduct1, givenProduct2, givenProduct2);

        // when
        service.cashPayment(givenBasket, BigDecimal.valueOf(100));

        // then
        verify(productService).reduceStock(givenBasket);
    }

    @Test
    void shouldThrowExceptionWhenPaidAmountIsLessThanBasketTotal() {
        // given
        final Product givenProduct = aProduct().price(BigDecimal.TEN).build();
        final Basket givenBasket = aBasket(givenProduct);

        // when
        final Throwable throwable = catchThrowable(() -> service.cashPayment(givenBasket, BigDecimal.ONE));

        // then
        assertThat(throwable).isInstanceOf(InsufficientBalanceException.class);
    }

}
