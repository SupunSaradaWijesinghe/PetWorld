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

public class HomeActivity extends AppCompatActivity implements PetRVAdapter.PetClickInterface{
    private RecyclerView petRV;
    private ProgressBar loadingPB;
    private FloatingActionButton addFAB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<PetRVModal> petRVModalArrayList;
    private RelativeLayout bottomSheetRL;
    private PetRVAdapter petRVAdapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        petRV = findViewById(R.id.idRVPets);
        loadingPB = findViewById(R.id.idPBLoading);
        addFAB = findViewById(R.id.idAddFAB);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Pets");
        petRVModalArrayList=new ArrayList<>();
        bottomSheetRL = findViewById(R.id.idRLBSheet);
        mAuth = FirebaseAuth.getInstance();
        petRVAdapter = new PetRVAdapter(petRVModalArrayList,this,this);
        petRV.setLayoutManager(new LinearLayoutManager(this));
        petRV.setAdapter(petRVAdapter);
        addFAB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(HomeActivity.this,AddPetActivity.class));
            }
        });
        getAllpets();
    }

    private void getAllpets(){
        petRVModalArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable  String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                petRVModalArrayList.add(snapshot.getValue(PetRVModal.class));
                petRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable  String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                petRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull  DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                petRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                petRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }

    @Override
    public void onPetClick(int position) {
        displayBottomSheet(petRVModalArrayList.get(position));
    }
    private void displayBottomSheet(PetRVModal petRVModal) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog_binura,bottomSheetRL);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView petBreedTV = layout.findViewById(R.id.idTVPetBreed);
        TextView petDescTV = layout.findViewById(R.id.idTVDescrip);
        TextView petLocationTV = layout.findViewById(R.id.idTVLocationPet);
        TextView petPriceTV = layout.findViewById(R.id.idTVPricce);
        ImageView petIV = layout.findViewById(R.id.idIVPet);
        Button editBtn = layout.findViewById(R.id.idBtnPetEdit);
        Button viewDetailsBtn = layout.findViewById(R.id.idBtnPetViewDetails);

        petBreedTV.setText(petRVModal.getPetBreed());
        petDescTV.setText(petRVModal.getPetDescription());
        petLocationTV.setText(petRVModal.getPetLocation());
        petPriceTV.setText("Rs."+petRVModal.getPetPrice());
        Picasso.get().load(petRVModal.getPetImg()).into(petIV);

        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(HomeActivity.this,EditPetActivity.class);
                i.putExtra("pet",petRVModal);
                startActivity(i);
            }
        });
        viewDetailsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(petRVModal.getPetLink()));
                startActivity(i);
            }
        });
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
                Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(i);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}