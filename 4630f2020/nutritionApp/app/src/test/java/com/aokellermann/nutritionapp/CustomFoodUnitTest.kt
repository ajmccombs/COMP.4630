package com.aokellermann.nutritionapp

import com.aokellermann.nutritionapp.custom.*
import com.aokellermann.nutritionapp.common.Portion
import com.aokellermann.nutritionapp.common.Serving
import com.aokellermann.nutritionapp.utils.DBMap
import com.aokellermann.nutritionapp.utils.DBMapImpl
import org.junit.Assert
import org.junit.Test

class CustomFoodUnitTest : DataBase() {
    @Test
    fun serializeDeserializeFood() {
        val db: DBMap<Food> = DBMapImpl()
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
        for (map in expectedNutrientIDAmountPairs)
            db.add(FoodImpl("beans", map))

        val bytes = serializationConfig.asByteArray(db)
        val deserializedDB = serializationConfig.asObject(bytes) as DBMap<Food>

        Assert.assertEquals(db, deserializedDB)
    }

    @Test
    fun serializeDeserializeRecipe() {
        val foods = arrayOf(
            FoodImpl(
                "food1",
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
                "food2", mapOf(
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

        val recipe = RecipeImpl("recipe") as Recipe
        val foodDB = DBMapImpl<Food>() as DBMap<Food>
        val portionDB = DBMapImpl<Portion>()
        val portionID = portionDB.add(Portion(1, 2.5, "scoop", 30.0))
        for (food in foods) {
            val id = foodDB.add(food)
            recipe.putIngredient(id, Serving(2.0, portionID))
        }

        val bytes = serializationConfig.asByteArray(recipe)
        val deserializedRecipe = serializationConfig.asObject(bytes) as Recipe

        Assert.assertEquals(recipe, deserializedRecipe)
    }
}