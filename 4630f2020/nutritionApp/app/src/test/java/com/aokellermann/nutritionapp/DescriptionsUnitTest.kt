package com.aokellermann.nutritionapp

import com.aokellermann.nutritionapp.common.FoodType
import com.aokellermann.nutritionapp.descriptions.MultiSourceDescriptions
import org.junit.Assert
import org.junit.Test
import kotlin.system.measureNanoTime

class DescriptionsUnitTest : DataBase() {
    @Test
    fun descriptionLookup() {
        val genericsIDs = arrayOf(
            322041,
            327395,
            327914,
            335301,
            746774
        )
        val genericsDescriptions = arrayOf(
            "MILK, SKIM (WAVE 22E)",
            "Oranges, Navel, Pass 2, Region 3, n/a, Yes - Riboflavin, NFY01075V",
            "Romaine Lettuce, Region 4, n/a, Yes - Thiamin - NFY0101TE",
            "Beans, Dry, Pink, 725 (0% moisture)",
            "Restaurant, Chinese, sweet and sour pork"
        )

        for (i in genericsIDs.indices) {
            Assert.assertEquals(
                genericsDescriptions[i].toLowerCase(),
                descriptions!!.getDescription(
                    genericsIDs[i],
                    FoodType.GENERIC
                ).toLowerCase()
            )
        }
    }

    @Test
    fun search() {
        for (i in 0..10) {
            var descriptionsList: List<String>?
            println("search time: " + measureNanoTime {
                descriptionsList = descriptions!!.searchKeywords(
                    "kidne red bean cann", FoodType.GENERIC
                ).map { id ->
                    descriptions!!.getDescription(
                        id,
                        FoodType.GENERIC
                    )
                }
            })

            if (descriptionsList != null) {
                for (description in descriptionsList!!) {
                    Assert.assertTrue(description.toLowerCase().contains("kidne"))
                    Assert.assertTrue(description.toLowerCase().contains("red"))
                    Assert.assertTrue(description.toLowerCase().contains("bean"))
                    Assert.assertTrue(description.toLowerCase().contains("cann"))
                }
            }
        }
    }
}