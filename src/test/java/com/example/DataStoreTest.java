package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.service.CacheService;
import com.example.service.DataProvider;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DataStoreTest {

    /**
     * Sample test with:
     * - 500 000 elements
     * - excluding about 100 000 elements from initial list
     * - getting following 300 element after exclusion
     * <p>
     * Sample computation time is :
     * - about 60 ms to prepare initial map
     * - about 24 ms to mark element as excluded and retrieving attributes
     */
    @Test
    void sampleTest() {
        DataProvider dataProvider = Mockito.mock(DataProvider.class);
        int size = 500000;
        List<String> data = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            data.add("sample-value-" + i);
        }
        Mockito.when(dataProvider.getData()).thenReturn(data);

        DataStoreFactory factory = new DataStoreFactory(dataProvider, new CacheService<>());
        long current = System.currentTimeMillis();
        factory.init();
        System.out.println(" preparing time : " + (System.currentTimeMillis() - current) + " ms");

        DataStore dataStore = factory.getDataStore();

        List<String> attributesToRemove = data.subList(101, 100000);
        current = System.currentTimeMillis();
        dataStore.removeAll(attributesToRemove);
        List<String> nextFollowingElements = dataStore.getNextFollowingElements("sample-value-99", 300);
        System.out.println(" computation time : " + (System.currentTimeMillis() - current) + " ms");
        System.out.println(Arrays.toString(nextFollowingElements.toArray()));
        System.out.println("Size : " + nextFollowingElements.size());
    }

}
