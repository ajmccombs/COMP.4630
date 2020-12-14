package com.aokellermann.nutritionapp;

import android.content.Intent;
import android.os.Bundle;

import com.aokellermann.nutritionapp.common.DataSource;
import com.aokellermann.nutritionapp.common.FoodType;
import com.aokellermann.nutritionapp.descriptions.MultiSourceDescriptionsImpl;
import com.aokellermann.nutritionapp.nutrition.FoodNutrientsImpl;
import com.aokellermann.nutritionapp.nutrition.FoodPortionsImpl;
import com.aokellermann.nutritionapp.nutrition.NutrientsImpl;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import java.io.File;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ArrayList<HomeItem> mHomeListBreak = new ArrayList<>();
    private ArrayList<HomeItem> mHomeListLunch = new ArrayList<>();
    private ArrayList<HomeItem> mHomeListDinner = new ArrayList<>();
    private RecyclerView mRecyclerView1;
    private HomeAdapter mAdapter1; //adapter controls how much gets displayed
    private RecyclerView mRecyclerView2;
    private HomeAdapter mAdapter2;
    private RecyclerView mRecyclerView3;
    private HomeAdapter mAdapter3;
    private RecyclerView.LayoutManager mLayoutManager;

    private Button buttonB;
    private Button buttonL;
    private Button buttonD;
    private ImageButton buttonMeal;

    ProgressBar pbP;
    ProgressBar pbC;
    ProgressBar pbF;
    int totalP;
    int totalC;
    int totalF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        progress();
        setupRecycler();
        setupButtons();

    }


    public void removeItemBreak(int position) {
         mHomeListBreak.remove(position);
         mAdapter1.notifyItemRemoved(position);
     }
    public void removeItemLunch(int position) {
        mHomeListLunch.remove(position);
        mAdapter2.notifyItemRemoved(position);
    }
    public void removeItemDinner(int position) {
        mHomeListDinner.remove(position);
        mAdapter3.notifyItemRemoved(position);
    }
    private void readItemBreak(int position){

        Intent intent_read = new Intent(this, ReadActivity.class);
        String str = "Default";
        str = mHomeListBreak.get(position).getText1();
        intent_read.putExtra("INFO", str);

        startActivity(intent_read);
    }
    private void readItemLunch(int position){

        Intent intent_read = new Intent(this, ReadActivity.class);
        String str = "Default";
        str = mHomeListLunch.get(position).getText1();
        intent_read.putExtra("INFO", str);

        startActivity(intent_read);
    }
    private void readItemDinner(int position){

        Intent intent_read = new Intent(this, ReadActivity.class);
        String str = "Default";
        str = mHomeListDinner.get(position).getText1();
        intent_read.putExtra("INFO", str);

        startActivity(intent_read);
    }

    public void setupButtons() {
        buttonB = (Button) findViewById(R.id.buttonAddBreakfast);
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFoodBreak();
            }
        });
        buttonL = (Button) findViewById(R.id.buttonAddLunch);
        buttonL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFoodLunch();
            }
        });
        buttonD = (Button) findViewById(R.id.buttonAddDinner);
        buttonD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFoodDinner();
            }
        });
        buttonMeal = (ImageButton) findViewById(R.id.buttonCreateMeal);
        buttonMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createMeal();
            }
        });

    }

    public void addFoodBreak() {
        Intent intent = new Intent(this, FoodSearchActivity.class);

        intent.putExtra("break", "Adding to Breakfast");
        intent.putExtra("addItem", " ");
        startActivity(intent);
    }
    public void addFoodLunch() {
        Intent intent = new Intent(this, FoodSearchActivity.class);
        intent.putExtra("lunch", "Adding to Lunch");
        intent.putExtra("addItem", " ");
        startActivity(intent);
    }
    public void addFoodDinner() {
        Intent intent = new Intent(this, FoodSearchActivity.class);
        intent.putExtra("dinner","Adding to Dinner");
        intent.putExtra("addItem", " ");
        startActivity(intent);
    }

    public void createMeal() {
        Intent intent = new Intent(this, CreateMealActivity.class);
        startActivity(intent);
    }

    public void progress() {

        pbP = (ProgressBar) findViewById(R.id.protein);

    }

    private void setupRecycler() {
        createHomeListBreak();
        createHomeListLunch();
        createHomeListDinner();
        buildRecyclerViewBreak();
        buildRecyclerViewLunch();
        buildRecyclerViewDinner();
    }


    /* adding items to each list */
    private void createHomeListBreak() {

        mHomeListBreak.add(new HomeItem("Orange"));
        mHomeListBreak.add(new HomeItem("Cereal"));
       // mHomeListBreak.add(new HomeItem(R.drawable.ic_breakfast, "Egg", "Macros"));

    }

    private void createHomeListLunch() {

        mHomeListLunch.add(new HomeItem("Pizza"));
       // mHomeListLunch.add(new HomeItem(R.drawable.ic_breakfast, "Egg", "Macros"));

    }

    private void createHomeListDinner() {

        mHomeListDinner.add(new HomeItem("Spaghetti"));
       // mHomeListDinner.add(new HomeItem(R.drawable.ic_dinner, "Chicken", "Macros"));


    }

    /* recycler building */
    // recycler for breakfast
    private void buildRecyclerViewBreak() {
        mRecyclerView1 = findViewById(R.id.foodRecyclerBreak);
        mRecyclerView1.setHasFixedSize(true); //if recyclerview wont change in size, set to true for better performance
        mLayoutManager = new LinearLayoutManager(this); //assigns layout for the recycler
        mAdapter1 = new HomeAdapter(mHomeListBreak); //passes list to adapter, mHomeListBreak is the getDataset()

        mRecyclerView1.setLayoutManager(mLayoutManager); //passing layout to recyclerview
        mRecyclerView1.setAdapter(mAdapter1); //passing adapter to the recyclerview

        mAdapter1.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                removeItemBreak(position);
            }

            @Override
            public void onReadClick(int position) {
                readItemBreak(position);
            }
        });
    }

    // recycler for lunch
    private void buildRecyclerViewLunch() {
        mRecyclerView2 = findViewById(R.id.foodRecyclerLunch);
        mRecyclerView2.setHasFixedSize(true); //if recyclerview wont change in size, set to true for better performance
        mLayoutManager = new LinearLayoutManager(this); //assigns layout for the recycler
        mAdapter2 = new HomeAdapter(mHomeListLunch); //passes list to adapter

        mRecyclerView2.setLayoutManager(mLayoutManager); //passing layout to recyclerview
        mRecyclerView2.setAdapter(mAdapter2); //passing adapter to the recyclerview

        mAdapter2.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                removeItemLunch(position);
            }
            @Override
            public void onReadClick(int position) {
                readItemLunch(position);
            }
        });
    }

    // recycler for dinner
    private void buildRecyclerViewDinner() {
        mRecyclerView3 = findViewById(R.id.foodRecyclerDinner);
        mRecyclerView3.setHasFixedSize(true); //if recyclerview wont change in size, set to true for better performance
        mLayoutManager = new LinearLayoutManager(this); //assigns layout for the recycler
        mAdapter3 = new HomeAdapter(mHomeListDinner); //passes list to adapter

        mRecyclerView3.setLayoutManager(mLayoutManager); //passing layout to recyclerview
        mRecyclerView3.setAdapter(mAdapter3); //passing adapter to the recyclerview

        mAdapter3.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                removeItemDinner(position);
            }
            @Override
            public void onReadClick(int position) {
                readItemDinner(position);
            }
        });
    }

}

