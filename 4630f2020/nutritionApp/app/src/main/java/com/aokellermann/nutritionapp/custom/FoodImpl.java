package com.aokellermann.nutritionapp.custom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FoodImpl implements Food {
    private final Map<Integer, Double> nutrients;
    private String description;

    public FoodImpl(String description) {
        this.description = description;
        this.nutrients = new HashMap<>();
    }

    public FoodImpl(String description, Map<Integer, Double> nutrients) {
        this.description = description;
        this.nutrients = nutrients;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    @Override
    public Map<Integer, Double> getNutrients() {
        return nutrients;
    }

    @Override
    public void putNutrient(Integer nutrientID, Double amount) {
        nutrients.put(nutrientID, amount);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.US, "%s: [%s]", description, nutrients);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return this.description.equals(((FoodImpl) obj).description) && this.nutrients.equals(((FoodImpl) obj).nutrients);
    }
}
