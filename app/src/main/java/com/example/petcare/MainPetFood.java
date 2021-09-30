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

public class MainPetFood extends AppCompatActivity implements FoodRVAdapter.FoodClickInterface{

    private RecyclerView foodRV;
    private ProgressBar loadingPB;
    private FloatingActionButton addFAB1;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<FoodRVModal> foodRVModalArrayList;
    private RelativeLayout bottomSheetRl1;
    private FoodRVAdapter foodRVAdapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pet_food);
        foodRV = findViewById(R.id.idRVFoods);
        loadingPB = findViewById(R.id.idPBLoading);
        addFAB1 = findViewById(R.id.idAddFAB1);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Food");
        foodRVModalArrayList = new ArrayList<>();
        bottomSheetRl1 =findViewById(R.id.idRLBSheet1);
        mAuth = FirebaseAuth.getInstance();
        foodRVAdapter = new FoodRVAdapter(foodRVModalArrayList,this,this);
        foodRV.setLayoutManager(new LinearLayoutManager(this));
        foodRV.setAdapter(foodRVAdapter);
        addFAB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPetFood.this,AddPetFood.class));

            }
        });

        getAllFoods();

    }

    private void getAllFoods(){

        foodRVModalArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                loadingPB.setVisibility(View.GONE);
                foodRVModalArrayList.add(snapshot.getValue(FoodRVModal.class));
                foodRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                loadingPB.setVisibility(View.GONE);
                foodRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                loadingPB.setVisibility(View.GONE);
                foodRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                loadingPB.setVisibility(View.GONE);
                foodRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onFoodClick(int position) {
            displayBottomSheet(foodRVModalArrayList.get(position));
    }
    private void displayBottomSheet(FoodRVModal foodRVModal){

        final BottomSheetDialog bottomSheetDialog1 =new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottem_sheet_dialog1,bottomSheetRl1);
        bottomSheetDialog1.setContentView(layout);
        bottomSheetDialog1.setCancelable(false);
        bottomSheetDialog1.setCanceledOnTouchOutside(true);
        bottomSheetDialog1.show();

        TextView brandNameTV =layout.findViewById(R.id.idTVBrandName);
        TextView foodTypeTV =layout.findViewById(R.id.idTVFoodType);
        TextView sellerNameTV =layout.findViewById(R.id.idTVsellerName);
        TextView PriceTV =layout.findViewById(R.id.idTVPrice);
        TextView weightTV =layout.findViewById(R.id.idTVWeight);
        TextView contactNumber =layout.findViewById(R.id.idTVContactNumber);
        TextView description1TV =layout.findViewById(R.id.idTVDescription1);
        ImageView BrandIV =layout.findViewById(R.id.idIVBrand);
        Button editBtn1 =layout.findViewById(R.id.idBtnEdit1);
        Button viewDetailsBtn1 =layout.findViewById(R.id.idBtnViewDetails1);


        brandNameTV.setText("Brand Name : " +foodRVModal.getBrandName());
        foodTypeTV.setText("Food Type : " +foodRVModal.getFoodType());
        sellerNameTV.setText("Seller Name : "+ foodRVModal.getSellerName());
        PriceTV.setText("Price : "+ "Rs."+foodRVModal.getPrice());
        weightTV.setText("Weight : "+ foodRVModal.getWeight()+"kg");
        contactNumber.setText("Contact Number : " +foodRVModal.getContactNumber());
        description1TV.setText("Message : " +foodRVModal.getDescription());
        Picasso.get().load(foodRVModal.getImage()).into(BrandIV);


        editBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(MainPetFood.this,EditPetFood.class);
                i.putExtra("food",foodRVModal);
                startActivity(i);

            }
        });


        viewDetailsBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(foodRVModal.getSellerId()));
                startActivity(i);
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
                Intent i = new Intent(MainPetFood.this,LoginActivity.class);
                startActivity(i);
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
