package com.eestienergia.fundraiser.rest.product;

import com.eestienergia.fundraiser.domain.Product;
import com.eestienergia.fundraiser.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.activation.MimetypesFileTypeMap;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.stream.Collectors.toList;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductResourceConverter converter;
    private final ProductService service;
    private final MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();

    @GetMapping(path = "/catalog/{type}")
    public List<ProductResource> getByProductType(@NotNull @PathVariable final Long type) {
        return service.getByProductType(type).stream().map(converter::convert).collect(toList());
    }

    @GetMapping(path = "/product/{productCode}/image")
    public ResponseEntity<Resource> getProductImage(@NotNull @PathVariable(name = "productCode") final String code) {
        final Product product = service.getByCode(code);
        if (product != null) {
            try {
                final File file = new File(product.getImageRelativePath());
                final Path filePath = Paths.get(file.getAbsolutePath());
                return ResponseEntity.ok()
                        .contentLength(file.length())
                        .cacheControl(CacheControl.maxAge(1, TimeUnit.DAYS).cachePrivate())
                        .contentType(MediaType.parseMediaType(mimeTypesMap.getContentType(file)))
                        .body(new ByteArrayResource(Files.readAllBytes(filePath)));
            } catch (IOException e) {
                e.printStackTrace();
                log.error("Could not resolve image from {}", product.getImageRelativePath());
            }
        }
        return ResponseEntity.noContent().build();
    }

}
