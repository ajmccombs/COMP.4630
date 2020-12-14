package com.aokellermann.nutritionapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder>{

    private ArrayList<HomeItem> mHomeList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onDeleteClick(int position);
        void onReadClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {

       // public ImageView mImageView;
        public TextView mTextView1;
       // public TextView mTextView2;
        public ImageView mDeleteImageBreak;
        public ImageView mDeleteImageLunch;
        public ImageView mDeleteImageDinner;
        public ImageView mReadImageBreak;
        public ImageView mReadImageLunch;
        public ImageView mReadImageDinner;

        public HomeViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
           // mImageView = itemView.findViewById(R.id.foodImage);
            mTextView1 = itemView.findViewById(R.id.foodName);
           // mTextView2 = itemView.findViewById(R.id.foodDesc);
            mDeleteImageBreak = itemView.findViewById(R.id.removeItem);
            mDeleteImageLunch = itemView.findViewById(R.id.removeItem);
            mDeleteImageDinner = itemView.findViewById(R.id.removeItem);
            mReadImageBreak = itemView.findViewById(R.id.readMore);
            mReadImageLunch = itemView.findViewById(R.id.readMore);
            mReadImageDinner = itemView.findViewById(R.id.readMore);

            mDeleteImageBreak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
            mReadImageBreak.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onReadClick(position);
                        }
                    }
                }
            });

            mDeleteImageLunch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
            mReadImageLunch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onReadClick(position);
                        }
                    }
                }
            });

            mDeleteImageDinner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
            mReadImageDinner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onReadClick(position);
                        }
                    }
                }
            });
        }
    }

    public HomeAdapter(ArrayList<HomeItem> HomeList){
        mHomeList = HomeList;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //gets our view holder from HomeItem.java and returns it in evh
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        return new HomeViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.HomeViewHolder holder, int position) {
        HomeItem currentItem = mHomeList.get(position);

       // holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getText1());
       // holder.mTextView2.setText(currentItem.getText2());
    }

    @Override
    public int getItemCount() {
        return mHomeList.size();
    }
}
