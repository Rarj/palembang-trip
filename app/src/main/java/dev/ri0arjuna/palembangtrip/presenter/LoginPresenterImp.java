package dev.ri0arjuna.palembangtrip.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dev.ri0arjuna.palembangtrip.view.LoginView;

public class LoginPresenterImp implements LoginPresenter {

    private LoginView loginView;
    private FirebaseAuth firebaseAuth;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public LoginPresenterImp(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void login(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            loginView.emptyUsername();
        } else if (TextUtils.isEmpty(password)) {
            loginView.emptyPassword();
        } else if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)) {
            loginView.emptyBoth();
        } else if (password.length() < 6) {
            loginView.minimumCharacters();
        } else if (!username.matches(emailPattern)) {
            loginView.badEmailPatter();
        } else {
            firebaseAuth = FirebaseAuth.getInstance();

            firebaseAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                loginView.loginSuccess();
                            } else {
                                loginView.loginGagal();
                            }
                        }
                    });
        }
    }
}
