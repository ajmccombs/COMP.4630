package com.aokellermann.nutritionapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {//needed for Old filter method implements Filterable {

    private ArrayList<ExampleItem> mExampleList;
    private OnItemClickListener mListener;


    public interface OnItemClickListener{
        void onAddClick(int position);
        void onReadClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

        //public ImageView mImageView;
        public TextView mTextView1;
        //public TextView mTextView2;
        public ImageView mAddItem;
        public ImageView mReadMore;

        public ExampleViewHolder( View itemView, OnItemClickListener listener) {
            super(itemView);
            //mImageView = itemView.findViewById(R.id.foodImage);
            mTextView1 = itemView.findViewById(R.id.foodName);
           // mTextView2 = itemView.findViewById(R.id.foodDesc);
            mAddItem = itemView.findViewById(R.id.addItem);
            mReadMore = itemView.findViewById(R.id.readMore);

            mAddItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onAddClick(position);
                        }
                    }
                }

            });
            mReadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onReadClick(position);
                        }
                    }
                }

            });
        }
    }

    public ExampleAdapter(ArrayList<ExampleItem> exampleList){
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        //gets our view holder from ExampleItem.java and returns it in evh
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item, parent, false);
        return new ExampleViewHolder(v, mListener);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);

        //holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getText1());
        //holder.mTextView2.setText(currentItem.getText2());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

    public void filterList(ArrayList<ExampleItem> filteredList){
        mExampleList = filteredList;
        notifyDataSetChanged();
    }


/* Old way of filtering
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<ExampleItem> filteredList = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(mExampleListFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (ExampleItem item : mExampleListFull) {
                        if (item.getText1().toLowerCase().contains(filterPattern)) { //compare with food name
                            filteredList.add(item);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;

                return results;
            }


            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mExampleList.clear();
                List temp = (List) filterResults.values;
                mExampleList.addAll(temp);
                notifyDataSetChanged();
            }
        };
    }*/


}
