package com.aokellermann.nutritionapp.custom;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aokellermann.nutritionapp.common.Serving;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RecipeImpl implements Recipe {
    private String description;
    private final Map<Integer, Serving> ingredients;

    public RecipeImpl(String description) {
        this.description = description;
        this.ingredients = new HashMap<>();
    }

    public RecipeImpl(String description, Map<Integer, Serving> ingredients) {
        this.description = description;
        this.ingredients = ingredients;
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
    public Map<Integer, Serving> getIngredients() {
        return ingredients;
    }

    @Override
    public void putIngredient(Integer foodID, Serving serving) {
        ingredients.put(foodID, serving);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        RecipeImpl other = (RecipeImpl) obj;
        return this.description.equals(other.description) && this.ingredients.equals(other.ingredients);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.US, "%s: [%s]", description, ingredients);
    }
}
