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

import java.util.HashMap;
import java.util.Map;

public class EditPetActivity extends AppCompatActivity {

    private TextInputEditText petBreedEdt, petPriceEdt, petLocationEdt, petImgEdt, petLinkEdt, petDescEdt;
    private Button updatePetBtn, deletePetBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String petID;
    private PetRVModal petRVModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet);
        firebaseDatabase = FirebaseDatabase.getInstance();
        petBreedEdt = findViewById(R.id.idEdtBreedName);
        petPriceEdt = findViewById(R.id.idEdtPetPrice);
        petLocationEdt = findViewById(R.id.idEdtPetLocation);
        petImgEdt = findViewById(R.id.idEdtPetImageLink);
        petLinkEdt = findViewById(R.id.idEdtPetLink);
        petDescEdt = findViewById(R.id.idEdtPetDesc);
        updatePetBtn = findViewById(R.id.idBtnUpdatePet);
        deletePetBtn = findViewById(R.id.idBtnDeletePet);
        loadingPB = findViewById(R.id.idPBLoading);
        petRVModal = getIntent().getParcelableExtra("pet");
        if(petRVModal!=null){
            petBreedEdt.setText(petRVModal.getPetBreed());
            petPriceEdt.setText(petRVModal.getPetPrice());
            petLocationEdt.setText(petRVModal.getPetLocation());
            petImgEdt.setText(petRVModal.getPetImg());
            petLinkEdt.setText(petRVModal.getPetLink());
            petDescEdt.setText(petRVModal.getPetDescription());
            petID = petRVModal.getPetID();
        }

        databaseReference =firebaseDatabase.getReference("Pets").child(petID);
        updatePetBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loadingPB.setVisibility(View.VISIBLE);
                String petBreed = petBreedEdt.getText().toString();
                String petPrice = petPriceEdt.getText().toString();
                String petLocation = petLocationEdt.getText().toString();
                String petImg = petImgEdt.getText().toString();
                String petLink = petLinkEdt.getText().toString();
                String petDesc = petDescEdt.getText().toString();

                Map<String,Object> map = new HashMap<>();
                map.put("petBreed",petBreed);
                map.put("petDescription",petDesc);
                map.put("petPrice",petPrice);
                map.put("petLocation",petLocation);
                map.put("petImg",petImg);
                map.put("petLink",petLink);
                map.put("petID",petID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditPetActivity.this,"Pet is Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditPetActivity.this,HomeActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditPetActivity.this,"Failed to update pet", Toast.LENGTH_SHORT).show();


                    }
                });
            }
        });

        deletePetBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                deletePet();

            }
        });
    }
    private  void deletePet(){
        databaseReference.removeValue();
        Toast.makeText(this,"Pet is Deleted", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditPetActivity.this,HomeActivity.class));

    }
}