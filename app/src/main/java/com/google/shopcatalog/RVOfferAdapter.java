package com.google.shopcatalog;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sergey on 28.02.2016.
 */
public class RVOfferAdapter extends RecyclerView.Adapter<RVOfferAdapter.OfferViewHolder>{

    ArrayList<ModelOffer> offers;
    RVOfferAdapter(ArrayList<ModelOffer> offers) {
        this.offers = offers;
    }

    @Override
    public OfferViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_offer, parent, false);
        OfferViewHolder ovh = new OfferViewHolder(v);
        return ovh;
    }

    @Override
    public void onBindViewHolder(OfferViewHolder holder, int position) {
        ModelOffer offer = offers.get(position);
        if(offer.getName() != null) {
            holder.offerName.setText(offer.getName());
        }
        if(offer.getWeight() != null) {
            holder.offerWeight.setText("Вес: " + offer.getWeight());    // may be try getResourses
        } else{
            holder.offerWeight.setVisibility(View.GONE);
        }
        if(offer.getUrlPhoto() != null) {
            Picasso.with(holder.offerPhoto.getContext())
                    .load(offer.getUrlPhoto())
                    .into(holder.offerPhoto);
        }
        if(offer.getPrice() != null) {
            holder.offerPrice.setText("Цена: " + offer.getPrice()  + " р");
        }
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class OfferViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView offerName;
        TextView offerWeight;
        TextView offerPrice;
        ImageView offerPhoto;


        OfferViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvoffer);
            offerName = (TextView)itemView.findViewById(R.id.offer_name);
            offerWeight = (TextView)itemView.findViewById(R.id.offer_weight);
            offerPrice = (TextView)itemView.findViewById(R.id.offer_price);
            offerPhoto = (ImageView)itemView.findViewById(R.id.offer_photo);
        }
    }
}
