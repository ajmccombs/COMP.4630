package com.aokellermann.nutritionapp.common;

import com.aokellermann.nutritionapp.custom.Recipe;
import com.aokellermann.nutritionapp.nutrition.NutrientValue;
import com.aokellermann.nutritionapp.nutrition.Nutrients;
import com.aokellermann.nutritionapp.utils.DBMap;

import java.util.HashMap;
import java.util.Map;

public class RecipeNutritionLabelCreator {
    private final DBMap<Recipe> recipes;
    private final DBMap<Map<Integer, Portion>> portions;
    private final FoodNutritionLabelCreator creator;

    public RecipeNutritionLabelCreator(DBMap<Recipe> recipes, DBMap<Map<Integer, Portion>> portions, FoodNutritionLabelCreator creator) {
        this.recipes = recipes;
        this.portions = portions;
        this.creator = creator;
    }

    /**
     * Creates a nutrition label for a serving of a recipe
     *
     * @param recipeID    the recipe ID
     * @param servingMass the mass of the serving in grams
     * @return a nutrition label
     */
    public NutritionLabel createRecipeLabel(Integer recipeID, Double servingMass) {
        Recipe recipe = recipes.get(recipeID);
        Map<Integer, Serving> ingredients = recipe.getIngredients();

        NutritionLabel label = new NutritionLabel(recipeID, recipe.getDescription());
        Map<Nutrients.Group, Map<Nutrients.Type, NutrientValue>> nutrients = label.getNutrients();

        double recipeMass = 0.0;
        for (Map.Entry<Integer, Serving> entry : ingredients.entrySet()) {
            double foodMass = entry.getValue().amount * portions.get(entry.getKey()).get(entry.getValue().portionID).mass;
            recipeMass += foodMass;
            NutritionLabel foodLabel = creator.createFoodLabel(entry.getKey(), foodMass);

            for (Map.Entry<Nutrients.Group, Map<Nutrients.Type, NutrientValue>> entry1 : foodLabel.getNutrients().entrySet()) {
                nutrients.putIfAbsent(entry1.getKey(), new HashMap<>());
                Map<Nutrients.Type, NutrientValue> nutMap = nutrients.get(entry1.getKey());
                for (Map.Entry<Nutrients.Type, NutrientValue> entry2 : entry1.getValue().entrySet()) {
                    nutMap.merge(entry2.getKey(), entry2.getValue(), NutrientValue::sum);
                }
            }
        }

        if (servingMass != null) {
            double divisor = recipeMass / servingMass;

            for (Map.Entry<Nutrients.Group, Map<Nutrients.Type, NutrientValue>> entry1 : nutrients.entrySet()) {
                for (Map.Entry<Nutrients.Type, NutrientValue> entry2 : entry1.getValue().entrySet()) {
                    entry2.setValue(new NutrientValue(entry2.getValue().getUnit(), entry2.getValue().getValue() / divisor));
                }
            }
        }

        return label;
    }

    /**
     * Creates a nutrition label for an entire recipe
     *
     * @param recipeID the recipe ID
     * @return a nutrition label
     */
    public NutritionLabel createRecipeLabel(Integer recipeID) {
        return createRecipeLabel(recipeID, null);
    }
}
