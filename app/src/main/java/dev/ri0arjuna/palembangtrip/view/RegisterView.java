package dev.ri0arjuna.palembangtrip.view;

public interface RegisterView {

    void emptyEmail();

    void badEmailPattern();

    void emptyNoTelp();

    void emptyPassword();

    void registerSuccess();

    void registerFailed();

    void emailAlreadyExists();

    void lengthMinimum();
}
