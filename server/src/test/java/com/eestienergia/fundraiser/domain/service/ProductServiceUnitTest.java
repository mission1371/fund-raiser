package com.eestienergia.fundraiser.domain.service;

import com.eestienergia.fundraiser.domain.Product;
import com.eestienergia.fundraiser.domain.exception.ProductNotFoundException;
import com.eestienergia.fundraiser.domain.exception.ProductOutOfStockException;
import com.eestienergia.fundraiser.domain.exception.StockIncreaseException;
import com.eestienergia.fundraiser.domain.exception.StockUpdateException;
import com.eestienergia.fundraiser.persistence.ProductEntity;
import com.eestienergia.fundraiser.persistence.ProductEntityConverter;
import com.eestienergia.fundraiser.persistence.ProductEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.eestienergia.fundraiser.domain.BasketBuilder.aBasket;
import static com.eestienergia.fundraiser.domain.ProductBuilder.aProduct;
import static com.eestienergia.fundraiser.persistence.ProductEntityBuilder.aProductEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceUnitTest {

    @Mock private ProductEntityRepository repository;

    @Mock private ProductEntityConverter converter;

    @InjectMocks private ProductService service;

    @Test
    void shouldLoadAllProductsFromRepository() {
        //given
        final ProductEntity givenEntity = aProductEntity().build();
        final Product givenProduct = aProduct().build();
        given(converter.convert(eq(givenEntity))).willReturn(givenProduct);
        given(repository.findAll()).willReturn(Collections.singletonList(givenEntity));

        // when
        final List<Product> products = service.getProducts();

        // then
        assertThat(products).containsExactly(givenProduct);
        verify(repository).findAll();
    }

    @Test
    void shouldFindProductsByType() {
        //given
        final ProductEntity givenEntity = aProductEntity().build();
        final Product givenProduct = aProduct().build();
        final Long givenProductType = 1L;
        given(converter.convert(eq(givenEntity))).willReturn(givenProduct);
        given(repository.findByType(eq(givenProductType))).willReturn(Collections.singletonList(givenEntity));

        // when
        final List<Product> products = service.getByProductType(givenProductType);

        // then
        assertThat(products).containsExactly(givenProduct);
        verify(repository).findByType(givenProductType);
    }

    @Test
    void shouldFindProductByCode() {
        //given
        final ProductEntity givenEntity = aProductEntity().build();
        final Product givenProduct = aProduct().build();
        final String givenProductCode = "CP1";
        given(repository.findByCode(eq(givenProductCode))).willReturn(givenEntity);
        given(converter.convert(eq(givenEntity))).willReturn(givenProduct);

        // when
        final Product product = service.getByCode(givenProductCode);

        // then
        assertThat(product).isEqualTo(givenProduct);
        verify(repository).findByCode(givenProductCode);
    }

    @Test
    void shouldThrowExceptionWhenCouldNotFindProduct() {
        //given
        final String givenProductCode = "CP1";
        given(repository.findByCode(eq(givenProductCode))).willReturn(null);

        // when
        final Throwable throwable = catchThrowable(() -> service.getByCode(givenProductCode));

        // then
        assertThat(throwable).isInstanceOf(ProductNotFoundException.class);
        assertThat(throwable).hasMessage("No product found with code: CP1");
    }

    @Test
    void shouldGroupByProductCodesBeforeQueryingDatabase() {
        //given
        final String givenProductCode1 = "CP1";
        final String givenProductCode2 = "CP2";
        final ProductEntity givenProductEntity1 = aProductEntity().code(givenProductCode1).build();
        final ProductEntity givenProductEntity2 = aProductEntity().code(givenProductCode2).build();
        final List<String> codes = Arrays.asList(givenProductCode1, givenProductCode2, givenProductCode2);
        given(repository.findByCodeIn(any())).willReturn(Arrays.asList(givenProductEntity1, givenProductEntity2));
        given(converter.convert(eq(givenProductEntity1))).willReturn(aProduct().code(givenProductCode1).build());
        given(converter.convert(eq(givenProductEntity2))).willReturn(aProduct().code(givenProductCode2).build());

        // when
        service.getProductsById(codes);

        // then
        verify(repository).findByCodeIn(argThat(argument -> argument.size() == 2));
        verify(repository).findByCodeIn(argThat(argument -> argument.contains(givenProductCode1)));
        verify(repository).findByCodeIn(argThat(argument -> argument.contains(givenProductCode2)));
    }

    @Test
    void shouldThrowExceptionWhenProductIsNotFound() {
        //given
        final String givenProductCode = "CP1";
        final List<String> codes = Collections.singletonList(givenProductCode);
        given(repository.findByCodeIn(any())).willReturn(Collections.emptyList());

        // when
        final Throwable throwable = catchThrowable(() -> service.getProductsById(codes));

        // then
        assertThat(throwable).isInstanceOf(ProductNotFoundException.class);
        assertThat(throwable).hasMessage("At least one product not found. [CP1]");
    }

    @Test
    void shouldReturnProductMap() {
        //given
        final String givenProductCode1 = "CP1";
        final ProductEntity givenProductEntity1 = aProductEntity().code(givenProductCode1).build();
        final Product givenProduct1 = aProduct().code(givenProductCode1).build();
        given(converter.convert(eq(givenProductEntity1))).willReturn(givenProduct1);
        final String givenProductCode2 = "CP2";
        final ProductEntity givenProductEntity2 = aProductEntity().code(givenProductCode2).build();
        final Product givenProduct2 = aProduct().code(givenProductCode2).build();
        given(converter.convert(eq(givenProductEntity2))).willReturn(givenProduct2);

        final List<String> codes = Arrays.asList(givenProductCode1, givenProductCode2, givenProductCode2);
        given(repository.findByCodeIn(any())).willReturn(Arrays.asList(givenProductEntity1, givenProductEntity2));

        // when
        final Map<String, Product> products = service.getProductsById(codes);

        // then
        assertThat(products).hasEntrySatisfying(givenProductCode1, (item) -> assertThat(item).isEqualTo(givenProduct1));
        assertThat(products).hasEntrySatisfying(givenProductCode2, (item) -> assertThat(item).isEqualTo(givenProduct2));
    }

    @Test
    void shouldThrowExceptionWhenProductIsOutOfStock() {
        //given
        final ProductEntity givenProductEntity = aProductEntity().stock(0).build();
        given(repository.findByCode(any())).willReturn(givenProductEntity);

        // when
        final Throwable throwable = catchThrowable(() -> service.reduceStock(aBasket(aProduct().build())));

        // then
        assertThat(throwable).isInstanceOf(ProductOutOfStockException.class);
        assertThat(throwable).hasMessage("Not enough Muffin left in the stock");
    }

    @Test
    void shouldThrowExceptionWhenProductIsNotFoundWhileReducingStock() {
        //given
        given(repository.findByCode(any())).willReturn(null);

        // when
        final Throwable throwable = catchThrowable(() -> service.reduceStock(aBasket(aProduct().build())));

        // then
        assertThat(throwable).isInstanceOf(ProductNotFoundException.class);
        assertThat(throwable).hasMessage("No product found with code: code");
    }

    @Test
    void shouldReduceStock() {
        //given
        final Product givenProduct = aProduct().build();
        final ProductEntity givenProductEntity = aProductEntity().stock(10).build();
        given(repository.findByCode(any())).willReturn(givenProductEntity);

        // when
        service.reduceStock(aBasket(givenProduct, givenProduct, givenProduct, givenProduct, givenProduct));

        // then
        verify(repository).save(argThat(argument -> argument.getStock() == 5));
    }

    @Test
    void shouldThrowExceptionWhenAmountIsLessThanZeroWhileIncreasingStock() {
        // when
        final Throwable throwable = catchThrowable(() -> service.increaseStock(aProduct().build(), -1));

        // then
        assertThat(throwable).isInstanceOf(StockIncreaseException.class);
        assertThat(throwable).hasMessage("Cannot increase stock by -1");
    }

    @Test
    void shouldThrowExceptionWhenProductIsNotFoundWhileIncreasingStock() {
        //given
        given(repository.findByCode(any())).willReturn(null);

        // when
        final Throwable throwable = catchThrowable(() -> service.increaseStock(aProduct().build(), 1));

        // then
        assertThat(throwable).isInstanceOf(ProductNotFoundException.class);
        assertThat(throwable).hasMessage("No product found with code: code");
    }

    @Test
    void shouldIncreaseStock() {
        //given
        final int givenQuantity = 5;
        final ProductEntity givenProductEntity = aProductEntity().stock(10).build();
        given(repository.findByCode(any())).willReturn(givenProductEntity);

        // when
        service.increaseStock(aProduct().build(), givenQuantity);

        // then
        verify(repository).save(argThat(argument -> argument.getStock() == 15));
    }

    @Test
    void shouldReturnProductAfterIncreaseStock() {
        //given
        final Product givenProduct = aProduct().build();
        final ProductEntity givenProductEntity = aProductEntity().build();
        given(repository.findByCode(any())).willReturn(givenProductEntity);
        given(repository.save(givenProductEntity)).willReturn(givenProductEntity);
        given(converter.convert(givenProductEntity)).willReturn(givenProduct);

        // when
        final Product expected = service.increaseStock(aProduct().build(), 1);

        // then
        assertThat(expected).isEqualTo(givenProduct);
    }

    @Test
    void shouldThrowExceptionWhenAmountIsLessThanZeroWhileUpdatingStock() {
        // when
        final Throwable throwable = catchThrowable(() -> service.updateStock(aProduct().build(), -1));

        // then
        assertThat(throwable).isInstanceOf(StockUpdateException.class);
        assertThat(throwable).hasMessage("Cannot update stock to -1");
    }

    @Test
    void shouldThrowExceptionWhenProductIsNotFoundWhileUpdatingStock() {
        //given
        given(repository.findByCode(any())).willReturn(null);

        // when
        final Throwable throwable = catchThrowable(() -> service.updateStock(aProduct().build(), 1));

        // then
        assertThat(throwable).isInstanceOf(ProductNotFoundException.class);
        assertThat(throwable).hasMessage("No product found with code: code");
    }

    @Test
    void shouldUpdateStock() {
        //given
        final int givenQuantity = 3;
        final ProductEntity givenProductEntity = aProductEntity().stock(10).build();
        given(repository.findByCode(any())).willReturn(givenProductEntity);

        // when
        service.updateStock(aProduct().build(), givenQuantity);

        // then
        verify(repository).save(argThat(argument -> argument.getStock() == 3));
    }

    @Test
    void shouldReturnProductAfterUpdatingStock() {
        //given
        final Product givenProduct = aProduct().build();
        final ProductEntity givenProductEntity = aProductEntity().build();
        given(repository.findByCode(any())).willReturn(givenProductEntity);
        given(repository.save(givenProductEntity)).willReturn(givenProductEntity);
        given(converter.convert(givenProductEntity)).willReturn(givenProduct);

        // when
        final Product expected = service.updateStock(aProduct().build(), 1);

        // then
        assertThat(expected).isEqualTo(givenProduct);
    }
}
