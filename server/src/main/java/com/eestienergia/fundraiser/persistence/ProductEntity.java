package com.eestienergia.fundraiser.persistence;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRODUCT")
public class ProductEntity {

    private static final String SEQUENCE_NAME = "SEQ_PRODUCT_ID";
    private static final String SEQUENCE_GENERATOR_NAME = "productEntitySequenceGenerator";

    @Id
    @NotNull
    @Column(name = "ID", nullable = false)
    @SequenceGenerator(name = SEQUENCE_GENERATOR_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    @GeneratedValue(generator = SEQUENCE_GENERATOR_NAME, strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Column(name = "TYPE", nullable = false)
    private Long type;

    @NotNull
    @Length(min = 1, max = 255)
    @Column(name = "CODE", nullable = false)
    private String code;

    @NotNull
    @Length(min = 1, max = 1024)
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotNull
    @Column(name = "PRICE", nullable = false, precision = 20, scale = 2)
    private BigDecimal price;

    @NotNull
    @Length(min = 3, max = 3)
    @Column(name = "CURRENCY_CODE", nullable = false)
    private String currencyCode;

    @Min(0)
    @NotNull
    @Column(name = "STOCK", nullable = false)
    private long stock;

    @NotNull
    @Column(name = "IMAGE_RELATIVE_PATH", nullable = false)
    private String imageRelativePath;
}
