package com.aokellermann.nutritionapp.nutrition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aokellermann.nutritionapp.common.Unit;

import java.util.Locale;

public class NutrientValue {
    private Unit unit;
    private double value;

    @NonNull
    @Override
    public String toString() {
        return String.format(Locale.US, "unit: %s, value: %f", this.unit, this.value);
    }

    public NutrientValue(NutrientValue nv) {
        this.unit = nv.unit;
        this.value = nv.value;
    }

    public NutrientValue(Unit unit, double value) {
        this.unit = unit;
        this.value = value;
    }

    public static NutrientValue sum(NutrientValue first, NutrientValue second) {
        NutrientValue cp1 = new NutrientValue(first);
        cp1.canonicalize();
        NutrientValue cp2 = new NutrientValue(second);
        cp2.canonicalize();
        cp1.value += cp2.value;
        cp1.convertTo(first.unit);
        return cp1;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj != null && obj.getClass() == this.getClass()) {
            NutrientValue nv1 = this.getCanonicalized();
            NutrientValue nv2 = ((NutrientValue) obj).getCanonicalized();
            return nv1.unit.equals(nv2.unit) && Double.valueOf(nv1.value).equals(nv2.value);
        }
        return false;
    }

    public Unit getUnit() {
        return unit;
    }

    public double getValue() {
        return value;
    }

    public void convertTo(Unit unit) {
        canonicalize();
        this.value *= unit.getCanonicalInfo().units;
        this.unit = unit;
    }

    private void canonicalize() {
        if (this.unit == null) return;
        Unit.CanonicalInfo canonicalInfo = this.unit.getCanonicalInfo();
        this.unit = canonicalInfo.unit;
        this.value /= canonicalInfo.units;
    }

    private NutrientValue getCanonicalized() {
        NutrientValue val = new NutrientValue(this);
        val.canonicalize();
        return val;
    }
}
