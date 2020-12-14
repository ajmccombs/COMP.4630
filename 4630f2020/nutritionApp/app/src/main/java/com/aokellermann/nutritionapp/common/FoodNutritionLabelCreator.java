package com.aokellermann.nutritionapp.common;

import com.aokellermann.nutritionapp.descriptions.MultiSourceDescriptions;
import com.aokellermann.nutritionapp.nutrition.FoodNutrients;
import com.aokellermann.nutritionapp.nutrition.NutrientValue;
import com.aokellermann.nutritionapp.nutrition.Nutrients;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FoodNutritionLabelCreator {
    private final MultiSourceDescriptions descriptions;
    private final Nutrients nutrients;
    private final FoodNutrients foodNutrients;

    public FoodNutritionLabelCreator(MultiSourceDescriptions descriptions, Nutrients nutrients, FoodNutrients foodNutrients) {
        this.descriptions = descriptions;
        this.nutrients = nutrients;
        this.foodNutrients = foodNutrients;
    }

    /**
     * Creates a nutrition label for a serving size
     * @param foodID the food ID
     * @param servingMass the mass of the serving in grams
     * @return a nutrition label
     */
    public NutritionLabel createFoodLabel(Integer foodID, Double servingMass) {
        double divisor = 100.0 / servingMass;

        Map<Nutrients.Group, Map<Nutrients.Type, Set<Integer>>> stratified = nutrients.getStratified();
        Map<Integer, Double> foodNutrition = foodNutrients.get(foodID);

        String description = descriptions.getDescription(foodID, FoodType.GENERIC);
        Map<Nutrients.Group, Map<Nutrients.Type, NutrientValue>> nutrientsMap = new HashMap<>();

        for (Map.Entry<Nutrients.Group, Map<Nutrients.Type, Set<Integer>>> groupEntry : stratified.entrySet()) {
            nutrientsMap.put(groupEntry.getKey(), new HashMap<>());
            Map<Nutrients.Type, NutrientValue> map = nutrientsMap.get(groupEntry.getKey());

            for (Map.Entry<Nutrients.Type, Set<Integer>> typeEntry : groupEntry.getValue().entrySet()) {
                for (Integer nutrientID : typeEntry.getValue()) {
                    Double amount = foodNutrition.get(nutrientID);
                    if (amount != null) {
                        amount /= divisor;
                        NutrientValue val = new NutrientValue(nutrients.get(nutrientID).getUnit(), amount);
                        if (val.getUnit() == Unit.INTERNATIONAL_UNIT || map.get(typeEntry.getKey()) == null) {
                            map.put(typeEntry.getKey(), val);
                        }
                    }
                }
            }
        }
        return new NutritionLabel(foodID, description, nutrientsMap);
    }

    /**
     * Creates a nutrition label for an 100g serving
     * @param foodID the food ID
     * @return a nutrition label
     */
    public NutritionLabel createFoodLabel(Integer foodID) {
        return createFoodLabel(foodID, 100.0);
    }
}
