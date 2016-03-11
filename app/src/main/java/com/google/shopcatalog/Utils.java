package com.google.shopcatalog;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.shopcatalog.model.ModelCategoryOffers;
import com.google.shopcatalog.model.ModelOffer;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Sergey on 02.03.2016.
 */
public class Utils {

    public static final String TAG      = Utils.class.getSimpleName();

    /**
     * Add to each of the categories photo
     */
    public static ArrayList<ModelCategoryOffers> fillImage(ArrayList<ModelCategoryOffers> mCategories){
        for(ModelCategoryOffers category : mCategories) {
            switch (category.getId()){
                case 1:
                    category.setIdPhoto(R.drawable.cat1);
                    break;
                case 2:
                    category.setIdPhoto(R.drawable.cat2);
                    break;
                case 3:
                    category.setIdPhoto(R.drawable.cat3);
                    break;
                case 5:
                    category.setIdPhoto(R.drawable.cat5);
                    break;
                case 6:
                    category.setIdPhoto(R.drawable.cat6);
                    break;
                case 7:
                    category.setIdPhoto(R.drawable.cat7);
                    break;
                case 8:
                    category.setIdPhoto(R.drawable.cat8);
                    break;
                case 9:
                    category.setIdPhoto(R.drawable.cat9);
                    break;
                case 10:
                    category.setIdPhoto(R.drawable.cat10);
                    break;
                case 18:
                    category.setIdPhoto(R.drawable.cat18);
                    break;
                case 20:
                    category.setIdPhoto(R.drawable.cat20);
                    break;
                case 23:
                    category.setIdPhoto(R.drawable.cat23);
                    break;
                case 24:
                    category.setIdPhoto(R.drawable.cat24);
                    break;
                case 25:
                    category.setIdPhoto(R.drawable.cat25);
                    break;
            }
        }
        return mCategories;
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
