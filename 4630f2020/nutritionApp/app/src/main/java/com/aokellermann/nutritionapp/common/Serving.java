package com.aokellermann.nutritionapp.common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Locale;

public class Serving implements Serializable {
    /**
     * The number of portions
     */
    public Double amount;
    /**
     * The portion ID
     */
    public Integer portionID;

    public Serving(Double amount, Integer portionID) {
        this.amount = amount;
        this.portionID = portionID;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.US, "amount: %f, portionID: %d", this.amount, this.portionID);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Serving other = (Serving)obj;
        return this.amount.equals(other.amount) && this.portionID.equals(other.portionID);
    }
}
