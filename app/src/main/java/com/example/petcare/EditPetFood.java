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

public class EditPetFood extends AppCompatActivity {

    private TextInputEditText sellerNameEdt,brandNameEdt,foodTypeEtd,weightEdt,priceEdt,contactNumberEdt,descriptionEdt,imageLinkEdt;
    private Button updateFoodBtn,deleteFoodBtn;
    private ProgressBar loadingPB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String sellerID;
    private FoodRVModal foodRVModal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pet_food);
        firebaseDatabase = FirebaseDatabase.getInstance();
        sellerNameEdt =findViewById(R.id.idEdtSellerName);
        brandNameEdt =findViewById(R.id.idEdtBrandName);
        foodTypeEtd =findViewById(R.id.idEdtFoodType);
        weightEdt =findViewById(R.id.idEdtWeight);
        priceEdt =findViewById(R.id.idEdtPrice);
        contactNumberEdt =findViewById(R.id.idEdtContectNumber);
        descriptionEdt=findViewById(R.id.idEdtDescription);
        imageLinkEdt=findViewById(R.id.idEdtImageLink);
        updateFoodBtn =findViewById(R.id.idBtnUpdateFood);
        deleteFoodBtn =findViewById(R.id.iidBtnDeleteFood);
        loadingPB= findViewById(R.id.idPBLoading);
        foodRVModal =getIntent().getParcelableExtra("food");


        if(foodRVModal!=null){

            sellerNameEdt.setText(foodRVModal.getSellerName());
            brandNameEdt.setText(foodRVModal.getBrandName());
            foodTypeEtd.setText(foodRVModal.getFoodType());
            weightEdt.setText(foodRVModal.getWeight());
            priceEdt.setText(foodRVModal.getPrice());
            contactNumberEdt.setText(foodRVModal.getContactNumber());
            descriptionEdt.setText(foodRVModal.getDescription());
            imageLinkEdt.setText(foodRVModal.getImage());
            sellerID = foodRVModal.getSellerId();
        }

        databaseReference = firebaseDatabase.getReference("Food").child(sellerID);
        updateFoodBtn.setOnClickListener(new View.OnClickListener() {
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

                Map<String,Object> map = new HashMap<>();
                map.put("sellerName",sellerName);
                map.put("brandName",brandName);
                map.put("foodType",foodType);
                map.put("weight",weight);
                map.put("price",price);
                map.put("contactNumber",contactNumber);
                map.put("description",description);
                map.put("image",image);
                map.put("sellerID",sellerID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        loadingPB.setVisibility(View.GONE);
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditPetFood.this, "Successfully Updated ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditPetFood.this,MainPetFood.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditPetFood.this, "Update Unsuccessful", Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });

        deleteFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFood();
            }
        });
    }

    private void deleteFood(){
        databaseReference.removeValue();
        Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditPetFood.this,MainPetFood.class));

    }


}
