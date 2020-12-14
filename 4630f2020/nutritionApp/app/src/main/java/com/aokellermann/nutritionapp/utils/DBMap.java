package com.aokellermann.nutritionapp.utils;

import java.io.Serializable;
import java.util.Map;

public interface DBMap<V> extends Serializable {
    Map<Integer, V> get();

    V get(Integer id);

    Integer add(V val);
}
