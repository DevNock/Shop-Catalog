package com.google.shopcatalog.fragment;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.shopcatalog.FarforFetchr;
import com.google.shopcatalog.MainActivity;
import com.google.shopcatalog.model.ModelCategoryOffers;
import com.google.shopcatalog.R;
import com.google.shopcatalog.adapter.RVCategoryAdapter;
import com.google.shopcatalog.RecyclerItemClickListener;
import com.google.shopcatalog.Utils;

import java.util.ArrayList;

/**
 * Created by Sergey on 01.03.2016.
 */
public class FragmentCategoriesList extends Fragment {

    private static final String TAG = FragmentCategoriesList.class.getSimpleName();

    private RecyclerView rv;
    private AppCompatActivity mainActivity;

    private LinearLayout progressBar;
    private RelativeLayout rl;

    ArrayList<ModelCategoryOffers> mCategories;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);

        progressBar = (LinearLayout) view.findViewById(R.id.category_list_progressbar_container);
        rl = (RelativeLayout) view.findViewById(R.id.category_list_recyclerview_container);

        getActivity().setTitle(getString(R.string.catalog));

        rv = (RecyclerView)view.findViewById(R.id.rvcategories);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        mainActivity = (AppCompatActivity)getActivity();

        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Bundle bundle = new Bundle();
                        ModelCategoryOffers categoryItems = mCategories.get(position);
                        bundle.putParcelableArrayList(MainActivity.EXTRA_OFFERS_LIST,
                                categoryItems.getListOffersID());
                        bundle.putString(MainActivity.EXTRA_NAME_CATEGORY,
                                categoryItems.getName());
                        FragmentOffersList fragmentOffersList = new FragmentOffersList();
                        fragmentOffersList.setArguments(bundle);

                        mainActivity.getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frame_container, fragmentOffersList)
                                .addToBackStack(null)
                                .commit();
                    }
                })
        );

        if(mCategories == null) {
            if (!Utils.isOnline(getActivity())) {   // check internet connection
                startDialogAlert();
            }
            new FetchItemsTask().execute();         // start downloading xml
        } else{
            setupAdapter();
        }

        return view;
    }

    private void startDialogAlert() {
        new AlertDialog.Builder(getActivity())
                .setTitle(mainActivity.getString(R.string.error_title))
                .setMessage(mainActivity.getString(R.string.error_message))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void setupAdapter(){
        if(this == null || rv == null || mCategories == null) {
            new FetchItemsTask().execute();
        } else {
            progressBar.setVisibility(View.GONE);
            rl.setVisibility(View.VISIBLE);

            mCategories = Utils.fillImage(mCategories);

            RVCategoryAdapter adapter = new RVCategoryAdapter(mCategories);
            rv.setAdapter(adapter);
        }
    }

    private class FetchItemsTask extends AsyncTask<Void,Void,ArrayList<ModelCategoryOffers>> {
        @Override
        protected ArrayList<ModelCategoryOffers> doInBackground(Void... params) {
            return new FarforFetchr().fetchCategory();
        }

        @Override
        protected void onPostExecute(ArrayList<ModelCategoryOffers> categories) {
            mCategories = categories;
            setupAdapter();
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);        // start progressBar
            rl.setVisibility(View.GONE);
            super.onPreExecute();
        }
    }
}
