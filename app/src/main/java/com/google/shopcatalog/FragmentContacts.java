package com.google.shopcatalog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
/**
 * Created by Sergey on 04.03.2016.
 */
public class FragmentContacts extends Fragment {

    private static final String TAG = FragmentContacts.class.getSimpleName();
    private GoogleMap googleMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        createMapView();
        addMarker();
        return view;
    }

    private void createMapView(){
        try {
            if(null == googleMap){
                googleMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView)).getMap();

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(53.904444, 27.587389))
                        .zoom(13)
                        .build();

                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                googleMap.moveCamera(cameraUpdate);

                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */
                if(null == googleMap) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Error creating map", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception){
            Log.e(TAG, exception.toString());
        }
    }

    private void addMarker(){

        if(null != googleMap){
            googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(53.904444, 27.587389))
                            .title("1")
            );
            googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(53.892054, 27.567347))
                            .title("2")
            );
        }
    }

    /**
     * Remove mapView fragment
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (googleMap != null) {
            Fragment fragment = (getFragmentManager().findFragmentById(R.id.mapView));
            if (fragment != null){
                getActivity().getSupportFragmentManager().beginTransaction()
                        .remove(fragment)
                        .commit();
            }
        }
    }
}
