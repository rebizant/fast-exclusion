package com.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Data store holding the linked list and a boolean table for marking
 * values as removed. Thanks to that the main map (linked list) is not modified
 * thus can be cached and shared among invocations
 */
public class DataStore {

    private final Boolean[] deletedMarker;
    private final Node firstElement;
    private final Map<String, Node> store;

    public DataStore(Node firstElement, Map<String, Node> store) {
        this.firstElement = firstElement;
        this.store = store;
        this.deletedMarker = new Boolean[store.size()];
    }

    /**
     * computation complexity O(n)
     *
     * @param attributesToRemove
     */
    public void removeAll(Collection<String> attributesToRemove) {
        // element are not removed only marked as delete in the external table
        attributesToRemove.forEach(key -> Optional.ofNullable(store.get(key)).ifPresent(node -> deletedMarker[node.index] = Boolean.TRUE));
    }

    /**
     * Thanks to using hashmap and a table
     * the computation complexity is about O(quantity) so is independent of the size of the value list
     *
     * @param attribute value after which the consequent element should be extracted (excluding this value)
     * @param quantity  how many consequent values should be returned
     * @return
     */
    public List<String> getNextFollowingElements(String attribute, int quantity) {

        if (quantity <= 0) {
            return Collections.emptyList();
        }

        if (attribute == null) {
            return getElements(quantity, firstElement);
        }
        Node node = store.get(attribute);
        if (node == null) {
            return Collections.emptyList();
        }
        return getElements(quantity, node.next);
    }

    private List<String> getElements(int quantity, Node node) {
        if (node == null) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>(quantity);
        while (node != null && quantity > 0) {
            if (!isDeleted(node)) {
                quantity--;
                result.add(node.value);
            }
            node = node.next;
        }
        return result;
    }

    boolean isDeleted(Node node) {
        return Optional.ofNullable(deletedMarker[node.index]).orElse(Boolean.FALSE);
    }

}
