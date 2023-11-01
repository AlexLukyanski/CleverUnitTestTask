package ru.clevertec.product.mapper.impl;

import lombok.Builder;
import ru.clevertec.product.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder(setterPrefix = "with")
public class ProductToMergeTestData {

    @Builder.Default
    private UUID uuid = UUID.fromString("cd0f1d08-b411-495d-81a0-fdbaf403e8ae");

    @Builder.Default
    private String name = "Яблоко";

    @Builder.Default
    private String description = "Зеленое";

    @Builder.Default
    private BigDecimal price = new BigDecimal("2.56");

    @Builder.Default
    private LocalDateTime created = LocalDateTime.parse("2023-10-29T01:59:23");

    public Product getProduct() {
        Product product = new Product();
        product.setUuid(this.uuid);
        product.setName(this.name);
        product.setDescription(this.description);
        product.setPrice(this.price);
        product.setCreated(this.created);
        return product;
    }
}
