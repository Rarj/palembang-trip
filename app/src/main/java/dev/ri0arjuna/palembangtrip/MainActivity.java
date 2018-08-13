package dev.ri0arjuna.palembangtrip;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.ri0arjuna.palembangtrip.adapter.AdapterSubFeatures;
import dev.ri0arjuna.palembangtrip.model.ModelSubFeatures;
import dev.ri0arjuna.palembangtrip.view.ProfileActivity;
import dev.ri0arjuna.palembangtrip.adapter.AdapterMainFeatures;
import dev.ri0arjuna.palembangtrip.model.ModelMainFeatures;
import dev.ri0arjuna.palembangtrip.presenter.MainPresenter;
import dev.ri0arjuna.palembangtrip.presenter.MainPresenterImp;
import dev.ri0arjuna.palembangtrip.view.LoginActivity;
import dev.ri0arjuna.palembangtrip.view.MainView;
import dev.ri0arjuna.palembangtrip.view.SubFeaturesActivity;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class MainActivity extends AppCompatActivity implements MainView {

    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().getRoot();
    private String uuid = firebaseUser.getUid();
    private String username, no_telpon, gambar, email;
    private String gambarFitur, id;
    private TextView textViewUsername, textViewNoTelpon;
    private CircleImageView circleImageViewProfile;
    private MainPresenter mainPresenter;
    private RecyclerView recyclerView;
    private AdapterMainFeatures adapterMainFeatures;
    private List<ModelMainFeatures> modelMainFeatures;
    private ModelMainFeatures fitur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainPresenter = new MainPresenterImp(this);

        recyclerView = findViewById(R.id.recycler_main_features);
        Toolbar toolbar = findViewById(R.id.toolbar);
        textViewUsername = findViewById(R.id.tv_username);
        textViewNoTelpon = findViewById(R.id.tv_no_telpon);

        circleImageViewProfile = findViewById(R.id.profile_image);
        setSupportActionBar(toolbar);

        mainPresenter.mainFeatures(fitur);
        mainPresenter.database(username, gambar, no_telpon);
        mainPresenter.koneksi(MainActivity.this);

        circleImageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra("profile", gambar);
                intent.putExtra("username", username);
                intent.putExtra("no_telpon", no_telpon);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });
    }

    @Override
    public void koneksiSuccess() {
        Toast.makeText(this, "konek!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void koneksigagal() {
        Toast.makeText(this, "gagal konek!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadDatabase() {
        new setUserAndNoTelp().execute();
    }

    @Override
    public void loadMainFeatures() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("MainFeatures");

        final Query asc = databaseReference.orderByChild("id");

        asc.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                modelMainFeatures = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    fitur = dataSnapshot1.getValue(ModelMainFeatures.class);
                    ModelMainFeatures fire = new ModelMainFeatures();

                    gambarFitur = fitur.getGambar();
                    id = fitur.getId();

                    fire.setGambar(gambarFitur);
                    fire.setId(id);

                    Toast.makeText(MainActivity.this, "Dapet: " + id, Toast.LENGTH_SHORT).show();

                    modelMainFeatures.add(fire);

                    adapterMainFeatures = new AdapterMainFeatures(MainActivity.this, modelMainFeatures);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
                    recyclerView.setAdapter(adapterMainFeatures);
                    adapterMainFeatures.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @SuppressLint("StaticFieldLeak")
    public class setUserAndNoTelp extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uuid);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    gambar = String.valueOf(dataSnapshot.child("profile_image").getValue());
                    username = String.valueOf(dataSnapshot.child("username").getValue());
                    no_telpon = String.valueOf(dataSnapshot.child("no_telpon").getValue());
                    email = String.valueOf(dataSnapshot.child("email").getValue());

                    Glide.with(MainActivity.this)
                            .load(gambar)
                            .into(circleImageViewProfile);
                    textViewUsername.setText(username);
                    textViewNoTelpon.setText(no_telpon);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_prefs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        new PrettyDialog(this)
                .setTitle("Logout")
                .setMessage("Are you sure want to logout?")
                .addButton("Logout", R.color.pdlg_color_white, R.color.pdlg_color_red, new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                    }
                })
                .show();
    }
}
