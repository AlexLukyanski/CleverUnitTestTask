package ru.clevertec.product.mapper.impl;

import lombok.Builder;
import ru.clevertec.product.data.ProductDto;

import java.math.BigDecimal;

@Builder(setterPrefix = "with")
public class ProductDtoToMergeTestData {

    @Builder.Default
    String name = "Перец";

    @Builder.Default
    String description = "Красный";

    @Builder.Default
    BigDecimal price = new BigDecimal("10.22");

    public ProductDto getProductDTO() {
        return new ProductDto(
                this.name,
                this.description,
                this.price
        );
    }
}
