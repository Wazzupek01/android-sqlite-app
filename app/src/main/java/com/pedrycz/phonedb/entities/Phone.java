package com.pedrycz.phonedb.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "phones")
public class Phone {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;

    @ColumnInfo(name = "manufacturer")
    private String brand;

    @ColumnInfo(name = "model")
    private String model;

    @ColumnInfo(name = "android")
    private String android;

    @ColumnInfo(name = "url")
    private final String url;

    public Phone(@NonNull String brand, @NonNull String model, @NonNull String android, @NonNull String url) {
        this.brand = brand;
        this.model = model;
        this.android = android;
        this.url = url;
    }

//    @Ignore
//    public Phone( @NonNull Long id, @NonNull String brand, @NonNull String model, @NonNull String android, @NonNull String url) {
//        this.brand = brand;
//        this.model = model;
//        this.android = android;
//        this.url = url;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAndroid() {
        return android;
    }

    public void setAndroid(String android) {
        this.android = android;
    }

    public String getUrl() {
        return url;
    }
}
