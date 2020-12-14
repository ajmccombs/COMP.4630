package com.aokellermann.nutritionapp.common;

import com.aokellermann.nutritionapp.nutrition.NutrientValue;
import com.aokellermann.nutritionapp.nutrition.Nutrients;

import java.util.HashMap;
import java.util.Map;

public class NutritionLabel {
    private final Integer id;
    private final String description;
    private final Map<Nutrients.Group, Map<Nutrients.Type, NutrientValue>> nutrients;

    public NutritionLabel(Integer id, String description) {
        this.id = id;
        this.description = description;
        this.nutrients = new HashMap<>();
    }

    public NutritionLabel(Integer id, String description, Map<Nutrients.Group, Map<Nutrients.Type, NutrientValue>> nutrients) {
        this.id = id;
        this.description = description;
        this.nutrients = nutrients;
    }

    public Integer getId() {
        return this.id;
    }

    public String getDescription() {
        return description;
    }

    public Map<Nutrients.Group, Map<Nutrients.Type, NutrientValue>> getNutrients() {
        return nutrients;
    }
}
