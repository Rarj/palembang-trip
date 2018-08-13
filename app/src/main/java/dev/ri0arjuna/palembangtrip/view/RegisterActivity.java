package dev.ri0arjuna.palembangtrip.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dev.ri0arjuna.palembangtrip.MainActivity;
import dev.ri0arjuna.palembangtrip.R;
import dev.ri0arjuna.palembangtrip.pref.PrefManager;
import dev.ri0arjuna.palembangtrip.presenter.RegisterPresenter;
import dev.ri0arjuna.palembangtrip.presenter.RegisterPresenterImp;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class RegisterActivity extends AppCompatActivity implements RegisterView {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    private ProgressDialog progressDialog;
    private RegisterPresenter registerPresenter;
    private TextInputEditText editTextEmail, editTextPassword, editTextNoTelp;
    private Button buttonRegister, buttonPindahLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerPresenter = new RegisterPresenterImp(this);
        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseUser != null) {
                    Log.i(RegisterActivity.class.getSimpleName(), "Usernya udah login.");
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION);

                    startActivity(intent);
                    finish();
                }
            }
        };

        editTextEmail = findViewById(R.id.edt_email_regis);
        editTextPassword = findViewById(R.id.edt_password_regis);
        editTextNoTelp = findViewById(R.id.edt_no_telpon);
        buttonRegister = findViewById(R.id.button_regis);
        buttonPindahLogin = findViewById(R.id.button_pindah_login);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                registerPresenter.register(editTextEmail.getText().toString(), editTextPassword.getText().toString(), editTextNoTelp.getText().toString());
                hideKeyboard(v);
            }
        });

        buttonPindahLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    private void hideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PrefManager prefManager = new PrefManager(getApplicationContext());
        prefManager.setFirstTimeLaunch(true);
    }

    @Override
    public void badEmailPattern() {
        progressDialog.dismiss();
        final PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("ERROR")
                .setMessage("Email not valid!")
                .addButton("OK", R.color.pdlg_color_white, R.color.pdlg_color_red, new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        prettyDialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void emptyEmail() {
        progressDialog.dismiss();
        editTextEmail.requestFocus();
        editTextEmail.setError("Required");
    }

    @Override
    public void emptyNoTelp() {
        progressDialog.dismiss();
        editTextNoTelp.requestFocus();
        editTextNoTelp.setError("Required");
    }

    @Override
    public void emptyPassword() {
        progressDialog.dismiss();
        editTextPassword.requestFocus();
        editTextPassword.setError("Required");
    }

    @Override
    public void registerSuccess() {
        Toast.makeText(this, "Berhasil!\n\n", Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void lengthMinimum() {
        progressDialog.dismiss();
        final PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("Password")
                .setMessage("Password must be minimun 6 characters!")
                .addButton("OK", R.color.pdlg_color_white, R.color.pdlg_color_red, new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        prettyDialog.dismiss();
                        editTextPassword.requestFocus();
                    }
                })
                .show();
    }

    @Override
    public void registerFailed() {
        progressDialog.dismiss();
        final PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("ERROR")
                .setMessage("Credential failed!")
                .addButton("OK", R.color.pdlg_color_white, R.color.pdlg_color_red, new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        prettyDialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void emailAlreadyExists() {
        progressDialog.dismiss();
        final PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("ERROR")
                .setMessage("Email already exists!")
                .addButton("OK", R.color.pdlg_color_white, R.color.pdlg_color_red, new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        prettyDialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null) {
            firebaseAuth.removeAuthStateListener(authStateListener);
        }
    }

}