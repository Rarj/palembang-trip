package dev.ri0arjuna.palembangtrip.presenter;

import android.content.Context;

import dev.ri0arjuna.palembangtrip.model.ModelMainFeatures;

public interface MainPresenter {
    void koneksi(Context cek);
    void database(String username, String profile_image, String no_telpon);
    void mainFeatures(ModelMainFeatures mainFeatures);
}
