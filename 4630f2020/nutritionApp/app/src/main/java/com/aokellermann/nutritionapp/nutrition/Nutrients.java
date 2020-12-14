package com.aokellermann.nutritionapp.nutrition;

import com.aokellermann.nutritionapp.common.DataSource;
import com.aokellermann.nutritionapp.common.Unit;

import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 * Provides methods for managing nutrients.
 */
public interface Nutrients {
    /**
     * Adds a new nutrient.
     *
     * @param id   the nutrient ID
     * @param name the name of the nutrient
     * @param unit the unit that this nutrient uses
     */
    void add(Integer id, String name, Unit unit);

    /**
     * Adds a new nutrient source to the database.
     *
     * @param file   the file in which the data resides
     * @param source the type of data source
     */
    void add(File file, DataSource source);

    /**
     * Gets information about a nutrient.
     *
     * @param id the nutrient ID
     * @return a NameAndUnit object
     */
    NameAndUnit get(Integer id);

    /**
     * Gets a map of all nutrients.
     *
     * @return a map of nutrient ID to its NameAndUnit
     */
    Map<Integer, NameAndUnit> get();

    /**
     * Gets a map of common nutrients and their IDs
     *
     * @return map from nutrient category to map of nutrient type to nutrient components. Ex:
     * Vitamins -> Vitamin K -> {K1, K2, dK}
     */
    Map<Group, Map<Type, Set<Integer>>> getStratified();

    /**
     * A general category of nutrients.
     */
    enum Group {
        GENERAL,
        CARBOHYDRATES,
        LIPIDS,
        PROTEINS,
        VITAMINS,
        MINERALS,
    }

    /**
     * A specific nutrient.
     */
    enum Type {

        // General

        ENERGY,
        ALCOHOL,
        ASH,
        CAFFEINE,
        WATER,

        // Carbs

        TOTAL_FIBER,
        SOLUBLE_FIBER,
        INSOLUBLE_FIBER,
        STARCH,
        TOTAL_SUGAR,
        ADDED_SUGAR,

        // Lipids

        TOTAL_FAT,
        MONOUNSATURATED_FAT,
        POLYUNSATURATED_FAT,
        OMEGA_3,
        OMEGA_6,
        SATURATED_FAT,
        TRANS_FAT,
        CHOLESTEROL,

        // Proteins

        PROTEIN,

        // Vitamins

        VITAMIN_B1,
        VITAMIN_B2,
        VITAMIN_B3,
        VITAMIN_B5,
        VITAMIN_B6,
        VITAMIN_B12,
        BIOTIN,
        CHOLINE,
        FOLATE,
        VITAMIN_A,
        VITAMIN_C,
        VITAMIN_D,
        VITAMIN_E,
        VITAMIN_K,

        // Minerals

        CALCIUM,
        CHROMIUM,
        COPPER,
        FLUORIDE,
        IODINE,
        IRON,
        MAGNESIUM,
        MANGANESE,
        MOLYBDENUM,
        PHOSPHORUS,
        POTASSIUM,
        SELENIUM,
        SODIUM,
        SULFUR,
        ZINC,
    }

    class NameAndUnit {
        private final String name;
        private final Unit unit;

        public NameAndUnit(String name, Unit unit) {
            this.name = name;
            this.unit = unit;
        }

        public boolean equals(NameAndUnit other) {
            return this.name.equals(other.name) && this.unit == other.unit;
        }

        public String getName() {
            return name;
        }

        public Unit getUnit() {
            return unit;
        }

    }
}
