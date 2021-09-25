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

public class MainActivity extends AppCompatActivity implements CenterRVAdapter.CenterClickInterface{
    private RecyclerView centerRV;
    private ProgressBar loadingPB;
    private FloatingActionButton addFAB;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<CenterRVModal> centerRVModalArrayList;
    private RelativeLayout bottomSheetRL;
    private CenterRVAdapter centerRVAdapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        centerRV = findViewById(R.id.idRVCenters);
        loadingPB = findViewById(R.id.idPBLoading);
        addFAB = findViewById(R.id.idAddFAB);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Centers");
        centerRVModalArrayList=new ArrayList<>();
        bottomSheetRL = findViewById(R.id.idRLBSheet);
        mAuth = FirebaseAuth.getInstance();
        centerRVAdapter = new CenterRVAdapter(centerRVModalArrayList,this,this);
        centerRV.setLayoutManager(new LinearLayoutManager(this));
        centerRV.setAdapter(centerRVAdapter);
        addFAB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this,AddDetails.class));
            }
        });
    }

    private void getAllcenters(){
        centerRVModalArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable  String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                centerRVModalArrayList.add(snapshot.getValue(CenterRVModal.class));
                centerRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable  String previousChildName) {
                 loadingPB.setVisibility(View.GONE);
                 centerRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull  DataSnapshot snapshot) {
                loadingPB.setVisibility(View.GONE);
                centerRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {
                loadingPB.setVisibility(View.GONE);
                centerRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {

            }
        });
    }

    @Override
    public void onCenterClick(int position) {
       displayBottomSheet(centerRVModalArrayList.get(position));
    }
     private void displayBottomSheet(CenterRVModal centerRVModal) {
          final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
          View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialog,bottomSheetRL);
          bottomSheetDialog.setContentView(layout);
          bottomSheetDialog.setCancelable(false);
          bottomSheetDialog.setCanceledOnTouchOutside(true);
          bottomSheetDialog.show();

          TextView centerNameTV = layout.findViewById(R.id.idTVCenterName);
         TextView centerDescTV = layout.findViewById(R.id.idTVDescription);
         TextView centerForTV = layout.findViewById(R.id.idTVFor);
         TextView centerFeeTV = layout.findViewById(R.id.idTVFee);
         ImageView centerIV = layout.findViewById(R.id.idIVCenter);
         ImageView editBtn = layout.findViewById(R.id.idBtnEdit);
         ImageView viewDetailsBtn = layout.findViewById(R.id.idBtnViewDetails);

         centerNameTV.setText(centerRVModal.getCenterName());
         centerDescTV.setText(centerRVModal.getCenterDescription());
         centerForTV.setText(centerRVModal.getCenterFor());
         centerFeeTV.setText("Rs."+centerRVModal.getCenterFee());
         Picasso.get().load(centerRVModal.getCenterImag()).into(centerIV);

         editBtn.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v){
                Intent i = new Intent(MainActivity.this,EditDetails.class);
                i.putExtra("center",centerRVModal);
                startActivity(i);
             }
         });
         viewDetailsBtn.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v){
                 Intent i = new Intent(Intent.ACTION_VIEW);
                 i.setData(Uri.parse(centerRVModal.getCenterLink()));
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
          }

        return super.onOptionsItemSelected(item);
    }
}