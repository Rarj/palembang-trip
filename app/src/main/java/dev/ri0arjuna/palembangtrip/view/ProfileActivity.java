package dev.ri0arjuna.palembangtrip.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import dev.ri0arjuna.palembangtrip.R;

public class ProfileActivity extends AppCompatActivity {

    private ImageView circleImageView;
    private String imagePreview, username, email, telpon;
    private TextView textViewUsername, textViewEmail, textViewNoTelp, textViewProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        parsingProfile();

        textViewEmail = findViewById(R.id.tv_email);
        textViewUsername = findViewById(R.id.tv_username);
        textViewNoTelp = findViewById(R.id.tv_notelpon);
        circleImageView = findViewById(R.id.profile_image);

        setAttribute();

    }

    private void setAttribute() {
        Glide.with(ProfileActivity.this)
                .load(imagePreview)
                .into(circleImageView);
        textViewUsername.setText(username);
        textViewEmail.setText(email);
        textViewNoTelp.setText(telpon);
    }

    private void parsingProfile() {
        imagePreview = getIntent().getStringExtra("profile");
        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");
        telpon = getIntent().getStringExtra("no_telpon");
    }
}
