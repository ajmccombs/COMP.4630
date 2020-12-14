package com.aokellermann.nutritionapp.descriptions;

import com.aokellermann.nutritionapp.common.DataSource;
import com.aokellermann.nutritionapp.common.FoodType;
import com.aokellermann.nutritionapp.common.Portion;
import com.aokellermann.nutritionapp.custom.Food;
import com.aokellermann.nutritionapp.custom.FoodImpl;
import com.aokellermann.nutritionapp.custom.Recipe;
import com.aokellermann.nutritionapp.custom.RecipeImpl;
import com.aokellermann.nutritionapp.utils.DBMap;
import com.aokellermann.nutritionapp.utils.DBMapImpl;
import com.aokellermann.nutritionapp.utils.FileUtils;

import org.nustaq.serialization.FSTConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class MultiSourceDescriptionsImpl implements MultiSourceDescriptions {
    Map<FoodType, Descriptions> descriptionsMap = new HashMap<>();

    @Override
    public void add(Integer id, String description, FoodType type) {
        descriptionsMap.putIfAbsent(type, new DescriptionsImpl());
        descriptionsMap.get(type).add(id, description);
    }

    @Override
    public void add(File file, DataSource source, FoodType foodType) {
        try {
            add(new FileInputStream(file), source, foodType);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(InputStream is, DataSource source, FoodType foodType) {
        descriptionsMap.putIfAbsent(foodType, new DescriptionsImpl());
        Descriptions descriptions = descriptionsMap.get(foodType);
        if (source == DataSource.FDC) {
            for (String[] tokens : FileUtils.getLinesFromCSVInputStream(is)) {
                Integer fdcID = Integer.parseInt(tokens[0]);
                String description = tokens[2];
                descriptions.add(fdcID, description);
            }
        } else if (source == DataSource.CUSTOM) {
            byte[] bytes = FileUtils.readFileInputStream(is);
            if (bytes != null) {
                FSTConfiguration serializationConfig = FSTConfiguration.createAndroidDefaultConfiguration();
                //serializationConfig.registerClass(FoodImpl.class, RecipeImpl.class, DBMapImpl.class);
                if (foodType == FoodType.CUSTOM_FOOD) {
                    DBMap<Food> db = (DBMap<Food>) serializationConfig.asObject(bytes);
                    for (Map.Entry<Integer, Food> entry : db.get().entrySet()) {
                        descriptions.add(entry.getKey(), entry.getValue().getDescription());
                    }
                } else if (foodType == FoodType.CUSTOM_RECIPE) {
                    DBMap<Recipe> db = (DBMap<Recipe>) serializationConfig.asObject(bytes);
                    for (Map.Entry<Integer, Recipe> entry : db.get().entrySet()) {
                        descriptions.add(entry.getKey(), entry.getValue().getDescription());
                    }
                }
            }
        }
    }

    @Override
    public String getDescription(Integer id, FoodType type) {
        return descriptionsMap.get(type).getDescription(id);
    }

    @Override
    public Collection<Integer> searchKeywords(String query, FoodType type) {
        return descriptionsMap.get(type).searchKeywords(query);
    }
}
