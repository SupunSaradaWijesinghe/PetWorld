package com.example.petcare;

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

public class PetRVAdapter extends RecyclerView.Adapter<PetRVAdapter.ViewHolder>{
    private ArrayList<PetRVModal> petRVModalArrayList;
    private Context context;
    int lastPos = -1;
    private PetClickInterface petClickInterface;

    public PetRVAdapter(ArrayList<PetRVModal> petRVModalArrayList, Context context, PetClickInterface petClickInterface) {
        this.petRVModalArrayList = petRVModalArrayList;
        this.context = context;
        this.petClickInterface = petClickInterface;
    }

    @NonNull
    @Override
    public PetRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.pet_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PetRVModal petRVModal = petRVModalArrayList.get(position);
        holder.petBreedTV.setText(petRVModal.getPetBreed());
        holder.petPriceTV.setText("Rs."+petRVModal.getPetPrice());
        setAnimation(holder.itemView,position);
        Picasso.get().load(petRVModal.getPetImg()).into(holder.petIV);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                petClickInterface.onPetClick(position);

            }
        });

    }

    private void setAnimation(View itemView,int position){
        if(position > lastPos) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }
    @Override
    public int getItemCount() {
        return petRVModalArrayList.size();
    }

    public interface PetClickInterface{
        void onPetClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView petBreedTV,petPriceTV;
        private ImageView petIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            petBreedTV = itemView.findViewById(R.id.idTVPetBreed);
            petPriceTV = itemView.findViewById(R.id.idTVPrice);
            petIV = itemView.findViewById(R.id.idIVPet);

        }
    }
}
