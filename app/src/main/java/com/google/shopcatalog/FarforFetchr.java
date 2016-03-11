package com.google.shopcatalog;

import android.util.Log;

import com.google.shopcatalog.model.ModelCategoryOffers;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Sergey on 25.02.2016.
 */
public class FarforFetchr {

    public static final String URL_XML  = "http://ufa.farfor.ru/getyml/?key=ukAXxeJYZN";
    public static final String TAG      = FarforFetchr.class.getSimpleName();

    public static ArrayList<ModelCategoryOffers> fetchCategory() {
        ArrayList<ModelCategoryOffers> items = new ArrayList<>();

        try {
            String xmlString = getXml();
            items = XMLParser.parseItems(items, xmlString);
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to fetch items", ioe);   // if connection is failed try again
            return fetchCategory();
        }
        return items;
    }

    public static String getXml() throws IOException {
        URL url = new URL(URL_XML);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader bufferReader;
        try{
            bufferReader = new BufferedReader(new InputStreamReader(connection
                    .getInputStream(), "windows-1251"), 4096);
            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                return null;
            }
            String line;
            StringBuilder websiteContent = new StringBuilder();
            while((line = bufferReader.readLine()) != null){
                websiteContent.append(line);
            }
            return websiteContent.toString();

        }finally {
            connection.disconnect();
        }
    }





}
