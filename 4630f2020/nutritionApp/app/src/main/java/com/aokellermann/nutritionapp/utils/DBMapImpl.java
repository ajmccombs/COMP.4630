package com.aokellermann.nutritionapp.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DBMapImpl<V> implements DBMap<V> {
    private final Map<Integer, V> map = new HashMap<>();
    private Integer nextID = 1000000000;

    @Override
    public Map<Integer, V> get() {
        return map;
    }

    @Override
    public V get(Integer id) {
        return map.get(id);
    }

    @Override
    public Integer add(V val) {
        map.put(nextID, val);
        return nextID++;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return this.map.equals(((DBMapImpl<V>) obj).map) && this.nextID.equals(((DBMapImpl<V>) obj).nextID);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.US, "db: [%s], nextID: [%d]", map, nextID);
    }
}
