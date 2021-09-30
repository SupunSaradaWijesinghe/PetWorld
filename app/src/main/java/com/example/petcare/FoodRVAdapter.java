package com.example.petcare;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class FoodRVAdapter extends RecyclerView.Adapter<FoodRVAdapter.ViewHolder> {
    int lastPos = -1;
    private ArrayList<FoodRVModal> foodRVModalArrayList;
    private Context context;
    private FoodClickInterface foodClickInterface;

    public FoodRVAdapter(ArrayList<FoodRVModal> foodRVModalArrayList, Context context, FoodClickInterface foodClickInterface) {
        this.foodRVModalArrayList = foodRVModalArrayList;
        this.context = context;
        this.foodClickInterface = foodClickInterface;
    }

    @NonNull
    @Override
    public FoodRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_rv_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            FoodRVModal foodRVModal = foodRVModalArrayList.get(position);
            holder.brandNameTV.setText(foodRVModal.getBrandName());
            holder.priceTV.setText("Rs."+foodRVModal.getPrice());
            setAnimation(holder.itemView,position);
            Picasso.get().load(foodRVModal.getImage()).into(holder.foodIV);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    foodClickInterface.onFoodClick(position);
                }
            });


    }


    private void setAnimation(View itemView, int position){
        if(position>lastPos){
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }


    @Override
    public int getItemCount() {
        return foodRVModalArrayList.size();
    }

    public interface FoodClickInterface{
        void onFoodClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView brandNameTV,priceTV;
        private ImageView foodIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            brandNameTV =itemView.findViewById(R.id.idTVBrandName);
            priceTV = itemView.findViewById(R.id.idTVPrice);
            foodIV = itemView.findViewById(R.id.idIVFood);

        }
    }




}
