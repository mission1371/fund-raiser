package com.eestienergia.fundraiser.rest.inventory;

import com.eestienergia.fundraiser.domain.exception.UnsupportedStockOperationException;
import com.eestienergia.fundraiser.domain.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/inventory")
public class InventoryRestController {

    private final InventoryService service;
    private final InventoryRecordResourceConverter converter;

    @GetMapping
    public List<InventoryRecordResource> getInventory() {
        return service.fetchInventory().stream().map(converter::convert).collect(toList());
    }

    @PutMapping
    public InventoryRecordResource updateStock(@RequestBody @Valid @NotNull final InventoryUpdateRequestResource resource) {
        if (resource.getAddedStock() != null) {
            return converter.convert(service.increaseStock(resource.getProductCode(), resource.getAddedStock()));
        } else if (resource.getStock() != null) {
            return converter.convert(service.updateStock(resource.getProductCode(), resource.getStock()));
        } else {
            throw new UnsupportedStockOperationException();
        }
    }

}
