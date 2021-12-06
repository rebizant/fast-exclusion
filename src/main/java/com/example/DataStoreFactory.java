package com.example;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.service.CacheService;
import com.example.service.DataProvider;

public class DataStoreFactory {

    private final DataProvider dataProvider;
    private final CacheService<Map<String, Node>> cacheService;

    public DataStoreFactory(DataProvider dataProvider, CacheService<Map<String, Node>> cacheService) {
        this.dataProvider = dataProvider;
        this.cacheService = cacheService;
    }

    public Map<String, Node> init() {
        Map<String, Node> store = prepareMap(dataProvider.getData());
        cacheService.putInCache("store", store);
        return store;
    }

    private Map<String, Node> prepareMap(List<String> values) {

        if (values == null || values.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, Node> store = new HashMap<>(values.size());
        Node previousNode = new Node(values.get(0), 0);
        store.put(previousNode.value, previousNode);
        int size = values.size();
        for (int i = 1; i < size; i++) {
            Node newNode = new Node(values.get(i), i);
            previousNode.next = newNode;
            store.put(newNode.value, newNode);
            previousNode = newNode;
        }
        return store;
    }

    public DataStore getDataStore() {
        Map<String, Node> store = cacheService.getFromCache("store").orElseGet(this::init);
        return new DataStore(store);
    }

}
