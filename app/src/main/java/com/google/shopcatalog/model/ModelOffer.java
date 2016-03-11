package com.google.shopcatalog.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sergey on 25.02.2016.
 */
public class ModelOffer implements Parcelable {

    private long   id;
    private int   categoryId;
    private String name;
    private String urlPhoto;
    private String weight;
    private String price;
    private String description;


    public ModelOffer(long id){
        this.id = id;
    }

    protected ModelOffer(Parcel in) {
        id = in.readLong();
        categoryId = in.readInt();
        name = in.readString();
        urlPhoto = in.readString();
        weight = in.readString();
        price = in.readString();
        description = in.readString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String toString(){
        return "ID: " + id + " name: " + name
                + " urlPhoto: " + urlPhoto + " Weight: " + weight
                + " Price: " + price + " Description: " + description
                + " CategoryID: " + categoryId;
    }

    // parcelable methods

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(id);
        dest.writeInt(categoryId);
        dest.writeString(name);
        dest.writeString(urlPhoto);
        dest.writeString(weight);
        dest.writeString(price);
        dest.writeString(description);

    }

    // define data handling class

    public static Creator<ModelOffer> CREATOR = new Creator<ModelOffer>() {

        @Override
        public ModelOffer createFromParcel(Parcel source) {
            return new ModelOffer(source);
        }

        @Override
        public ModelOffer[] newArray(int size) {
            return new ModelOffer[size];
        }

    };
}
