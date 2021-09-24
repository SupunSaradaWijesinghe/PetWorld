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

public class EditDetails extends AppCompatActivity {

    private TextInputEditText centerNameEdt, centerFeeEdt, centerForEdt, centerImgEdt, centerLinkEdt, centerDescEdt;
    private Button updateCenterBtn,deleteCenterBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String centerID;
    private CenterRVModal centerRVModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);
        firebaseDatabase = FirebaseDatabase.getInstance();
        centerNameEdt = findViewById(R.id.idEdtCenterName);
        centerFeeEdt = findViewById(R.id.idEdtCenterFee);
        centerForEdt = findViewById(R.id.idEdtCenterFor);
        centerImgEdt = findViewById(R.id.idEdtCenterImageLink);
        centerLinkEdt = findViewById(R.id.idEdtCenterLink);
        centerDescEdt = findViewById(R.id.idEdtCenterDesc);
        updateCenterBtn = findViewById(R.id.idBtnUpdateCenter);
        deleteCenterBtn = findViewById(R.id.idBtnDeleteCenter);
        loadingPB = findViewById(R.id.idPBLoading);
        centerRVModal = getIntent().getParcelableExtra("center");
        if(centerRVModal!=null){
            centerNameEdt.setText(centerRVModal.getCenterName());
            centerFeeEdt.setText(centerRVModal.getCenterFee());
            centerForEdt.setText(centerRVModal.getCenterFor());
            centerImgEdt.setText(centerRVModal.getCenterImag());
            centerLinkEdt.setText(centerRVModal.getCenterLink());
            centerDescEdt.setText(centerRVModal.getCenterDescription());
            centerID = centerRVModal.getCenterID();
        }

        databaseReference =firebaseDatabase.getReference("Centers").child(centerID);
        updateCenterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                loadingPB.setVisibility(View.VISIBLE);
                String centerName = centerNameEdt.getText().toString();
                String centerFee = centerFeeEdt.getText().toString();
                String centerFor = centerForEdt.getText().toString();
                String centerImg = centerImgEdt.getText().toString();
                String centerLink = centerLinkEdt.getText().toString();
                String centerDesc = centerDescEdt.getText().toString();

                Map<String,Object> map = new HashMap<>();
                map.put("CenterName",centerName);
                map.put("centerDescription",centerDesc);
                map.put("centerFee",centerFee);
                map.put("centerFor",centerFor);
                map.put("centerImag",centerImg);
                map.put("centerLink",centerLink);
                map.put("centerID",centerID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditDetails.this,"Center Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditDetails.this,MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditDetails.this,"Fail to update center", Toast.LENGTH_SHORT).show();


                    }
                });
            }
        });

        deleteCenterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                deleteCenter();

            }
        });
    }
    private  void deleteCenter(){
        databaseReference.removeValue();
        Toast.makeText(this,"Course Deleted", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditDetails.this,MainActivity.class));

    }
}