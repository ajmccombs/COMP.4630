package com.aokellermann.nutritionapp

import com.aokellermann.nutritionapp.utils.FileUtils
import java.io.File
import java.net.URL

class CommonSetup {
    companion object {
        @JvmStatic
        fun setup() {
            val fdcDir = File("fdcData")
            if (!fdcDir.exists()) {
                val dataSources = arrayOf(
                    Pair(
                        "foundation",
                        "https://fdc.nal.usda.gov/fdc-datasets/FoodData_Central_foundation_food_csv_2020-10-30.zip"
                    ),
                    Pair(
                        "supporting",
                        "https://fdc.nal.usda.gov/fdc-datasets/FoodData_Central_Supporting_Data_csv_2020-10-30.zip"
                    ),
                    Pair(
                        "legacy",
                        "https://fdc.nal.usda.gov/fdc-datasets/FoodData_Central_sr_legacy_food_csv_%202019-04-02.zip"
                    )
                )

                for (dataUrl in dataSources) {
                    val zip = File("fdcData.zip")
                    FileUtils.download(URL(dataUrl.second), zip)
                    FileUtils.unzip(zip, File(fdcDir.path + '/' + dataUrl.first))
                    zip.delete()
                }
            }
        }
    }
}