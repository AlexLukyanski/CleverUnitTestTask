package ru.clevertec.product.repository.impl;

import lombok.RequiredArgsConstructor;
import ru.clevertec.product.entity.Product;
import ru.clevertec.product.repository.DataContainer;
import ru.clevertec.product.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class InMemoryProductRepository implements ProductRepository {

    private DataContainer dataContainer = DataContainer.getInstance();

    @Override
    public Optional<Product> findById(UUID uuid) {
        return Optional.of(dataContainer.getMapWithData().get(uuid));
    }

    @Override
    public List<Product> findAll() {
        return dataContainer.getMapWithData().values().stream()
                .toList();
    }

    @Override
    public Product save(Product product) {
        UUID uuid = product.getUuid();
        dataContainer.getMapWithData().put(uuid, product);
        return dataContainer.getMapWithData().get(uuid);
    }

    @Override
    public void delete(UUID uuid) {
        dataContainer.getMapWithData().put(uuid, null);
    }
}

