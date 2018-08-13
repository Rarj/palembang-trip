package dev.ri0arjuna.palembangtrip.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import dev.ri0arjuna.palembangtrip.R;
import dev.ri0arjuna.palembangtrip.adapter.AdapterSubFeatures;
import dev.ri0arjuna.palembangtrip.model.ModelSubFeatures;

public class SubFeaturesActivity extends AppCompatActivity {

    private List<ModelSubFeatures> modelSubFeatures;
    private RecyclerView recyclerView;
    private AdapterSubFeatures adapterSubFeatures;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().getRoot();
    private String nama, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_features);

        id = getIntent().getStringExtra("id");

        recyclerView = findViewById(R.id.recycler_sub_features);

        loadSubFeatures();
    }

    private void loadSubFeatures() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Wisata").child(id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelSubFeatures = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    ModelSubFeatures values = dataSnapshot1.getValue(ModelSubFeatures.class);
                    ModelSubFeatures fire = new ModelSubFeatures();

                    id = values.getId();
                    nama = values.getNama();

                    fire.setId(id);
                    fire.setNama(nama);

                    modelSubFeatures.add(fire);

                    Toast.makeText(SubFeaturesActivity.this, "dapate nama:" + nama+id, Toast.LENGTH_SHORT).show();

                    adapterSubFeatures = new AdapterSubFeatures(SubFeaturesActivity.this, modelSubFeatures);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(SubFeaturesActivity.this));
                    recyclerView.setAdapter(adapterSubFeatures);
                    adapterSubFeatures.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                databaseError.getMessage();
            }
        });
    }
}
