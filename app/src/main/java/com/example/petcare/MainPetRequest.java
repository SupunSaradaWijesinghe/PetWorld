package com.example.petcare;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainPetRequest extends AppCompatActivity implements RequestAdap.PostClickInterface {

    private RecyclerView requestDis;
    private ProgressBar pb_loading;
    private FloatingActionButton btn_add_float;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<PetRequest> petRequestArrayList;
    private RelativeLayout ly_relative;
    private RequestAdap requestAdap;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pet_request);

        requestDis = findViewById(R.id.id_requests);
        pb_loading = findViewById(R.id.id_pb_Loading);
        btn_add_float = findViewById(R.id.id_btn_float);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Requests");
        petRequestArrayList=new ArrayList<>();
        ly_relative = findViewById(R.id.id_relativeLy);
        mAuth = FirebaseAuth.getInstance();
        requestAdap = new RequestAdap(petRequestArrayList,this,this);
        requestDis.setLayoutManager(new LinearLayoutManager(this));
        requestDis.setAdapter(requestAdap);
        btn_add_float.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainPetRequest.this,AddPetRequest.class));
            }
        });
        getAllPosts();
    }
    //get all posts from the database
    private void getAllPosts(){
        petRequestArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                pb_loading.setVisibility(View.GONE);
                petRequestArrayList.add(snapshot.getValue(PetRequest.class));
                requestAdap.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                pb_loading.setVisibility(View.GONE);
                requestAdap.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                pb_loading.setVisibility(View.GONE);
                requestAdap.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                pb_loading.setVisibility(View.GONE);
                requestAdap.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onPostClick(int position) {
        displayPostTouch(petRequestArrayList.get(position));
    }

    private void displayPostTouch(PetRequest petRequest){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.post_touch,ly_relative);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView tv_category = layout.findViewById(R.id.id_tv_categoryName);
        TextView tv_breed = layout.findViewById(R.id.id_tv_breed);
        TextView tv_budget = layout.findViewById(R.id.id_tv_budget);
        TextView tv_qty = layout.findViewById(R.id.id_tv_qty);
        TextView tv_cat = layout.findViewById(R.id.id_tv_cat);
        TextView tv_con = layout.findViewById(R.id.id_tv_con);
        TextView tv_rem = layout.findViewById(R.id.id_tv_rem);
        //ImageView centerIV = layout.findViewById(R.id.idIVCenter);
        Button btn_edit = layout.findViewById(R.id.id_btn_edit);
        //Button btn_view = layout.findViewById(R.id.id_btn_view);

        tv_category.setText(petRequest.getPetCategory()+" Request");
        tv_breed.setText("Breed : " + petRequest.getPetBreed());
        tv_budget.setText("Budget : " + petRequest.getBudget());
        tv_qty.setText("Qty : " + petRequest.getQty());
        tv_cat.setText("Category: " + petRequest.getPetCategory());
        tv_con.setText("Contact No : " + petRequest.getContactNo());
        tv_rem.setText("Remarks : " + petRequest.getRemarks());

        //Picasso.get().load(petRequest.getRemarks()).into(centerIV);

        btn_edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainPetRequest.this,EditPetRequest.class);
                i.putExtra("request",petRequest);
                startActivity(i);
            }
        });
        /**btn_view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainPetRequest.this,EditPetRequest.class);
                i.putExtra("request",petRequest);
                startActivity(i);
            }
        });**/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.idLogOut:
                Toast.makeText(this,"User Logged Out..", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                Intent i = new Intent(MainPetRequest.this, LoginActivity.class);
                startActivity(i);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}