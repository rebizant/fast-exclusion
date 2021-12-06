package com.example;

/**
 * Single node of the linked list
 * Stored in the hashmap with a key as the value
 * and a reference to the next element,
 * thanks to that it is very fast and easy to get following elements
 */
class Node {

    String value;
    Integer index;
    Node next;

    Node(String value, Integer index) {
        this.value = value;
        this.index = index;
    }

}
