package ru.clevertec.product.repository.impl;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.repository.DataContainer;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InMemoryProductRepositoryTest {

    @InjectMocks
    private InMemoryProductRepository productRepository;
    @Mock
    private DataContainer dataContainer;

    @Nested
    class FindByIdMethodTest {

        @ParameterizedTest
        @ValueSource(strings = {"18caf7a9-8719-4cae-816c-e11a778a074e"})
        public void should_NotThrowException_when_InvokeMethod(String id) {
            //given
            UUID uuid = UUID.fromString(id);
            Map<UUID, Product> map = new HashMap<>();
            Product product = new Product();
            product.setUuid(uuid);
            map.put(uuid, product);

            when(dataContainer.getMapWithData()).thenReturn(map);

            //when
            Executable executable = () -> productRepository.findById(uuid);

            //then
            assertDoesNotThrow(executable);
        }
    }

    @Nested
    class FindAllMethodTest {

        @Test
        public void should_NotThrowException_when_InvokeMethod() {
            //given

            //when
            Executable executable = () -> productRepository.findAll();

            //then
            assertDoesNotThrow(executable);
        }
    }

    @Nested
    class SaveMethodTest {

        @Test
        public void should_NotThrowException_when_InvokeMethod() {
            //given
            Product product = new Product();

            //when
            Executable executable = () -> productRepository.save(product);

            //then
            assertDoesNotThrow(executable);
        }
    }

    @Nested
    class DeleteMethodTest {

        @ParameterizedTest
        @ValueSource(strings = {"18caf7a9-8719-4cae-816c-e11a778a074e"})
        public void should_NotThrowException_when_InvokeMethod(String id) {
            //given
            UUID uuid = UUID.fromString(id);

            //when
            Executable executable = () -> productRepository.delete(uuid);

            //then
            assertDoesNotThrow(executable);
        }
    }
}