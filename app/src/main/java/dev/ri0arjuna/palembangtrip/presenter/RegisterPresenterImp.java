package dev.ri0arjuna.palembangtrip.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import dev.ri0arjuna.palembangtrip.view.RegisterView;

public class RegisterPresenterImp implements RegisterPresenter {

    private RegisterView registerView;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().getRoot();
    private FirebaseAuth firebaseAuth;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public RegisterPresenterImp(RegisterView registerView) {
        this.registerView = registerView;
    }

    @Override
    public void register(final String email, String password, final String no_telpon) {

        firebaseAuth = FirebaseAuth.getInstance();

        if (TextUtils.isEmpty(email)) {
            registerView.emptyEmail();
        } else if (TextUtils.isEmpty(no_telpon)) {
            registerView.emptyNoTelp();
        } else if (TextUtils.isEmpty(password)) {
            registerView.emptyPassword();
        } else if (password.length() < 6) {
            registerView.lengthMinimum();
        } else if (!email.matches(emailPattern)) {
            registerView.badEmailPattern();
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        String splitUsername = email.substring(0, email.indexOf("@"));

                        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = null;
                        if (currentUser != null) {
                            uid = currentUser.getUid();
                        }

                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                        try {
                            HashMap<String, String> userMap = new HashMap<>();

                            userMap.put("email", email);
                            userMap.put("username", splitUsername);
                            userMap.put("no_telpon", no_telpon);
                            userMap.put("profile_image", "https://firebasestorage.googleapis.com/v0/b/resepmami-2fe69.appspot.com/o/tourist.png?alt=media&token=3310e472-d159-48a5-86a2-19cd3c61d315");
                            databaseReference.setValue(userMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            registerView.registerSuccess();
                                        }
                                    });
                        } catch (Exception fe) {
                            fe.printStackTrace();
                        }
                    } else if (!task.isSuccessful()) {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            registerView.emailAlreadyExists();
                        }
                    } else {
                        registerView.registerFailed();
                    }
                }
            });
        }
    }
}
