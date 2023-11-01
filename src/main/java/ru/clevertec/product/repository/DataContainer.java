package ru.clevertec.product.repository;

import lombok.Getter;
import ru.clevertec.product.entity.Product;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public final class DataContainer {

    private final Map<UUID, Product> mapWithData = new HashMap<>();

    private DataContainer() {
    }

    public static DataContainer getInstance() {
        return DataContainerHelper.instance;
    }

    public static class DataContainerHelper {

        private final static DataContainer instance = new DataContainer();
    }
}
