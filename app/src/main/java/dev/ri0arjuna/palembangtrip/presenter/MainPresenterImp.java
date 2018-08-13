package dev.ri0arjuna.palembangtrip.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import dev.ri0arjuna.palembangtrip.model.ModelMainFeatures;
import dev.ri0arjuna.palembangtrip.view.MainView;

public class MainPresenterImp implements MainPresenter {

    private MainView mainView;

    public MainPresenterImp(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void koneksi(Context cek) {
        ConnectivityManager cm = (ConnectivityManager) cek.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            mainView.koneksiSuccess();
        } else {
            mainView.koneksigagal();
        }
    }

    @Override
    public void database(final String username, final String no_telpon, final String profile_image) {
       mainView.loadDatabase();
    }

    @Override
    public void mainFeatures(ModelMainFeatures mainFeatures) {
        mainView.loadMainFeatures();
    }
}
