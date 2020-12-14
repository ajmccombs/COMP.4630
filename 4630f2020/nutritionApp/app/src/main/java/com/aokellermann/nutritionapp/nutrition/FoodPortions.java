package com.aokellermann.nutritionapp.nutrition;

import com.aokellermann.nutritionapp.common.DataSource;
import com.aokellermann.nutritionapp.common.FoodType;
import com.aokellermann.nutritionapp.common.Portion;

import java.io.File;
import java.util.Map;

/**
 * Provides methods for adding and getting portion sizes
 */
public interface FoodPortions {
    void add(Integer portionID, Integer foodID, Portion portion, FoodType foodType);

    /**
     * Adds a new portion size
     * @param portionID ID of the portion
     * @param foodID ID of the corresponding food
     * @param amount amount of units in the portion
     * @param description the description of the unit
     * @param mass the mass of the portion in grams
     */
    void add(Integer portionID, Integer foodID, Double amount, String description, Double mass, FoodType foodType);

    /**
     * Adds a new food portion source
     * @param file the file
     * @param source the source
     */
    void add(File file, DataSource source, FoodType foodType);

    /**
     * Gets all portions for the given food
     * @param foodID the ID of the food
     * @return map of portion IDs to portions
     */
    Map<Integer, Portion> get(Integer foodID, FoodType foodType);
}
