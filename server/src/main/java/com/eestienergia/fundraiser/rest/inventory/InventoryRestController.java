package com.eestienergia.fundraiser.rest.inventory;

import com.eestienergia.fundraiser.domain.exception.UnsupportedStockOperationException;
import com.eestienergia.fundraiser.domain.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/inventory")
public class InventoryRestController {

    private final InventoryService service;
    private final InventoryRecordResourceConverter converter;

    @GetMapping
    @Operation(summary = "Get all products with stock information")
    public List<InventoryRecordResource> getInventory() {
        return service.fetchInventory().stream().map(converter::convert).collect(toList());
    }

    @PutMapping
    @Operation(summary = "Update stock of the product")
    public InventoryRecordResource updateStock(@RequestBody @Valid final InventoryUpdateRequestResource resource) {
        if (resource.getAddedStock() != null) {
            return converter.convert(service.increaseStock(resource.getProductCode(), resource.getAddedStock()));
        } else if (resource.getStock() != null) {
            return converter.convert(service.updateStock(resource.getProductCode(), resource.getStock()));
        } else {
            throw new UnsupportedStockOperationException();
        }
    }

}
