package com.example;

import java.util.Map;

public class ComplexStore {

    private final Map<String, Node> store;
    private final Node firstEntry;

    public ComplexStore() {
        store = null;
        firstEntry = null;
    }

    public ComplexStore(Map<String, Node> store, Node firstEntry) {
        this.store = store;
        this.firstEntry = firstEntry;
    }

    public Map<String, Node> getStore() {
        return store;
    }

    public Node getFirstEntry() {
        return firstEntry;
    }

    public boolean isEmpty() {
        return firstEntry == null;
    }

}
