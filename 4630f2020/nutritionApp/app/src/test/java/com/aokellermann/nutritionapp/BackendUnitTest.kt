package com.aokellermann.nutritionapp

import com.aokellermann.nutritionapp.common.*
import com.aokellermann.nutritionapp.common.Unit
import com.aokellermann.nutritionapp.custom.Food
import com.aokellermann.nutritionapp.custom.FoodImpl
import com.aokellermann.nutritionapp.custom.Recipe
import com.aokellermann.nutritionapp.custom.RecipeImpl
import com.aokellermann.nutritionapp.descriptions.MultiSourceDescriptions
import com.aokellermann.nutritionapp.nutrition.NutrientValue
import com.aokellermann.nutritionapp.nutrition.Nutrients
import com.aokellermann.nutritionapp.utils.DBMap
import com.aokellermann.nutritionapp.utils.DBMapImpl
import com.aokellermann.nutritionapp.utils.FileUtils
import org.junit.Assert
import org.junit.Test
import java.io.File
import java.util.*

class BackendUnitTest : DataBase() {
    @Test
    fun unifiedTest() {
        val portionDB: DBMap<Map<Int, Portion>> = DBMapImpl()
        val foodDB: DBMap<Food> = DBMapImpl()
        val recipeDB: DBMap<Recipe> = DBMapImpl()

        val foodDescriptions = arrayOf("food1", "food2")

        val foods = arrayOf(
            FoodImpl(
                foodDescriptions[0],
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
                )
            ),
            FoodImpl(
                foodDescriptions[1], mapOf(
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
        )

        val foodIDs = LinkedList<Int>()
        val portions = LinkedList<Portion>()

        val recipe = RecipeImpl("recipe") as Recipe
        for (food in foods) {
            val foodID = foodDB.add(food)
            foodIDs.add(foodID)

            val portion = Portion(foodID, 2.5, "scoop", 30.0)
            portions.add(portion)
            portionDB.get()[foodID] = mapOf(Pair(0, portion))

            recipe.putIngredient(foodID, Serving(2.0, 0))
        }

        val recipeID = recipeDB.add(recipe)

        val portionFile = File("portionDB")
        val foodFile = File("foodDB")
        val recipeFile = File("recipeDB")

        FileUtils.writeFile(portionFile, serializationConfig.asByteArray(portionDB))
        FileUtils.writeFile(foodFile, serializationConfig.asByteArray(foodDB))
        FileUtils.writeFile(recipeFile, serializationConfig.asByteArray(recipeDB))

        foodPortions.add(portionFile, DataSource.CUSTOM, FoodType.CUSTOM_FOOD)
        descriptions.add(foodFile, DataSource.CUSTOM, FoodType.CUSTOM_FOOD)
        descriptions.add(
            recipeFile,
            DataSource.CUSTOM,
            FoodType.CUSTOM_RECIPE
        )
        foodNutrients.add(foodFile, DataSource.CUSTOM)

        Assert.assertEquals(
            "recipe",
            descriptions.getDescription(recipeID, FoodType.CUSTOM_RECIPE)
        )
        for (i in foodIDs.indices) {
            Assert.assertEquals(
                foodDescriptions[i],
                descriptions.getDescription(
                    foodIDs[i],
                    FoodType.CUSTOM_FOOD
                )
            )
            Assert.assertEquals(foods[i].nutrients, foodNutrients.get(foodIDs[i]))
            Assert.assertEquals(portions[i], foodPortions.get(foodIDs[i], FoodType.CUSTOM_FOOD)[0])
        }

        portionFile.delete()
        foodFile.delete()
        recipeFile.delete()

        val recipeLabelCreator = RecipeNutritionLabelCreator(
            recipeDB, portionDB, FoodNutritionLabelCreator(
                descriptions,
                nutrients,
                foodNutrients
            )
        )

        val label = recipeLabelCreator.createRecipeLabel(recipeID)
        Assert.assertEquals(NutrientValue(Unit.MILLIGRAM, 300.0),
            label.nutrients[Nutrients.Group.MINERALS]?.get(Nutrients.Type.SODIUM)
        )
    }
}