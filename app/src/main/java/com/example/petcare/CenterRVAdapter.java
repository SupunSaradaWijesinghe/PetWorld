package com.example.petcare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CenterRVAdapter extends RecyclerView.Adapter<CenterRVAdapter.ViewHolder>{
    private ArrayList<CenterRVModal> centerRVModalArrayList;
    private Context context;
    int lastPos = -1;
    private CenterClickInterface centerClickInterface;

    public CenterRVAdapter(ArrayList<CenterRVModal> centerRVModalArrayList, Context context, CenterClickInterface centerClickInterface) {
        this.centerRVModalArrayList = centerRVModalArrayList;
        this.context = context;
        this.centerClickInterface = centerClickInterface;
    }

    @NonNull
    @Override
    public CenterRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.course_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CenterRVModal centerRVModal = centerRVModalArrayList.get(position);
        holder.centerNameTV.setText(centerRVModal.getCenterName());
        holder.centerFeeTV.setText("Rs."+centerRVModal.getCenterFee());
        Picasso.get().load(centerRVModal.getCenterImag()).into(holder.centerIV);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });

    }

    @Override
    public int getItemCount() {
        return centerRVModalArrayList.size();
    }

    public interface CenterClickInterface{
        void onCenterClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView centerNameTV,centerFeeTV;
        private ImageView centerIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            centerNameTV = itemView.findViewById(R.id.idTVCenterName);
            centerFeeTV = itemView.findViewById(R.id.idTVFee);
            centerIV = itemView.findViewById(R.id.idIVCenter);

        }
    }
}
