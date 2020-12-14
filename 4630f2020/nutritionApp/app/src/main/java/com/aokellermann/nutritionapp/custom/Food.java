package com.aokellermann.nutritionapp.custom;

import java.io.Serializable;
import java.util.Map;

/**
 * A custom food item.
 */
public interface Food extends Serializable {
    String getDescription();

    void setDescription(String newDescription);

    Map<Integer, Double> getNutrients();

    void putNutrient(Integer nutrientID, Double amount);
}
