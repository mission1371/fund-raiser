package com.eestienergia.fundraiser.rest.inventory;

import com.eestienergia.fundraiser.domain.exception.UnsupportedStockOperationException;
import com.eestienergia.fundraiser.domain.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<InventoryRecordResource> getInventory() {
        return service.fetchInventory().stream().map(converter::convert).collect(toList());
    }

    @PutMapping
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
