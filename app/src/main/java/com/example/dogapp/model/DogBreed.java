package com.example.dogapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DogBreed implements Serializable {
    @SerializedName("bred_for")
    private String bred_for;

    @SerializedName("breed_group")
    private String breed_group;

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("life_span")
    private String lifeSpan;

    @SerializedName("origin")
    private String origin;

    @SerializedName("url")
    private String url;

    @SerializedName("temperament")
    private String temperament;

    private Boolean showCard = false;

    public DogBreed(String bred_for, String breed_group, int id, String name, String lifeSpan, String origin, String url, String temperament) {
        this.bred_for = bred_for;
        this.breed_group = breed_group;
        this.id = id;
        this.name = name;
        this.lifeSpan = lifeSpan;
        this.origin = origin;
        this.url = url;
        this.temperament = temperament;
    }

    public DogBreed(DogBreed item) {
        this.bred_for = item.bred_for;
        this.breed_group = item.breed_group;
        this.id = item.id;
        this.name = item.name;
        this.lifeSpan = item.lifeSpan;
        this.origin = item.origin;
        this.url = item.url;
        this.temperament = item.temperament;
    }

    public String getBred_for() {
        return bred_for;
    }

    public void setBred_for(String bred_for) {
        this.bred_for = bred_for;
    }

    public String getBreed_group() {
        return breed_group;
    }

    public void setBreed_group(String breed_group) {
        this.breed_group = breed_group;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(String lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTemperament() {
        return temperament;
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    public Boolean getShowCard() {
        return showCard;
    }

    public void setShowCard(Boolean showCard) {
        this.showCard = showCard;
    }
}
