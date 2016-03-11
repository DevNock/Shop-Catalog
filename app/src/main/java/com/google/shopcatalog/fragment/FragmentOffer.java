package com.google.shopcatalog.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.shopcatalog.MainActivity;
import com.google.shopcatalog.model.ModelOffer;
import com.google.shopcatalog.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Sergey on 01.03.2016.
 */
public class FragmentOffer extends Fragment {

    private ImageView photo;
    private TextView name;
    private TextView description;
    private TextView price;
    private TextView weight;



    public FragmentOffer() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offer, container, false);

        Bundle bundle = this.getArguments();
        ModelOffer offer = bundle.getParcelable(MainActivity.EXTRA_OFFER);

        photo = (ImageView) view.findViewById(R.id.offer_card_photo);
        name = (TextView) view.findViewById(R.id.offer_card_name);
        price = (TextView) view.findViewById(R.id.offer_card_price);
        weight = (TextView) view.findViewById(R.id.offer_card_weight);
        description = (TextView) view.findViewById(R.id.offer_card_description);

        getActivity().setTitle(offer.getName());

        if(offer.getUrlPhoto() != "" && offer.getUrlPhoto() != null) {
            Picasso.with(getContext()).load(offer.getUrlPhoto()).into(photo);
        }
        if(offer.getName() != null) {
            name.setText(offer.getName());
        }
        if(offer.getPrice() != null) {
            price.setText(getString(R.string.cost_description) + offer.getPrice() + " р");
        }
        if(offer.getWeight() != null) {
            weight.setText(getString(R.string.weight_description) + offer.getWeight());
        } else{
            weight.setVisibility(View.GONE);
        }
        if(offer.getDescription() != null) {
            description.setText(offer.getDescription());
        }



        return view;
    }

}
