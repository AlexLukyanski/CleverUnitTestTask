package ru.clevertec.product.mapper.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mapstruct.factory.Mappers;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.mapper.ProductMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperImplTest {

    private ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Nested
    class ToProductMethodTest {

        @ParameterizedTest(name = "name={0}, description={1}, price={2}")
        @CsvFileSource(resources = "/to-product-mapper-input-data.csv", numLinesToSkip = 1)
        void should_ReturnProduct_when_ValidProductDTOPass(String name, String description, BigDecimal price) {
            //given
            ProductDto productDto = new ProductDto(name, description, price);

            //when
            Product productActual = productMapper.toProduct(productDto);

            //then
            assertThat(productActual).hasFieldOrPropertyWithValue(Product.Fields.name, productDto.name())
                    .hasFieldOrPropertyWithValue(Product.Fields.description, productDto.description())
                    .hasFieldOrPropertyWithValue(Product.Fields.price, productDto.price());
        }
    }

    @Nested
    class ToInfoProductDtoMethodTest {

        @ParameterizedTest(name = "uuid{0}, name={1}, description={2}, price={3}, created={4}")
        @CsvFileSource(resources = "/to-productdto-mapper-input-data.csv", numLinesToSkip = 1)
        void should_ReturnInfoProductDTO_when_ValidProductPass(String uuid, String name, String description, BigDecimal price, LocalDateTime created) {
            //given
            Product productTest = new Product(UUID.fromString(uuid), name, description, price, created);

            //when
            InfoProductDto infoProductDtoActual = productMapper.toInfoProductDto(productTest);

            //then
            assertThat(infoProductDtoActual).hasFieldOrPropertyWithValue(InfoProductDto.Fields.uuid, productTest.getUuid())
                    .hasFieldOrPropertyWithValue(InfoProductDto.Fields.name, productTest.getName())
                    .hasFieldOrPropertyWithValue(InfoProductDto.Fields.description, productTest.getDescription())
                    .hasFieldOrPropertyWithValue(InfoProductDto.Fields.price, productTest.getPrice());
        }
    }

    @Nested
    class MergeMethodTest {

        @Test
        void should_ReturnProduct_when_ArgumentsAreValid() {
            //given
            Product product = ProductToMergeTestData.builder().build().getProduct();
            ProductDto dto = ProductDtoToMergeTestData.builder().build().getProductDTO();

            //when
            Product actual = productMapper.merge(product, dto);

            //then
            assertThat(actual).hasFieldOrPropertyWithValue(Product.Fields.uuid, product.getUuid())
                    .hasFieldOrPropertyWithValue(Product.Fields.name, dto.name())
                    .hasFieldOrPropertyWithValue(Product.Fields.description, dto.description())
                    .hasFieldOrPropertyWithValue(Product.Fields.price, dto.price())
                    .hasFieldOrPropertyWithValue(Product.Fields.created, product.getCreated());
        }
    }
}