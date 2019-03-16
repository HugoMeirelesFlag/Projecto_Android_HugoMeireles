package com.example.projecto_android_hugomeireles.model;

import android.widget.ImageView;
import android.widget.TextView;

import io.realm.RealmObject;

public class Favorito extends RealmObject {
    private String id;
    private String ivImage;
    private String tvNome;
    private String tvData;
    private String tvBuy;

    public Favorito() {

    }

    public String getIvImage() {
        return ivImage;
    }

    public void setIvImage(String ivImage) {
        this.ivImage = ivImage;
    }

    public String getTvNome() {
        return tvNome;
    }

    public void setTvNome(String tvNome) {
        this.tvNome = tvNome;
    }

    public String getTvData() {
        return tvData;
    }

    public void setTvData(String tvData) {
        this.tvData = tvData;
    }

    public String getTvBuy() {
        return tvBuy;
    }

    public void setTvBuy(String tvBuy) {
        this.tvBuy = tvBuy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
