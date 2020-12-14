package com.aokellermann.nutritionapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.aokellermann.nutritionapp.common.DataSource;
import com.aokellermann.nutritionapp.common.FoodType;
import com.aokellermann.nutritionapp.descriptions.DescriptionsImpl;
import com.aokellermann.nutritionapp.descriptions.MultiSourceDescriptions;
import com.aokellermann.nutritionapp.descriptions.MultiSourceDescriptionsImpl;
import com.aokellermann.nutritionapp.nutrition.FoodNutrients;
import com.aokellermann.nutritionapp.nutrition.FoodNutrientsImpl;
import com.aokellermann.nutritionapp.nutrition.FoodPortions;
import com.aokellermann.nutritionapp.nutrition.FoodPortionsImpl;
import com.aokellermann.nutritionapp.nutrition.NutrientsImpl;
import com.aokellermann.nutritionapp.utils.FileUtils;

import net.lingala.zip4j.exception.ZipException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.AsynchronousFileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodSearchActivity extends AppCompatActivity {

    MultiSourceDescriptions descriptions = new MultiSourceDescriptionsImpl();
    NutrientsImpl nutrients = new NutrientsImpl();
    FoodNutrients foodNutrients = new FoodNutrientsImpl();
    FoodPortions foodPortions = new FoodPortionsImpl();

    private ArrayList<ExampleItem> mExampleList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter; //adapter controls how much gets displayed
    private RecyclerView.LayoutManager mLayoutManager;

    private ImageView addToHomeView;

    private String fromWho = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search);

        Intent intent = getIntent();

        // who called us
        String fromBreak = intent.getStringExtra("break");
        String fromLunch = intent.getStringExtra("lunch");
        String fromDinner = intent.getStringExtra("dinner");

        if( fromBreak != null) {
            fromWho = "break";
        } else if (fromLunch != null){
            fromWho = "lunch";
        } else if (fromDinner != null) {
            fromWho = "dinner";
        }

        // Setup the db
        setup();
        buildRecyclerView();


        EditText editText = (EditText) findViewById(R.id.foodSearch); // what user is searching
        TextView output = (TextView) findViewById(R.id.testText); // testing textview
        output.setText(fromWho);

        // listening to the user input
        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    //Editable editable = (Editable) editText; // I dont think I need this anymore, but keeping it just in case I do...

                    //output.setText(editText.getText().toString()); // for debugging purposes, changes the textview to user input
                    filter(editText.getText().toString()); //calling filter function

                    return true;
                }
                return false;
            }
        });


    }

    // creating the filter for the search bar
    private void filter(String text) {

        //populate
        ArrayList<ExampleItem> filteredList = new ArrayList<>();
        List<Integer> results = Collections.emptyList();
        ArrayList<String> res_discript = new ArrayList<>();
        int c = 0;

        results = (List<Integer>) descriptions.searchKeywords(text, FoodType.GENERIC); // searches db for requested item

        for (int i : results) {
            res_discript.add(c, descriptions.getDescription(i, FoodType.GENERIC)); // iterates through our list and gets their descriptions. Stores in List of Strings
            c++;
        }

        // turn list of strings into list of items and stores into mExampleList
        toExampleItems(res_discript);


        for (ExampleItem item : mExampleList) {
            if (item.getText1().toLowerCase().contains(text.toLowerCase())) {

                // searching for our keyword in recycler and add it
                filteredList.add(item);
            }
        }

        mAdapter.filterList(filteredList);

    }

    // creates a list of ExampleItems from our list of search results
    private void toExampleItems(List<String> list) {

        mExampleList = new ArrayList<>();

        for (String s : list) {
            mExampleList.add(new ExampleItem(s));
        }

        // Early stage testing,removed images and second text line
        /*
        mExampleList.add(new ExampleItem(R.drawable.ic_lunch, "Sandwich", "Macros"));
        mExampleList.add(new ExampleItem(R.drawable.ic_breakfast, "Eggs", "Macros"));
        */


    }


    public void returnItem(int position){
/*
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);

        String str = " ";
        str = mExampleList.get(position).getText1();
        intent.putExtra("addItem", str);

        startActivity(intent);
*/
    }
    // opens ReadItemActivity so we can view more details about the chosen food
    public void readItem(int position){

        Intent intent_read = new Intent(this, ReadActivity.class);
        String str = "Default";
        str = mExampleList.get(position).getText1();
        intent_read.putExtra("INFO", str);

        startActivity(intent_read);
    }

    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.foodRecycler);
        mRecyclerView.setHasFixedSize(true); //if recyclerview wont change in size, set to true for better performance
        mLayoutManager = new LinearLayoutManager(this); //assigns layout for the recycler
        mAdapter = new ExampleAdapter(mExampleList); //passes list to adapter

        mRecyclerView.setLayoutManager(mLayoutManager); //passing layout to recyclerview
        mRecyclerView.setAdapter(mAdapter); //passing adapter to the recyclerview



        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onAddClick(int position) {
                // send info to home to display
                returnItem(position);
            }

            @Override
            public void onReadClick(int position) {
                // open activity to read details
                readItem(position);
            }
        });
    }

    // setup for downlading the data

    public void setup(){

        //filling database will our data

        try {

            InputStream assetFiles1 = getAssets().open("fdcData/foundation/food.csv");
            InputStream assetFiles2 = getAssets().open("fdcData/legacy/food.csv");

            descriptions.add(
                    assetFiles1,
                    DataSource.FDC,
                    FoodType.GENERIC
            );
            descriptions.add(
                    assetFiles2,
                    DataSource.FDC,
                    FoodType.GENERIC
            );

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

/* Old stuff


            // live filter for recycler
        /*
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    filter(editable.toString());
                } catch (ZipException e) {
                    e.printStackTrace();
                }

            }
        });*/

 /* Old way for filtering
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
        }


  db populating
        if (!fdcDir.exists()) {
            HashMap<String, String> sources = new HashMap<String, String>();
            sources.put(
                    "foundation",
                    "FoodData_Central_foundation_food_csv_2020-10-30.zip"
                    //"https://fdc.nal.usda.gov/fdc-datasets/FoodData_Central_foundation_food_csv_2020-10-30.zip"
            );
            sources.put(
                    "supporting",
                    "FoodData_Central_Supporting_Data_csv_2020-10-30.zip"
                    //"https://fdc.nal.usda.gov/fdc-datasets/FoodData_Central_Supporting_Data_csv_2020-10-30.zip"
            );
            sources.put(
                    "legacy",
                    "FoodData_Central_sr_legacy_food_csv_%202019-04-02.zip"
                    //"https://fdc.nal.usda.gov/fdc-datasets/FoodData_Central_sr_legacy_food_csv_%202019-04-02.zip"
            );


            for (Map.Entry<String, String> entry : sources.entrySet()) {
                File zip = new File("fdcData.zip");
                try {
                    FileUtils.download(new URL(entry.getValue()), zip);
                } catch (Exception e) {
                }
                FileUtils.unzip(zip, new File(fdcDir.getAbsolutePath() + '/' + entry.getKey()));
                zip.delete();

            }

        }

        //MultiSourceDescriptionsImpl descriptions = new MultiSourceDescriptionsImpl();
        /*if ( sb.exists() ){
        descriptions.add(
                new File("./fdcData/foundation/food.csv"),
                DataSource.FDC,
                FoodType.GENERIC
        );
        descriptions.add(
                new File("./fdcData/legacy/food.csv"),
                DataSource.FDC,
                FoodType.GENERIC
        ); }

        //NutrientsImpl nutrients = new NutrientsImpl();
        nutrients.add(
                new File("fdcData/supporting/nutrient.csv"),
                DataSource.FDC
        );

        //FoodNutrientsImpl foodNutrients = new FoodNutrientsImpl();
        foodNutrients.add(
                new File("fdcData/foundation/food_nutrient.csv"),
                DataSource.FDC
        );
        foodNutrients.add(
                new File("fdcData/legacy/food_nutrient.csv"),
                DataSource.FDC
        );

        //FoodPortionsImpl foodPortions = new FoodPortionsImpl();
        foodPortions.add(
                new File("fdcData/foundation/food_portion.csv"),
                DataSource.FDC,
                FoodType.GENERIC
        );
        foodPortions.add(
                new File("fdcData/legacy/food_portion.csv"),
                DataSource.FDC,
                FoodType.GENERIC
        );


 */

