package com.aokellermann.nutritionapp.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides types and methods for units of measurement.
 */
public enum Unit {
    GRAM(Collections.singletonList("g"), "g", 1000000.0),
    MILLIGRAM(Arrays.asList("mg", "mg_ate"), "mg", 1000.0),
    MICROGRAM(Collections.singletonList("ug"), "ug", 1.0),
    INTERNATIONAL_UNIT(Collections.singletonList("iu"), "IU", 1.0),
    SPECIFIC_GRAVITY(Collections.singletonList("sp_gr"), "SG", 1.0),
    KILOJOULE(Collections.singletonList("kj"), "kJ", 4.184),
    KILOCALORIE(Collections.singletonList("kcal"), "kcal", 1.0);

    private static final Map<String, Unit> FROM = new HashMap<>();
    private static final Map<Unit, String> TO = new HashMap<>();
    private static final Map<Unit, Double> UNITS = new HashMap<>();
    private static final Map<Unit, Unit> CANONICAL = new HashMap<>();

    static {
        for (Unit unit : values()) {
            for (String from_s : unit.from)
                FROM.put(from_s, unit);
            TO.put(unit, unit.to);
            UNITS.put(unit, unit.ratio);
        }
        CANONICAL.put(GRAM, MICROGRAM);
        CANONICAL.put(MILLIGRAM, MICROGRAM);
        CANONICAL.put(MICROGRAM, MICROGRAM);
        CANONICAL.put(INTERNATIONAL_UNIT, INTERNATIONAL_UNIT);
        CANONICAL.put(SPECIFIC_GRAVITY, SPECIFIC_GRAVITY);
        CANONICAL.put(KILOJOULE, KILOCALORIE);
        CANONICAL.put(KILOCALORIE, KILOCALORIE);
    }


    private final Collection<String> from;
    private final String to;
    private final Double ratio;

    Unit(Collection<String> from, String to, Double ratio) {
        this.from = from;
        this.to = to;
        this.ratio = ratio;
    }

    public static Unit fromString(String string) {
        return FROM.get(string.toLowerCase());
    }

    @Override
    public String toString() {
        return this.to;
    }

    public CanonicalInfo getCanonicalInfo() {
        return new CanonicalInfo(CANONICAL.get(this), UNITS.get(this));
    }

    public class CanonicalInfo {
        public final Unit unit;
        public final Double units;

        CanonicalInfo(Unit unit, Double units) {
            this.unit = unit;
            this.units = units;
        }
    }
}
