package com.aokellermann.nutritionapp.descriptions;

import java.util.Collection;

/**
 * Provides methods for a user to search for food products.
 */
public interface Descriptions {
    /**
     * Adds the given ID and description to the database.
     *
     * @param id          the ID of the new product
     * @param description a description of the new product
     */
    void add(Integer id, String description);

    /**
     * Given an ID, returns a description of the product.
     *
     * @param id the id of the product
     * @return English description
     */
    String getDescription(Integer id);

    /**
     * Given a query containing keywords, returns a collection of IDs of relevant products, ordered
     * from highest relevance to lowest.
     *
     * @param query the keywords to search with
     * @return relevant ordered IDs
     */
    Collection<Integer> searchKeywords(String query);
}
