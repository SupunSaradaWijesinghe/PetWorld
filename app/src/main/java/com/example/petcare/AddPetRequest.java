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

public class AddPetRequest extends AppCompatActivity {

    private TextInputEditText etCategory, etBreed, etQty, etBudget, etContact, etRemarks;
    private Button btn_createRequest;
    private ProgressBar pb_loading;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String requestID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet_request);

        etCategory = findViewById(R.id.id_et_petCategory);
        etBreed = findViewById(R.id.id_et_petBreed);
        etQty = findViewById(R.id.id_et_qty);
        etBudget = findViewById(R.id.id_et_budget);
        etContact = findViewById(R.id.id_et_contact);
        etRemarks = findViewById(R.id.id_et_remarks);
        btn_createRequest = findViewById(R.id.id_btn_createRequest);
        pb_loading = findViewById(R.id.id_pb);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Requests");

        btn_createRequest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                pb_loading.setVisibility(View.VISIBLE);
                String petCategory = etCategory.getText().toString();
                String breed = etBreed.getText().toString();
                String qty = etQty.getText().toString();
                String budget = etBudget.getText().toString();
                String contact = etContact.getText().toString();
                String remarks = etRemarks.getText().toString();
                requestID = breed;
                PetRequest petReq = new PetRequest(petCategory,remarks,breed,qty,budget,contact,requestID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        pb_loading.setVisibility(View.GONE);
                        databaseReference.child(requestID).setValue(petReq);
                        Toast.makeText(AddPetRequest.this, "Request created...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddPetRequest.this, MainPetRequest.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddPetRequest.this,"Error is"+error.toString(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    }
}