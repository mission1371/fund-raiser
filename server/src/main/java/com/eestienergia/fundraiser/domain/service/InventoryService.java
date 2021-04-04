package com.eestienergia.fundraiser.domain.service;

import com.eestienergia.fundraiser.domain.InventoryRecord;
import com.eestienergia.fundraiser.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ProductService service;

    public List<InventoryRecord> fetchInventory() {
        return service.getProducts().stream().map(InventoryRecord::of).collect(toList());
    }

    public InventoryRecord increaseStock(final String productCode, final long amount) {
        final Product product = service.getByCode(productCode);
        return InventoryRecord.of(service.increaseStock(product, amount));
    }

    public InventoryRecord updateStock(final String productCode, final long amount) {
        final Product product = service.getByCode(productCode);
        return InventoryRecord.of(service.updateStock(product, amount));
    }
}
