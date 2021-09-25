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

public class RequestAdap extends RecyclerView.Adapter<RequestAdap.ViewHolder> {
    int lastPos = -1;
    private ArrayList<PetRequest> petRequestArrayList;
    private Context context;
    private PostClickInterface postClickInterface;
    //private CenterRVAdapter.CenterClickInterface centerClickInterface;


    public RequestAdap(ArrayList<PetRequest> petRequestArrayList, Context context, PostClickInterface postClickInterface) {
        this.petRequestArrayList = petRequestArrayList;
        this.context = context;
        this.postClickInterface = postClickInterface;
    }

    @NonNull
    @Override
    public RequestAdap.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.request_post,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestAdap.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PetRequest petRequest = petRequestArrayList.get(position);
        holder.tv_category.setText(petRequest.getPetCategory());
        holder.tv_breed.setText(petRequest.getPetBreed());
        setAnimation(holder.itemView,position); //set animation
        Picasso.get().load(petRequest.getBudget()).into(holder.tv_post);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                postClickInterface.onPostClick(position);

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
        return petRequestArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_category,tv_breed;
        private ImageView tv_post;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_category = itemView.findViewById(R.id.id_tv_category);
            tv_breed = itemView.findViewById(R.id.id_tv_breed);
            tv_post = itemView.findViewById(R.id.id_req_post);

        }
    }
    public interface PostClickInterface{
        void onPostClick(int position);
    }
}
