package com.aokellermann.nutritionapp.nutrition;

import com.aokellermann.nutritionapp.common.DataSource;
import com.aokellermann.nutritionapp.custom.Food;

import java.io.File;
import java.util.Map;

/**
 * Provides methods for managing the nutrients in food products
 */
public interface FoodNutrients {
    /**
     * Adds a nutrient for the given food.
     * @param id the ID of the food
     * @param nutrientID the nutrient ID of the nutrient that is being added
     * @param amount the amount of the nutrient that is being added
     */
    void add(Integer id, Integer nutrientID, Double amount);

    /**
     * Adds a data source of nutrients.
     * @param file the file containing food nutrient data
     * @param source the data source
     */
    void add(File file, DataSource source);

    /**
     * Gets the nutrients of a given food.
     * @param id the ID of the food
     * @return a map of nutrient ID to amount for the requested food
     */
    Map<Integer, Double> get(Integer id);
}
