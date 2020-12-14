package com.aokellermann.nutritionapp.nutrition;

import com.aokellermann.nutritionapp.common.DataSource;
import com.aokellermann.nutritionapp.custom.Food;
import com.aokellermann.nutritionapp.custom.FoodImpl;
import com.aokellermann.nutritionapp.utils.DBMap;
import com.aokellermann.nutritionapp.utils.DBMapImpl;
import com.aokellermann.nutritionapp.utils.FileUtils;

import org.nustaq.serialization.FSTConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FoodNutrientsImpl implements FoodNutrients {
    private final Map<Integer, Map<Integer, Double>> foodNutrients = new HashMap<>();

    @Override
    public void add(Integer id, Integer nutrientID, Double amount) {
        foodNutrients.putIfAbsent(id, new HashMap<>());
        foodNutrients.get(id).put(nutrientID, amount);
    }

    @Override
    public void add(File file, DataSource source) {
        if (source == DataSource.FDC) {
            for (String[] tokens : FileUtils.getLinesFromCSV(file)) {
                Integer fdcID = Integer.parseInt(tokens[1]);
                Integer nutrientID = Integer.parseInt(tokens[2]);
                Double amount = Double.parseDouble(tokens[3]);
                add(fdcID, nutrientID, amount);
            }
        } else if (source == DataSource.CUSTOM) {
            byte[] bytes = FileUtils.readFile(file);
            if (bytes != null) {
                FSTConfiguration serializationConfig = FSTConfiguration.createAndroidDefaultConfiguration();
                serializationConfig.registerClass(FoodImpl.class, DBMapImpl.class);
                DBMap<Food> db = (DBMap<Food>) serializationConfig.asObject(bytes);
                for (Map.Entry<Integer, Food> entry : db.get().entrySet()) {
                    foodNutrients.putIfAbsent(entry.getKey(), entry.getValue().getNutrients());
                }
            }
        }
    }


    @Override
    public Map<Integer, Double> get(Integer id) {
        return foodNutrients.get(id);
    }
}
