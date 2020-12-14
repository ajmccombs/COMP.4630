package com.aokellermann.nutritionapp.nutrition;

import com.aokellermann.nutritionapp.common.DataSource;
import com.aokellermann.nutritionapp.common.Unit;
import com.aokellermann.nutritionapp.utils.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NutrientsImpl implements Nutrients {
    private final Map<Integer, NameAndUnit> nutrients = new HashMap<>();
    private final Map<Group, Map<Type, Set<Integer>>> stratifiedNutrients;

    public NutrientsImpl() {
        Map<Group, Map<Type, Set<Integer>>> map = new HashMap<>();
        for (Group group : Group.values()) {
            map.put(group, new HashMap<>());
        }

        Map<Type, Set<Integer>> generalMap = map.get(Group.GENERAL);
        generalMap.put(Type.ENERGY, new HashSet<>(Arrays.asList(1008, 1062)));
        generalMap.put(Type.ALCOHOL, new HashSet<>(Arrays.asList(1018)));
        generalMap.put(Type.ASH, new HashSet<>(Arrays.asList(1007)));
        generalMap.put(Type.CAFFEINE, new HashSet<>(Arrays.asList(1057)));
        generalMap.put(Type.WATER, new HashSet<>(Arrays.asList(1051)));

        Map<Type, Set<Integer>> carbsMap = map.get(Group.CARBOHYDRATES);
        carbsMap.put(Type.TOTAL_FIBER, new HashSet<>(Arrays.asList(1079)));
        carbsMap.put(Type.SOLUBLE_FIBER, new HashSet<>(Arrays.asList(1082)));
        carbsMap.put(Type.INSOLUBLE_FIBER, new HashSet<>(Arrays.asList(1084)));
        carbsMap.put(Type.STARCH, new HashSet<>(Arrays.asList(1009)));
        carbsMap.put(Type.TOTAL_SUGAR, new HashSet<>(Arrays.asList(1063)));
        carbsMap.put(Type.ADDED_SUGAR, new HashSet<>(Arrays.asList(1235)));

        Map<Type, Set<Integer>> lipidMap = map.get(Group.LIPIDS);
        lipidMap.put(Type.TOTAL_FAT, new HashSet<>(Arrays.asList(1004)));
        lipidMap.put(Type.MONOUNSATURATED_FAT, new HashSet<>(Arrays.asList(1292)));
        lipidMap.put(Type.POLYUNSATURATED_FAT, new HashSet<>(Arrays.asList(1293)));
        lipidMap.put(Type.OMEGA_3, new HashSet<>(Arrays.asList(1404))); // alpha linolenic acid (ALA)
        lipidMap.put(Type.OMEGA_6, new HashSet<>(Arrays.asList(1316))); // linoleic acid (LA)
        lipidMap.put(Type.SATURATED_FAT, new HashSet<>(Arrays.asList(1258)));  // total saturated
        lipidMap.put(Type.TRANS_FAT, new HashSet<>(Arrays.asList(1257)));  // total trans
        lipidMap.put(Type.CHOLESTEROL, new HashSet<>(Arrays.asList(1253)));

        Map<Type, Set<Integer>> proteinMap = map.get(Group.PROTEINS);
        proteinMap.put(Type.PROTEIN, new HashSet<>(Arrays.asList(1003))); // protein

        Map<Type, Set<Integer>> vitaminsMap = map.get(Group.VITAMINS);
        vitaminsMap.put(Type.VITAMIN_B1, new HashSet<>(Arrays.asList(1165))); // thiamin
        vitaminsMap.put(Type.VITAMIN_B2, new HashSet<>(Arrays.asList(1166))); // riboflavin
        vitaminsMap.put(Type.VITAMIN_B3, new HashSet<>(Arrays.asList(1167))); // niacin
        vitaminsMap.put(Type.VITAMIN_B5, new HashSet<>(Arrays.asList(1170))); // pantothenic acid
        vitaminsMap.put(Type.VITAMIN_B6, new HashSet<>(Arrays.asList(1175))); // b-6
        vitaminsMap.put(Type.VITAMIN_B12, new HashSet<>(Arrays.asList(1178))); // b-12
        vitaminsMap.put(Type.BIOTIN, new HashSet<>(Arrays.asList(1176))); // biotin
        vitaminsMap.put(Type.CHOLINE, new HashSet<>(Arrays.asList(1180))); // total choline
        vitaminsMap.put(Type.FOLATE, new HashSet<>(Arrays.asList(1177))); // total folate
        vitaminsMap.put(Type.VITAMIN_A, new HashSet<>(Arrays.asList(1104, 1106))); // IU, RAE
        vitaminsMap.put(Type.VITAMIN_C, new HashSet<>(Arrays.asList(1162))); // ascorbic acid
        vitaminsMap.put(Type.VITAMIN_D, new HashSet<>(Arrays.asList(1110, 1114))); // IU, ug
        vitaminsMap.put(Type.VITAMIN_E, new HashSet<>(Arrays.asList(1109))); // alpha tocopherol
        // menaquinone (K2), dihydrophylloquinone (dK), phylloquinone (K1)
        vitaminsMap.put(Type.VITAMIN_K, new HashSet<>(Arrays.asList(1183, 1184, 1185)));

        Map<Type, Set<Integer>> mineralsMap = map.get(Group.MINERALS);
        mineralsMap.put(Type.CALCIUM, new HashSet<>(Arrays.asList(1087))); // Ca
        mineralsMap.put(Type.CHROMIUM, new HashSet<>(Arrays.asList(1096))); // Cr
        mineralsMap.put(Type.COPPER, new HashSet<>(Arrays.asList(1098))); // Cu
        mineralsMap.put(Type.FLUORIDE, new HashSet<>(Arrays.asList(1099))); // F
        mineralsMap.put(Type.IODINE, new HashSet<>(Arrays.asList(1100))); // I
        mineralsMap.put(Type.IRON, new HashSet<>(Arrays.asList(1089))); // Fe
        mineralsMap.put(Type.MAGNESIUM, new HashSet<>(Arrays.asList(1090))); // Mg
        mineralsMap.put(Type.MANGANESE, new HashSet<>(Arrays.asList(1101))); // Mn
        mineralsMap.put(Type.MOLYBDENUM, new HashSet<>(Arrays.asList(1002))); // Mo
        mineralsMap.put(Type.PHOSPHORUS, new HashSet<>(Arrays.asList(1091))); // P
        mineralsMap.put(Type.POTASSIUM, new HashSet<>(Arrays.asList(1092))); // K
        mineralsMap.put(Type.SELENIUM, new HashSet<>(Arrays.asList(1103))); // Se
        mineralsMap.put(Type.SODIUM, new HashSet<>(Arrays.asList(1093))); // Na
        mineralsMap.put(Type.SULFUR, new HashSet<>(Arrays.asList(1094))); // S
        mineralsMap.put(Type.ZINC, new HashSet<>(Arrays.asList(1095))); // Zn

        stratifiedNutrients = map;
    }

    @Override
    public void add(Integer id, String name, Unit unit) {
        nutrients.put(id, new NameAndUnit(name, unit));
    }

    @Override
    public void add(File file, DataSource source) {
        if (source == DataSource.FDC) {
            for (String[] tokens : FileUtils.getLinesFromCSV(file)) {
                Integer id = Integer.parseInt(tokens[0]);
                String name = tokens[1];
                Unit unit = Unit.fromString(tokens[2]);
                add(id, name, unit);
            }
        }
    }

    @Override
    public NameAndUnit get(Integer id) {
        return nutrients.get(id);
    }

    @Override
    public Map<Integer, NameAndUnit> get() {
        return nutrients;
    }

    @Override
    public Map<Group, Map<Type, Set<Integer>>> getStratified() {
        return stratifiedNutrients;
    }
}
