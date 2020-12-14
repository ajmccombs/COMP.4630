package com.aokellermann.nutritionapp;

//This is the object for the recycler View. It has 1 image and 2 lines of text.

public class HomeItem {

   // private int mImageResource;
    private String mText1;
   // private String mText2;

    //constructor
    public HomeItem(String text1){
       // mImageResource = imageResource;
        mText1 = text1;
       // mText2 = text2;
    }

    //getters
    //public int getImageResource(){ return mImageResource; }
    public String getText1(){
        return mText1;
    }
   // public String getText2(){ return mText2; }

}
