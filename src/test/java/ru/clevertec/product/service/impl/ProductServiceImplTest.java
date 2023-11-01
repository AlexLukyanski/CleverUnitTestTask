package ru.clevertec.product.service.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.data.InfoProductDto;
import ru.clevertec.product.data.ProductDto;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.mapper.ProductMapper;
import ru.clevertec.product.repository.ProductRepository;
import ru.clevertec.product.service.ProductTestData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private ProductServiceImpl productService;


    @Nested
    class GetMethodTest {

        @Test
        public void should_ReturnInfoProductDto_when_UuidIsValid() {
            //given
            Product product = ProductTestData.builder().build().getProduct();
            UUID uuid = product.getUuid();
            Optional<Product> optionalProduct = Optional.of(product);
            InfoProductDto expected = new InfoProductDto(product.getUuid(), product.getName(), product.getDescription(), product.getPrice());

            when(productRepository.findById(uuid))
                    .thenReturn(optionalProduct);
            when(productMapper.toInfoProductDto(optionalProduct.get()))
                    .thenReturn(expected);

            //when
            InfoProductDto actual = productService.get(uuid);

            //then
            assertEquals(expected, actual);
        }

        @Test
        public void should_InvokeProductRepositoryFindByID_when_InvokeMethod() {

            //given
            Product product = ProductTestData.builder().build().getProduct();
            InfoProductDto infoProductDto = new InfoProductDto(product.getUuid(), product.getName(), product.getDescription(), product.getPrice());

            when(productMapper.toInfoProductDto(product))
                    .thenReturn(infoProductDto);
            when(productRepository.findById(product.getUuid()))
                    .thenReturn(Optional.of(product));

            //when
            productService.get(product.getUuid());

            //then
            verify(productRepository).findById(product.getUuid());
        }

        @Test
        public void should_InvokeProductMapper_when_InvokeMethod() {

            //given
            Product product = ProductTestData.builder().build().getProduct();
            InfoProductDto infoProductDto = new InfoProductDto(product.getUuid(), product.getName(), product.getDescription(), product.getPrice());

            when(productMapper.toInfoProductDto(product))
                    .thenReturn(infoProductDto);
            when(productRepository.findById(product.getUuid()))
                    .thenReturn(Optional.of(product));

            //when
            productService.get(product.getUuid());

            //then
            verify(productMapper).toInfoProductDto(product);
        }
    }

    @Nested
    class GetAllMethodTest {

        @Test
        public void should_ReturnInfoProductDtoList_when_MethodInvoke() {
            //given
            Product product = ProductTestData.builder().build().getProduct();
            InfoProductDto infoProductDto = new InfoProductDto(product.getUuid(), product.getName(), product.getDescription(), product.getPrice());

            List<Product> listProduct = new ArrayList<>();
            listProduct.add(product);

            List<InfoProductDto> expected = new ArrayList<>();
            expected.add(infoProductDto);

            when(productRepository.findAll())
                    .thenReturn(listProduct);
            when(productMapper.toInfoProductDto(product))
                    .thenReturn(infoProductDto);

            //when
            List<InfoProductDto> actual = productService.getAll();

            //then
            assertEquals(expected, actual);
        }

        @Test
        public void should_InvokeProductRepositoryFindAll_when_InvokeMethod() {
            //given
            Product product = ProductTestData.builder().build().getProduct();
            InfoProductDto infoProductDto = new InfoProductDto(product.getUuid(), product.getName(), product.getDescription(), product.getPrice());
            List<Product> list = List.of(product);

            when(productRepository.findAll())
                    .thenReturn(list);
            when(productMapper.toInfoProductDto(list.get(0)))
                    .thenReturn(infoProductDto);

            //when
            productService.getAll();

            //then
            verify(productRepository).findAll();
        }

        @Test
        public void should_InvokeProductMapper_when_InvokeMethod() {
            //given
            Product product = ProductTestData.builder().build().getProduct();
            InfoProductDto infoProductDto = new InfoProductDto(product.getUuid(), product.getName(), product.getDescription(), product.getPrice());
            List<Product> list = List.of(product);

            when(productRepository.findAll())
                    .thenReturn(list);
            when(productMapper.toInfoProductDto(list.get(0)))
                    .thenReturn(infoProductDto);

            //when
            productService.getAll();

            //then
            verify(productMapper).toInfoProductDto(product);
        }
    }

    @Nested
    class CreateMethodTest {
        @Captor
        private ArgumentCaptor<Product> productCaptor;

        @Test
        public void should_ReturnUUID_when_MethodInvoke() {
            //given
            Product product = ProductTestData.builder().build().getProduct();
            ProductDto dto = new ProductDto(product.getName(), product.getDescription(), product.getPrice());
            UUID expected = UUID.fromString("07f6711f-225f-442c-b099-6c663b89d2ab");
            product.setUuid(expected);

            when(productRepository.save(product))
                    .thenReturn(product);
            when(productMapper.toProduct(dto))
                    .thenReturn(product);

            //when
            UUID actual = productService.create(dto);

            //then
            assertEquals(expected, actual);
        }

        @Test
        public void should_ReturnNewUUID_when_MethodInvoke() {

            //given
            Product productWithoutUUID = ProductTestData.builder().uuid(null).build().getProduct();
            Product productWithUUID = ProductTestData.builder().build().getProduct();
            ProductDto dto = new ProductDto(productWithoutUUID.getName()
                    , productWithoutUUID.getDescription()
                    , productWithoutUUID.getPrice());
            UUID expected = productWithUUID.getUuid();

            when(productRepository.save(productWithoutUUID))
                    .thenReturn(productWithUUID);
            when(productMapper.toProduct(dto))
                    .thenReturn(productWithoutUUID);

            //when
            productService.create(dto);

            //then
            verify(productRepository)
                    .save(productCaptor.capture());
            assertNotEquals(expected, productCaptor.getValue().getUuid());
        }

        @Test
        public void should_InvokeProductRepositorySave_when_InvokeMethod() {
            //given
            Product product = ProductTestData.builder().build().getProduct();
            ProductDto dto = new ProductDto(product.getName()
                    , product.getDescription()
                    , product.getPrice());

            when(productRepository.save(product))
                    .thenReturn(product);
            when(productMapper.toProduct(dto))
                    .thenReturn(product);

            //when
            productService.create(dto);

            //then
            verify(productRepository).save(product);
        }

    }

    @Nested
    class UpdateMethodTest {

        @Test
        public void should_InvokeProductRepositorySave_when_InvokeMethod() {
            //given
            Product product = ProductTestData.builder()
                    .name(null)
                    .description(null)
                    .price(null)
                    .created(null)
                    .build()
                    .getProduct();
            ProductDto dto = new ProductDto(product.getName()
                    , product.getDescription()
                    , product.getPrice());

            when(productMapper.merge(product, dto))
                    .thenReturn(product);
            when(productRepository.save(product))
                    .thenReturn(product);

            //when
            productService.update(product.getUuid(), dto);

            //then
            verify(productRepository).save(product);
        }

        @Test
        public void should_InvokeProductMapper_when_InvokeMethod() {
            //given
            Product product = ProductTestData.builder()
                    .name(null)
                    .description(null)
                    .price(null)
                    .created(null)
                    .build()
                    .getProduct();
            ProductDto dto = new ProductDto(product.getName()
                    , product.getDescription()
                    , product.getPrice());

            when(productMapper.merge(product, dto))
                    .thenReturn(product);
            when(productRepository.save(product))
                    .thenReturn(product);

            //when
            productService.update(product.getUuid(), dto);

            //then
            verify(productMapper).merge(product, dto);
        }
    }

    @Nested
    class DeleteMethodTest {

        @Test
        public void should_InvokeProductRepositoryDelete_when_InvokeMethod() {
            //given
            UUID uuid = UUID.fromString("e2599420-6aa9-4731-be40-783ed9051457");

            //when
            productService.delete(uuid);

            //then
            verify(productRepository).delete(uuid);
        }
    }
}