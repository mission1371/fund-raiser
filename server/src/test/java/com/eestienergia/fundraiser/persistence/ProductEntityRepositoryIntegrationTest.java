package com.eestienergia.fundraiser.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.eestienergia.fundraiser.persistence.ProductEntityBuilder.aProductEntity;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {"spring.liquibase.contexts=test"})
class ProductEntityRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductEntityRepository repository;

    @Test
    void shouldFindByCode() {
        // given
        final String code = "code";
        final ProductEntity entity = entityManager.persistAndFlush(aProductEntity().build());

        //when
        final ProductEntity expected = repository.findByCode(code);

        //then
        assertThat(expected).isNotNull();
        assertThat(expected).isEqualTo(entity);
    }

    @Test
    void shouldFindByType() {
        // given
        final Long type = 1L;
        final ProductEntity entity1 = entityManager.persistAndFlush(aProductEntity().type(type).build());
        final ProductEntity entity2 = entityManager.persistAndFlush(aProductEntity().type(type).build());
        final ProductEntity entity3 = entityManager.persistAndFlush(aProductEntity().type(2L).build());

        //when
        final List<ProductEntity> expected = repository.findByType(type);

        //then
        assertThat(expected).isNotEmpty();
        assertThat(expected).containsExactly(entity1, entity2);
        assertThat(expected).doesNotContain(entity3);
    }

    @Test
    void shouldFindByCodeIn() {
        // given
        final List<String> codes = Arrays.asList("CP1", "CP2");
        final ProductEntity entity1 = entityManager.persistAndFlush(aProductEntity().code("CP1").build());
        final ProductEntity entity2 = entityManager.persistAndFlush(aProductEntity().code("CP2").build());
        final ProductEntity entity3 = entityManager.persistAndFlush(aProductEntity().code("CP3").build());

        //when
        final List<ProductEntity> expected = repository.findByCodeIn(codes);

        //then
        assertThat(expected).isNotEmpty();
        assertThat(expected).containsExactly(entity1, entity2);
        assertThat(expected).doesNotContain(entity3);
    }
}
