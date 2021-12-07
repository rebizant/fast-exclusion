package com.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.service.CacheService;
import com.example.service.DataProvider;

public class DataStoreFactory {

    private final DataProvider dataProvider;
    private final CacheService<ComplexStore> cacheService;

    public DataStoreFactory(DataProvider dataProvider, CacheService<ComplexStore> cacheService) {
        this.dataProvider = dataProvider;
        this.cacheService = cacheService;
    }

    public ComplexStore init() {
        ComplexStore store = prepareMap(dataProvider.getData());
        cacheService.putInCache("store", store);
        return store;
    }

    /**
     * Initial preparation of a hashmap and a simple implementation
     * of the linked list over the hashmap
     * Thanks to that, checking if value is in the list is very fast (constant time independent of the size of the list)
     *
     * @param values
     * @return
     */
    private ComplexStore prepareMap(List<String> values) {

        if (values == null || values.isEmpty()) {
            return new ComplexStore();
        }
        Map<String, Node> store = new HashMap<>(values.size());
        Node previousNode = new Node(values.get(0), 0);
        ComplexStore complexStore = new ComplexStore(store, previousNode);
        store.put(previousNode.value, previousNode);
        int size = values.size();
        // preparing the linked list with fast elements retrieval by the value
        for (int i = 1; i < size; i++) {
            Node newNode = new Node(values.get(i), i);
            previousNode.next = newNode;
            store.put(newNode.value, newNode);
            previousNode = newNode;
        }
        return complexStore;
    }

    public DataStore getDataStore() {
        ComplexStore store = cacheService.getFromCache("store").orElseGet(this::init);
        return new DataStore(store.getFirstEntry(), store.getStore());
    }

}
