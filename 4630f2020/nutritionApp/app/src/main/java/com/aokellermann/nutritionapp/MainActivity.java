package com.aokellermann.nutritionapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.aokellermann.nutritionapp.common.DataSource;
import com.aokellermann.nutritionapp.common.FoodType;
import com.aokellermann.nutritionapp.descriptions.MultiSourceDescriptionsImpl;
import com.aokellermann.nutritionapp.nutrition.FoodNutrientsImpl;
import com.aokellermann.nutritionapp.nutrition.FoodPortionsImpl;
import com.aokellermann.nutritionapp.nutrition.NutrientsImpl;
import com.aokellermann.nutritionapp.utils.FileUtils;

import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.buttonToHome);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                try {
                    setup();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                 */

                openHome();
            }
        });
    }

    public void openHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
/*
    public static void setup() throws MalformedURLException {

        File dest = new File("assets/fdcData");

        if (!dest.exists()) {
            HashMap<String, String> sources = new HashMap<String, String>();
            sources.put(
                    "foundation",
                    "https://fdc.nal.usda.gov/fdc-datasets/FoodData_Central_foundation_food_csv_2020-10-30.zip"
            );

            for (Map.Entry<String, String> entry : sources.entrySet()) {

                File zip = new File("assets/fdcData/fdcData.zip");
                String sZip = "assets/fdcData/fdcData.zip";

                Decompress.Download(new URL(entry.getValue()), zip);
                String destination = dest.getAbsolutePath() + '/' + entry.getKey();
                Decompress.unzip(sZip, destination);

            }
        }
    }

 */
}











