package com.aokellermann.nutritionapp

import com.aokellermann.nutritionapp.common.DataSource
import com.aokellermann.nutritionapp.common.FoodType
import com.aokellermann.nutritionapp.common.Portion
import com.aokellermann.nutritionapp.custom.Food
import com.aokellermann.nutritionapp.custom.FoodImpl
import com.aokellermann.nutritionapp.custom.Recipe
import com.aokellermann.nutritionapp.custom.RecipeImpl
import com.aokellermann.nutritionapp.descriptions.MultiSourceDescriptions
import com.aokellermann.nutritionapp.descriptions.MultiSourceDescriptionsImpl
import com.aokellermann.nutritionapp.nutrition.*
import com.aokellermann.nutritionapp.utils.DBMap
import com.aokellermann.nutritionapp.utils.DBMapImpl
import org.junit.BeforeClass
import org.nustaq.serialization.FSTConfiguration
import java.io.File

open class DataBase {
    companion object {
        lateinit var descriptions: MultiSourceDescriptions
        lateinit var nutrients: Nutrients
        lateinit var foodNutrients: FoodNutrients
        lateinit var foodPortions: FoodPortions
        lateinit var serializationConfig: FSTConfiguration

        // https://stackoverflow.com/questions/39679180/kotlin-call-java-method-with-classt-argument
        private inline fun <reified T : Any> typeRef(): DBMap<T> = object : DBMapImpl<T>() {}

        @BeforeClass
        @JvmStatic
        fun setup() {
            CommonSetup.setup()

            descriptions =
                MultiSourceDescriptionsImpl()
            descriptions.add(
                File("fdcData/foundation/food.csv"),
                DataSource.FDC,
                FoodType.GENERIC
            )
            descriptions.add(
                File("fdcData/legacy/food.csv"),
                DataSource.FDC,
                FoodType.GENERIC
            )

            nutrients = NutrientsImpl()
            nutrients.add(
                File("fdcData/supporting/nutrient.csv"),
                DataSource.FDC
            )

            foodNutrients = FoodNutrientsImpl()
            foodNutrients.add(
                File("fdcData/foundation/food_nutrient.csv"),
                DataSource.FDC
            )
            foodNutrients.add(
                File("fdcData/legacy/food_nutrient.csv"),
                DataSource.FDC
            )

            foodPortions =
                FoodPortionsImpl()
            foodPortions.add(
                File("fdcData/foundation/food_portion.csv"),
                DataSource.FDC,
                FoodType.GENERIC
            )
            foodPortions.add(
                File("fdcData/legacy/food_portion.csv"),
                DataSource.FDC,
                FoodType.GENERIC
            )

            serializationConfig = FSTConfiguration.createAndroidDefaultConfiguration()
//            serializationConfig.registerClass(
//                FoodImpl::class.java, typeRef<Food>()::class.java,
//                RecipeImpl::class.java, typeRef<Recipe>()::class.java,
//                Portion::class.java, typeRef<Portion>()::class.java,
//            )
        }
    }
}