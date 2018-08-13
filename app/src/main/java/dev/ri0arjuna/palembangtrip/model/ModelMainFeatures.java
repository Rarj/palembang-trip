package dev.ri0arjuna.palembangtrip.model;

public class ModelMainFeatures {
    private String gambar, id, count;

    public ModelMainFeatures(String gambar, String id, String count) {
        this.gambar = gambar;
        this.id = id;
        this.count = count;
    }

    public ModelMainFeatures() {
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}