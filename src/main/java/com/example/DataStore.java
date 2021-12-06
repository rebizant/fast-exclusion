package com.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DataStore {

    private final Boolean[] deletedMarker;
    private final Map<String, Node> store;

    public DataStore(Map<String, Node> store) {
        this.store = store;
        this.deletedMarker = new Boolean[store.size()];
    }

    public void removeAll(Collection<String> attributesToRemove) {
        attributesToRemove.forEach(key -> Optional.ofNullable(store.get(key)).ifPresent(node -> deletedMarker[node.index] = Boolean.TRUE));
    }

    public List<String> getNextFollowingElements(String attribute, int quantity) {
        Node node = store.get(attribute);
        if (node == null) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>(quantity);
        while (node.next != null && quantity > 0) {
            node = node.next;
            if (!isDeleted(node)) {
                quantity--;
                result.add(node.value);
            }
        }
        return result;
    }

    boolean isDeleted(Node node) {
        return Optional.ofNullable(deletedMarker[node.index]).orElse(Boolean.FALSE);
    }

}
