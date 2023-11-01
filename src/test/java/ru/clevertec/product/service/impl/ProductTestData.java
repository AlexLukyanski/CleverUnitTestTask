package ru.clevertec.product.service;

import lombok.Builder;
import ru.clevertec.product.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public class ProductTestData {

    @Builder.Default
    private UUID uuid = UUID.fromString("e2599420-6aa9-4731-be40-783ed9051457");

    @Builder.Default
    private String name = "Вишня";

    @Builder.Default
    private String description = "С косточкой";

    @Builder.Default
    private BigDecimal price = new BigDecimal("4.33");

    @Builder.Default
    private LocalDateTime created = LocalDateTime.parse("2023-10-29T01:59:23");

    public Product getProduct() {
        return new Product(uuid, name, description, price, created);
    }
}
