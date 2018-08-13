package dev.ri0arjuna.palembangtrip.model;

public class ModelSubFeatures {
    private String nama, id;

    public ModelSubFeatures(String nama, String id) {
        this.nama = nama;
        this.id = id;
    }

    public ModelSubFeatures() {
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
