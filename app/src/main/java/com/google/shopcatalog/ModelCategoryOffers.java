package com.google.shopcatalog;

import java.util.ArrayList;

/**
 * Created by Sergey on 25.02.2016.
 */
public class ModelCategoryOffers {

    private int id;
    private String name;
    private int idPhoto;
    private ArrayList<ModelOffer> listOffers;


    public ModelCategoryOffers(int id, String name){
        this.id = id;
        this.name = name;
        listOffers = new ArrayList<>();
    }

    public ModelCategoryOffers(int id, String name, int idPhoto){
        this.id = id;
        this.name = name;
        this.idPhoto = idPhoto;
        listOffers = new ArrayList<>();
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

    public ArrayList<ModelOffer> getListOffersID() {
        return listOffers;
    }

    public void setListOffersID(ArrayList<ModelOffer> listOffers) {
        this.listOffers = listOffers;
    }

    public void addOffer(ModelOffer offer){
        listOffers.add(offer);
    }

    public int getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(int idPhoto) {
        this.idPhoto = idPhoto;
    }
}
