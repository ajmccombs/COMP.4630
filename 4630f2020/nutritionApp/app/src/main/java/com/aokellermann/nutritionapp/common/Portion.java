package com.aokellermann.nutritionapp.common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Locale;

public class Portion implements Serializable {
    /**
     * ID of associated food.
     */
    public Integer foodID;
    /**
     * The number of units (ex. 1 oz., 2 slices, 1 package, etc.)
     */
    public Double amount;
    /**
     * Description of the unit
     */
    public String description;
    /**
     * The mass per portion in grams.
     */
    public Double mass;

    public Portion(Integer foodID, Double amount, String description, Double mass) {
        this.foodID = foodID;
        this.amount = amount;
        this.description = description;
        this.mass = mass;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.US, "foodID: %d, %f %s (%fg)", this.foodID, this.amount, this.description, this.mass);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Portion other = (Portion)obj;
        return this.amount.equals(other.amount) && this.description.equals(other.description) && this.mass.equals(other.mass);
    }
}
