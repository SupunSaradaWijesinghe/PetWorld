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

public class EditPetRequest extends AppCompatActivity {

    private TextInputEditText etCategory, etBreed, etQty, etBudget, etContact, etRemarks;
    private Button btn_updateRequest,btn_deleteRequest;
    private ProgressBar pb_loading;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String requestID;
    private PetRequest petReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet_request);

        firebaseDatabase = FirebaseDatabase.getInstance();

        etCategory = findViewById(R.id.id_et_petCategory);
        etBreed = findViewById(R.id.id_et_petBreed);
        etQty = findViewById(R.id.id_et_qty);
        etBudget = findViewById(R.id.id_et_budget);
        etContact = findViewById(R.id.id_et_contact);
        etRemarks = findViewById(R.id.id_et_remarks);
        btn_updateRequest = findViewById(R.id.id_btn_updateRequest);
        btn_deleteRequest = findViewById(R.id.id_btn_deleteRequest);
        pb_loading = findViewById(R.id.id_pb);

        //getting the data from the previous activity
        petReq = getIntent().getParcelableExtra("request");
        if(petReq!=null){
            etCategory.setText(petReq.getPetCategory());
            etBreed.setText(petReq.getPetBreed());
            etQty.setText(petReq.getQty());
            etBudget.setText(petReq.getBudget());
            etContact.setText(petReq.getContactNo());
            etRemarks.setText(petReq.getRemarks());
            requestID = petReq.getRequestID();
        }

        databaseReference = firebaseDatabase.getReference("Requests").child(requestID);

        btn_updateRequest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                pb_loading.setVisibility(View.VISIBLE);
                String petCategory = etCategory.getText().toString();
                String breed = etBreed.getText().toString();
                String qty = etQty.getText().toString();
                String budget = etBudget.getText().toString();
                String contact = etContact.getText().toString();
                String remarks = etRemarks.getText().toString();

                //hash map instead of modal, work is same | another approach
                Map<String,Object> map = new HashMap<>();
                map.put("petCategory",petCategory);
                map.put("remarks",remarks);
                map.put("petBreed",breed);
                map.put("qty",qty);
                map.put("budget",budget);
                map.put("contactNo",contact);
                map.put("requestID",requestID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        pb_loading.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditPetRequest.this,"Request Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditPetRequest.this,MainPetRequest.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditPetRequest.this,"Fail to update request", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_deleteRequest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                deleteRequest();

            }
        });
    }
    private void deleteRequest(){
        databaseReference.removeValue();
        Toast.makeText( this, "Request deleted", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditPetRequest.this,MainPetRequest.class));
    }
}