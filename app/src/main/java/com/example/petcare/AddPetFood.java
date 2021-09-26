package com.example.petcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddPetFood extends AppCompatActivity {

    private TextInputEditText sellerNameEdt,brandNameEdt,foodTypeEtd,weightEdt,priceEdt,contactNumberEdt,descriptionEdt,imageLinkEdt;
    private Button submitFoodBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String sellerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet_food);
        sellerNameEdt =findViewById(R.id.idEdtSellerName);
        brandNameEdt =findViewById(R.id.idEdtBrandName);
        foodTypeEtd =findViewById(R.id.idEdtFoodType);
        weightEdt =findViewById(R.id.idEdtWeight);
        priceEdt =findViewById(R.id.idEdtPrice);
        contactNumberEdt =findViewById(R.id.idEdtContectNumber);
        descriptionEdt=findViewById(R.id.idEdtDescription);
        imageLinkEdt=findViewById(R.id.idEdtImageLink);
        submitFoodBtn =findViewById(R.id.idBtnSubmitFood);
        loadingPB= findViewById(R.id.idPBLoading);
        firebaseDatabase =FirebaseDatabase.getInstance();
        databaseReference =firebaseDatabase.getReference("Food");

        submitFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                String sellerName = sellerNameEdt.getText().toString();
                String brandName = brandNameEdt.getText().toString();
                String foodType = foodTypeEtd.getText().toString();
                String weight = weightEdt.getText().toString();
                String price = priceEdt.getText().toString();
                String contactNumber = contactNumberEdt.getText().toString();
                String description = descriptionEdt.getText().toString();
                String image = imageLinkEdt.getText().toString();
                sellerID = sellerName;
                FoodRVModal foodRVModal = new FoodRVModal(sellerName,brandName,foodType,weight,price,contactNumber,description,image,sellerID);
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.child(sellerID).setValue(foodRVModal);
                        Toast.makeText(AddPetFood.this, "Pet Food Added successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddPetFood.this,MainPetFood.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddPetFood.this, "unsuccessful ERROR"+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}