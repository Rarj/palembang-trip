package dev.ri0arjuna.palembangtrip.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import dev.ri0arjuna.palembangtrip.MainActivity;
import dev.ri0arjuna.palembangtrip.R;
import dev.ri0arjuna.palembangtrip.presenter.LoginPresenter;
import dev.ri0arjuna.palembangtrip.presenter.LoginPresenterImp;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private ProgressDialog progressDialog;
    private LoginPresenter loginPresenter;
    public TextInputEditText editTextUsername, editTextPassword;
    private Button buttonLogin, buttonPindahRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.edt_email_login);
        editTextPassword = findViewById(R.id.edt_password_login);
        buttonLogin = findViewById(R.id.button_login);
        buttonPindahRegister = findViewById(R.id.button_pindah_register);

        loginPresenter = new LoginPresenterImp(this);
        progressDialog = new ProgressDialog(this);

        buttonPindahRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                loginPresenter.login(editTextUsername.getText().toString(), editTextPassword.getText().toString());
                hideKeyboard(v);
            }
        });
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void badEmailPatter() {
        progressDialog.dismiss();
        editTextUsername.setError("Email not valid!");
        editTextUsername.requestFocus();
        Toast.makeText(this, "email not valid", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void emptyBoth() {
        progressDialog.dismiss();
        editTextPassword.setError("Required!");
        editTextUsername.requestFocus();
        editTextUsername.setError("Required!");
        editTextPassword.requestFocus();
    }

    @Override
    public void emptyUsername() {
        progressDialog.dismiss();
        editTextUsername.setError("Required!");
        editTextUsername.requestFocus();
    }

    @Override
    public void emptyPassword() {
        progressDialog.dismiss();
        editTextPassword.requestFocus();
        editTextPassword.setError("Required!");
    }

    @Override
    public void minimumCharacters() {
        progressDialog.dismiss();
        editTextPassword.requestFocus();
        editTextPassword.setError("Minimum 6 characters!");
    }

    @Override
    public void loginSuccess() {
        progressDialog.dismiss();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void loginGagal() {
        final PrettyDialog prettyDialog = new PrettyDialog(this);
        prettyDialog
                .setTitle("ERROR")
                .setMessage("Login failed!")
                .addButton("OK", R.color.pdlg_color_white, R.color.pdlg_color_red, new PrettyDialogCallback() {
                    @Override
                    public void onClick() {
                        prettyDialog.dismiss();
                        progressDialog.dismiss();
                    }
                })
                .show();
    }
}
