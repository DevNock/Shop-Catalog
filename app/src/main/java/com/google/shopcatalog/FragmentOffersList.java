package com.google.shopcatalog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by Sergey on 01.03.2016.
 */
public class FragmentOffersList extends Fragment {

    private RecyclerView rv;
    private ArrayList<ModelOffer> offers;

    private AppCompatActivity mainActivity;

    public FragmentOffersList() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offer_list, container, false);

        Bundle bundle = this.getArguments();
        offers = bundle.getParcelableArrayList(MainActivity.EXTRA_OFFERS_LIST);
        getActivity().setTitle(bundle.getString(MainActivity.EXTRA_NAME_CATEGORY));

        rv = (RecyclerView)view.findViewById(R.id.rvoffers);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        mainActivity = (AppCompatActivity)getActivity();

        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        ModelOffer offer = offers.get(position);

                        Bundle bundle = new Bundle();
                        bundle.putParcelable(MainActivity.EXTRA_OFFER, offer);

                        FragmentOffer fragmentOffer  = new FragmentOffer();
                        fragmentOffer.setArguments(bundle);
                        mainActivity.getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frame_container, fragmentOffer)
                                .addToBackStack(null)
                                .commit();
                    }
                })
        );
        setupAdapter();

        return view;
    }

    public void setupAdapter(){
        if(this == null || rv == null) {
            return;
        }
        if(offers != null){
            RVOfferAdapter adapter = new RVOfferAdapter(offers);
            rv.setAdapter(adapter);
        } else{
            rv.setAdapter(null);
        }

    }
}
