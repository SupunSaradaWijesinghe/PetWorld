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

public class AddDetails extends AppCompatActivity {

    private TextInputEditText centerNameEdt, centerFeeEdt, centerLocationEdt, centerImgEdt, centerLinkEdt, centerDescEdt;
    private Button addCenterBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String centerID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);
        centerNameEdt = findViewById(R.id.idEdtCenterName);
        centerFeeEdt = findViewById(R.id.idEdtCenterFee);
        centerLocationEdt = findViewById(R.id.idEdtCenterLocation);
        centerImgEdt = findViewById(R.id.idEdtCenterImageLink);
        centerLinkEdt = findViewById(R.id.idEdtCenterLink);
        centerDescEdt = findViewById(R.id.idEdtCenterDesc);
        addCenterBtn = findViewById(R.id.idBtnAddCenter);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Centers");

        addCenterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                String centerName = centerNameEdt.getText().toString();
                String centerFee = centerFeeEdt.getText().toString();
                String centerLocation = centerLocationEdt.getText().toString();
                String centerImg = centerImgEdt.getText().toString();
                String centerLink = centerLinkEdt.getText().toString();
                String centerDesc = centerDescEdt.getText().toString();
                centerID = centerName;
                CenterRVModal centerRVModal = new CenterRVModal(centerName, centerDesc,centerFee,centerLocation,centerImg,centerLink, centerID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.child(centerID).setValue(centerRVModal);
                        Toast.makeText(AddDetails.this, "Center Added...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddDetails.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddDetails.this,"Error is"+error.toString(), Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });


    }
}