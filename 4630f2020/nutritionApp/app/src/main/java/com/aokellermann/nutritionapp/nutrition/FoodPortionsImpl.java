package com.aokellermann.nutritionapp.nutrition;

import com.aokellermann.nutritionapp.common.DataSource;
import com.aokellermann.nutritionapp.common.FoodType;
import com.aokellermann.nutritionapp.common.Portion;
import com.aokellermann.nutritionapp.custom.Food;
import com.aokellermann.nutritionapp.custom.FoodImpl;
import com.aokellermann.nutritionapp.utils.DBMap;
import com.aokellermann.nutritionapp.utils.DBMapImpl;
import com.aokellermann.nutritionapp.utils.FileUtils;

import org.nustaq.serialization.FSTConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FoodPortionsImpl implements FoodPortions {
    // Map<FoodType, Map<FoodID, Map<PortionID, Portion>>
    private final Map<FoodType, Map<Integer, Map<Integer, Portion>>> foodNutrients = new HashMap<>();

    @Override
    public void add(Integer portionID, Integer foodID, Portion portion, FoodType foodType) {
        foodNutrients.putIfAbsent(foodType, new HashMap<>());
        foodNutrients.get(foodType).putIfAbsent(foodID, new HashMap<>());
        foodNutrients.get(foodType).get(foodID).putIfAbsent(portionID, portion);
    }

    public void add(Integer portionID, Integer foodID, Double amount, String description, Double mass, FoodType foodType) {
        add(portionID, foodID, new Portion(foodID, amount, description, mass), foodType);
    }

    public void add(File file, DataSource source, FoodType foodType) {
        if (source == DataSource.FDC) {
            for (String[] tokens : FileUtils.getLinesFromCSV(file)) {
                Integer portionID = Integer.parseInt(tokens[0]);
                Integer fdcID = Integer.parseInt(tokens[1]);
                Double amount = Double.parseDouble(tokens[3]);
                String description = tokens[6];
                Double mass = Double.parseDouble(tokens[7]);
                add(portionID, fdcID, amount, description, mass, foodType);
            }
        } else if (source == DataSource.CUSTOM) {
            byte[] bytes = FileUtils.readFile(file);
            if (bytes != null) {
                FSTConfiguration serializationConfig = FSTConfiguration.createAndroidDefaultConfiguration();
                //serializationConfig.registerClass(Portion.class, new DBMapImpl<Portion>().getClass(), DBMapImpl.class);
                if (foodType == FoodType.CUSTOM_FOOD) {
                    DBMap<Map<Integer, Portion>> db = (DBMapImpl<Map<Integer, Portion>>) serializationConfig.asObject(bytes);
                    foodNutrients.putIfAbsent(foodType, new HashMap<>());
                    foodNutrients.get(foodType).putAll(db.get());
                }
            }
        }
    }

    public Map<Integer, Portion> get(Integer foodID, FoodType foodType) {
        return foodNutrients.get(foodType).get(foodID);
    }
}
