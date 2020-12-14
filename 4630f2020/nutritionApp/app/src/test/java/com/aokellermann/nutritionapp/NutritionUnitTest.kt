package com.aokellermann.nutritionapp

import com.aokellermann.nutritionapp.common.Unit
import com.aokellermann.nutritionapp.nutrition.NutrientValue
import com.aokellermann.nutritionapp.nutrition.Nutrients
import com.aokellermann.nutritionapp.common.FoodNutritionLabelCreator
import com.aokellermann.nutritionapp.common.FoodType
import com.aokellermann.nutritionapp.common.Portion
import org.junit.Assert
import org.junit.Test

class NutritionUnitTest : DataBase() {
    @Test
    fun nameAndUnitLookup() {
        val ids = arrayOf(
            1002,
            1026,
            1158,
            1096,
            1110,
            1024,
            1062,
            1008,
        )
        val expectedNames = arrayOf(
            "Nitrogen",
            "Acetic acid",
            "Vitamin E",
            "Chromium, Cr",
            "Vitamin D (D2 + D3), International Units",
            "Specific Gravity",
            "Energy",
            "Energy",
        )
        val expectedUnits = arrayOf(
            Unit.GRAM,
            Unit.MILLIGRAM,
            Unit.MILLIGRAM,
            Unit.MICROGRAM,
            Unit.INTERNATIONAL_UNIT,
            Unit.SPECIFIC_GRAVITY,
            Unit.KILOJOULE,
            Unit.KILOCALORIE,
        )

        for (i in ids.indices) {
            Assert.assertTrue(
                "name: ${expectedNames[i]}, type: ${expectedUnits[i]}, id: ${ids[i]}",
                Nutrients.NameAndUnit(expectedNames[i], expectedUnits[i])
                    .equals(nutrients!!.get(ids[i]))
            )
        }
    }

    @Test
    fun foodNutrientsLookup() {
        val ids = arrayOf(
            320015,
            331909
        )
        val expectedNutrientIDAmountPairs = arrayOf(
            mapOf(
                Pair(1091, 137.0),
                Pair(1087, 30.0),
                Pair(1101, 0.941),
                Pair(1090, 65.0),
                Pair(1092, 239.0),
                Pair(1095, 1.25),
                Pair(1098, 0.281),
                Pair(1089, 2.24),
                Pair(1093, 457.0),
            ),
            mapOf(
                Pair(1098, 0.042),
                Pair(1093, 43.0),
                Pair(1089, 0.5),
                Pair(1087, 6.0),
                Pair(1095, 1.0),
                Pair(1090, 31.7),
                Pair(1101, 0.011),
                Pair(1091, 240.0),
                Pair(1092, 332.0),
            )
        )

        for (i in ids.indices) {
            Assert.assertEquals(
                "id: ${ids[i]}, nutrs: ${expectedNutrientIDAmountPairs[i]}",
                expectedNutrientIDAmountPairs[i],
                foodNutrients!!.get(ids[i])
            )
        }
    }

    @Test
    fun createLabel() {
        val expectedNutrients = arrayOf(
            Triple(Nutrients.Type.PROTEIN, 4.83, Unit.GRAM),
            Triple(Nutrients.Type.FOLATE, 47.0, Unit.MICROGRAM),
            Triple(Nutrients.Type.VITAMIN_A, 2.0, Unit.INTERNATIONAL_UNIT),
            Triple(Nutrients.Type.VITAMIN_B5, 0.381, Unit.MILLIGRAM),
            Triple(Nutrients.Type.VITAMIN_B3, 3.024, Unit.MILLIGRAM),
            Triple(Nutrients.Type.VITAMIN_D, 0.0, Unit.INTERNATIONAL_UNIT),
            Triple(Nutrients.Type.VITAMIN_B12, 0.0, Unit.MICROGRAM),
            Triple(Nutrients.Type.VITAMIN_B2, 0.273, Unit.MILLIGRAM),
            Triple(Nutrients.Type.VITAMIN_B1, 0.362, Unit.MILLIGRAM),
            Triple(Nutrients.Type.VITAMIN_B6, 0.093, Unit.MILLIGRAM),
            Triple(Nutrients.Type.VITAMIN_C, 35.6, Unit.MILLIGRAM),
            Triple(Nutrients.Type.ASH, 0.58, Unit.GRAM),
            Triple(Nutrients.Type.WATER, 89.3, Unit.GRAM),
            Triple(Nutrients.Type.ENERGY, 33.0, Unit.KILOCALORIE),
            Triple(Nutrients.Type.POLYUNSATURATED_FAT, 0.318, Unit.GRAM),
            Triple(Nutrients.Type.MONOUNSATURATED_FAT, 0.045, Unit.GRAM),
            Triple(Nutrients.Type.TOTAL_FAT, 0.58, Unit.GRAM),
            Triple(Nutrients.Type.CHOLESTEROL, 0.0, Unit.MILLIGRAM),
            Triple(Nutrients.Type.SATURATED_FAT, 0.083, Unit.GRAM),
            Triple(Nutrients.Type.TRANS_FAT, 0.0, Unit.GRAM),
            Triple(Nutrients.Type.SODIUM, 7.0, Unit.MILLIGRAM),
            Triple(Nutrients.Type.SELENIUM, 0.6, Unit.MICROGRAM),
            Triple(Nutrients.Type.COPPER, 0.174, Unit.MILLIGRAM),
            Triple(Nutrients.Type.ZINC, 0.44, Unit.MILLIGRAM),
            Triple(Nutrients.Type.PHOSPHORUS, 38.0, Unit.MILLIGRAM),
            Triple(Nutrients.Type.CALCIUM, 19.0, Unit.MILLIGRAM),
            Triple(Nutrients.Type.IRON, 0.89, Unit.MILLIGRAM),
            Triple(Nutrients.Type.MANGANESE, 0.199, Unit.MILLIGRAM),
            Triple(Nutrients.Type.POTASSIUM, 194.0, Unit.MILLIGRAM),
            Triple(Nutrients.Type.MAGNESIUM, 23.0, Unit.MILLIGRAM),
        )

        val creator =
            FoodNutritionLabelCreator(
                descriptions,
                nutrients,
                foodNutrients
            )
        val label = creator.createFoodLabel(168395)

        val flatNutrientMap = HashMap<Nutrients.Type, NutrientValue>()
        for (entry1 in label.nutrients) {
            for (entry2 in entry1.value) {
                flatNutrientMap[entry2.key] = entry2.value
                println("${entry2.key}: ${entry2.value.value}${entry2.value.unit}")
            }
        }

        Assert.assertEquals(
            label.description,
            "Beans, kidney, mature seeds, sprouted, cooked, boiled, drained, without salt"
        )
        for (expectedNutrient in expectedNutrients) {
            val nv = flatNutrientMap[expectedNutrient.first]
            Assert.assertEquals(
                expectedNutrient,
                Triple(expectedNutrient.first, nv!!.value, nv.unit)
            )
        }
    }

    @Test
    fun unitConvert() {
        // energy
        var nv = NutrientValue(Unit.KILOCALORIE, 1.0)
        var nvCopy = NutrientValue(nv)

        nv.convertTo(Unit.KILOJOULE)
        Assert.assertEquals(4.184, nv.value, 0.0000001)
        Assert.assertEquals(Unit.KILOJOULE, nv.unit)
        Assert.assertEquals(nvCopy, nv)

        nv.convertTo(Unit.KILOCALORIE)
        Assert.assertEquals(1.0, nv.value, 0.0000001)
        Assert.assertEquals(Unit.KILOCALORIE, nv.unit)
        Assert.assertEquals(nvCopy, nv)

        // mass
        nv = NutrientValue(Unit.MICROGRAM, 1.0)
        nvCopy = NutrientValue(nv)

        nv.convertTo(Unit.MILLIGRAM)
        Assert.assertEquals(1000.0, nv.value, 0.0000001)
        Assert.assertEquals(Unit.MILLIGRAM, nv.unit)
        Assert.assertEquals(nvCopy, nv)

        nv.convertTo(Unit.GRAM)
        Assert.assertEquals(1000000.0, nv.value, 0.0000001)
        Assert.assertEquals(Unit.GRAM, nv.unit)
        Assert.assertEquals(nvCopy, nv)

        nv.convertTo(Unit.MICROGRAM)
        Assert.assertEquals(1.0, nv.value, 0.0000001)
        Assert.assertEquals(Unit.MICROGRAM, nv.unit)
        Assert.assertEquals(nvCopy, nv)

        nv.convertTo(Unit.GRAM)
        Assert.assertEquals(1000000.0, nv.value, 0.0000001)
        Assert.assertEquals(Unit.GRAM, nv.unit)
        Assert.assertEquals(nvCopy, nv)
    }

    @Test
    fun serving() {
        val expectedServings = mapOf(
            Pair(87313,
                Portion(0, 4.0, "oz", 113.0)
            ),
            Pair(87314,
                Portion(1,
                    1.0,
                    "steak",
                    361.0
                )
            ),
            Pair(87315,
                Portion(
                    2,
                    1.0,
                    "roast",
                    1173.0
                )
            ),
        )

        val servings = foodPortions!!.get(170818, FoodType.GENERIC)
        Assert.assertEquals(expectedServings, servings)
    }
}