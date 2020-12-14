package com.aokellermann.nutritionapp.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of a generic trie.
 *
 * @param <V> the value type
 */
public class Trie<V> {
    private final Set<V> ids = new HashSet<>();
    private final Map<Character, Trie<V>> children = new HashMap<>();

    /**
     * Adds a value to the trie.
     *
     * @param key
     * @param value
     */
    public void add(String key, V value) {
        Trie<V> walk = this;
        for (Character c : key.toCharArray()) {
            assert walk != null;
            walk.children.putIfAbsent(c, new Trie<>());
            walk = walk.children.get(c);
        }

        assert walk != null;
        walk.ids.add(value);
    }

    /**
     * Adds a collection of keys with the same value.
     *
     * @param keys
     * @param value
     */
    public void addAll(Collection<String> keys, V value) {
        for (String key : keys) {
            add(key, value);
        }
    }

    /**
     * Returns an ordered list of value sets for the given search prefix.
     *
     * @param key the prefix
     * @return ordered list of value sets
     */
    public List<Set<V>> searchPrefix(String key) {
        Trie<V> walk = this;
        for (Character c : key.toCharArray()) {
            walk = walk.children.get(c);
            if (walk == null) {
                return new LinkedList<>();
            }
        }

        return walk.aggregateIDs();
    }

    private List<Set<V>> aggregateIDs() {
        List<Set<V>> prioritizedIDs = new LinkedList<>();
        prioritizedIDs.add(ids);
        for (Trie<V> child : children.values()) {
            prioritizedIDs.addAll(child.aggregateIDs());
        }

        return prioritizedIDs;
    }
}
