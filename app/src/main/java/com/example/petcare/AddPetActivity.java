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

public class AddPetActivity extends AppCompatActivity {

    private TextInputEditText petBreedEdt, petPriceEdt, petLocationEdt, petImgEdt, petLinkEdt, petDescEdt;
    private Button addPetBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String petID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);
        petBreedEdt = findViewById(R.id.idEdtBreedName);
        petPriceEdt = findViewById(R.id.idEdtPetPrice);
        petLocationEdt = findViewById(R.id.idEdtPetLocation);
        petImgEdt = findViewById(R.id.idEdtPetImageLink);
        petLinkEdt = findViewById(R.id.idEdtPetLink);
        petDescEdt = findViewById(R.id.idEdtPetDesc);
        addPetBtn = findViewById(R.id.idBtnAddpet);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Pets");

        addPetBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                String petBreed = petBreedEdt.getText().toString();
                String petPrice = petPriceEdt.getText().toString();
                String petLocation = petLocationEdt.getText().toString();
                String petImg = petImgEdt.getText().toString();
                String petLink = petLinkEdt.getText().toString();
                String petDesc = petDescEdt.getText().toString();
                petID = petBreed;
                PetRVModal petRVModal = new PetRVModal(petBreed,petDesc,petPrice,petLocation,petImg,petLink,petID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.child(petID).setValue(petRVModal);
                        Toast.makeText(AddPetActivity.this, "Pet Added...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddPetActivity.this, HomeActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddPetActivity.this,"Error is"+error.toString(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


    }
}