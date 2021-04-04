package com.eestienergia.fundraiser.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductEntityRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByCode(String code);

    List<ProductEntity> findByType(Long type);

    List<ProductEntity> findByCodeIn(Collection<String> codes);
}
