package com.google.shopcatalog;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Sergey on 02.03.2016.
 */
public class Utils {

    public static final String TAG      = Utils.class.getSimpleName();

    private static final String XML_CATEGORIES  = "categories";
    private static final String XML_CATEGORY    = "category";
    private static final String XML_OFFERS      = "offers";
    private static final String XML_OFFER       = "offer";

    private static final String XML_NAME        = "name";
    private static final String XML_PRICE       = "price";
    private static final String XML_DESCRIPTION = "description";
    private static final String XML_PICTURE     = "picture";
    private static final String XML_CATEGORY_ID = "categoryId";
    private static final String XML_PARAM       = "param";
    private static final String XML_WEIGHT      = "Вес";

    public static ArrayList<ModelCategoryOffers> parseItems(ArrayList<ModelCategoryOffers> items,
                                                      XmlPullParser parser)
            throws XmlPullParserException, IOException {
        int eventType = parser.next();
        Log.d(TAG, "Parse items is started");
        try {
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (XML_CATEGORIES.equals(parser.getName())) {

                        // Parsing categories of items

                        int id = 0;
                        String name = "";
                        while (!(eventType == XmlPullParser.END_TAG
                                && XML_CATEGORIES.equals(parser.getName()))) {
                            switch (eventType) {
                                case XmlPullParser.START_TAG:
                                    if (XML_CATEGORY.equals(parser.getName())) {
                                        id = Integer.parseInt(parser
                                                .getAttributeValue(null, "id"), 10);
                                    }
                                    break;
                                case XmlPullParser.TEXT:
                                    name = parser.getText();
                                    break;
                                case XmlPullParser.END_TAG:
                                    if (XML_CATEGORY.equals(parser.getName())) {
                                        Log.d(TAG, "ID: " + id + " Name: " + name);
                                        items.add(new ModelCategoryOffers(id, name));
                                    }
                                    break;
                                default:
                                    break;
                            }
                            eventType = parser.next();
                        }

                    }
                    if (parser.getName().equals(XML_OFFERS)) {

                        // Parsing each of items

                        while (!(eventType == XmlPullParser.END_TAG
                                && XML_OFFERS.equals(parser.getName()))) {
                            if (eventType == XmlPullParser.START_TAG
                                    && XML_OFFER.equals(parser.getName())) {
                                long id = Long.parseLong(parser.getAttributeValue(null, "id"), 10);
                                ModelOffer offer = new ModelOffer(id);
                                eventType = parser.next();
                                while (!(eventType == XmlPullParser.END_TAG
                                        && XML_OFFER.equals(parser.getName()))) {
                                    if (eventType == XmlPullParser.START_TAG) {
                                        switch (parser.getName()) {
                                            case XML_NAME:
                                                parser.next();
                                                offer.setName(parser.getText());
                                                break;
                                            case XML_PRICE:
                                                parser.next();
                                                offer.setPrice(parser.getText());
                                                break;
                                            case XML_DESCRIPTION:
                                                parser.next();
                                                offer.setDescription(parser.getText());
                                                break;
                                            case XML_PICTURE:
                                                parser.next();
                                                offer.setUrlPhoto(parser.getText());
                                                break;
                                            case XML_CATEGORY_ID:
                                                parser.next();
                                                int categoryId = Integer.parseInt(parser.getText());
                                                offer.setCategoryId(categoryId);
                                                break;
                                            case XML_PARAM:
                                                String value = parser
                                                        .getAttributeValue(null, XML_NAME);
                                                if (XML_WEIGHT.equals(value)) {
                                                    parser.next();
                                                    offer.setWeight(parser.getText());
                                                }
                                                break;
                                            default:
                                                break;
                                        }

                                    }
                                    eventType = parser.next();
                                }
                                Log.d(TAG, offer.toString());
                                for (ModelCategoryOffers categoryItems : items) {
                                    if (categoryItems.getId() == offer.getCategoryId()) {
                                        categoryItems.addOffer(offer);
                                    }
                                }

                            } else {
                                eventType = parser.next();
                            }
                        }
                    }
                }
                eventType = parser.next();
            }
            return(items);
        }
        catch (Exception e){
            return null;
        }
    }

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
