package com.aokellermann.nutritionapp.descriptions;

import com.aokellermann.nutritionapp.common.DataSource;
import com.aokellermann.nutritionapp.common.FoodType;
import com.aokellermann.nutritionapp.custom.Food;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;

/**
 * Provides methods for a user to search for food products of different types.
 */
public interface MultiSourceDescriptions {
    /**
     * Adds a food to the database.
     *
     * @param id          the ID of the food or recipe
     * @param description a description of the food or recipe
     * @param type        the type of food or recipe
     */
    void add(Integer id, String description, FoodType type);

    /**
     * Adds a new food or recipe source to the database.
     *
     * @param file   the file in which the data resides
     * @param source the type of data source
     */
    void add(File file, DataSource source, FoodType foodType);

    void add(InputStream is, DataSource source, FoodType foodType);

    /**
     * Given an ID, returns a description of the product.
     *
     * @param id the id of the food or recipe
     * @return English description
     */
    String getDescription(Integer id, FoodType type);

    /**
     * Given a query containing keywords, returns a collection of IDs of relevant products, ordered
     * from highest relevance to lowest.
     *
     * @param query the keywords to search with
     * @return relevant ordered IDs
     */
    Collection<Integer> searchKeywords(String query, FoodType type);
}
