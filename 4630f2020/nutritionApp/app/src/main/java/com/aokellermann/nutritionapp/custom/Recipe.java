package com.aokellermann.nutritionapp.custom;

import com.aokellermann.nutritionapp.common.Portion;
import com.aokellermann.nutritionapp.common.Serving;

import java.io.Serializable;
import java.util.Map;

public interface Recipe extends Serializable {
    String getDescription();

    void setDescription(String newDescription);

    Map<Integer, Serving> getIngredients();

    void putIngredient(Integer foodID, Serving serving);
}
