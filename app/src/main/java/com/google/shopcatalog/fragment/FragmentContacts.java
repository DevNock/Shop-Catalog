package com.google.shopcatalog.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
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
import com.google.shopcatalog.*;
import com.google.shopcatalog.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergey on 04.03.2016.
 */
public class FragmentContacts extends Fragment {

    private static final String TAG = FragmentContacts.class.getSimpleName();
    private boolean isPermisson_granted;
    private LatLng defaultPostion;

    private GoogleMap googleMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(com.google.shopcatalog.R.layout.fragment_contacts, container, false);
        isPermisson_granted = true;
        defaultPostion = new LatLng(53.904444, 27.587389);
        createMapView();
        addMarker();
        return view;
    }

    private void createMapView() {
        try {
            if (null == googleMap) {
                googleMap = ((SupportMapFragment) getChildFragmentManager()
                        .findFragmentById(R.id.mapView)).getMap();

                // if it's Android M check permission specially

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int hasLocationPermission = getActivity()
                            .checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
                    List<String> permissions = new ArrayList<String>();
                    if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
                        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
                    }

                    if (!permissions.isEmpty()) {
                        requestPermissions(permissions.toArray(new String[permissions.size()]),
                                MainActivity.REQUEST_CODE_SOME_FEATURES_PERMISSIONS);
                    } else {

                        // if all permission is granted show current position
                        addCurrentPosition();
                    }
                } else {
                    addCurrentPosition();
                }
                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */
                if (null == googleMap) {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Error creating map", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (NullPointerException exception) {
            Log.e(TAG, exception.toString());
        }
    }

    private void addCurrentPosition() {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(defaultPostion)
                .zoom(12)
                .build();
        // even we don't have permission
        // display default position

        if (isPermisson_granted) {

            final LocationManager locationManager = (LocationManager) getActivity()
                    .getSystemService(Context.LOCATION_SERVICE);
            final LocationListener locationListener = new LocationListener() {

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                @Override
                public void onProviderEnabled(String provider) {
                }

                @Override
                public void onProviderDisabled(String provider) {
                }

                @Override
                public void onLocationChanged(Location location) {
                }
            };

            // if Provide is enabled
            // get current location and move camera there
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                        0, 0, locationListener);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                googleMap.setMyLocationEnabled(true);
                cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))
                        .zoom(13)
                        .build();
            }
        }
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        googleMap.moveCamera(cameraUpdate);
    }

    private void addMarker() {

        if (null != googleMap) {

            googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(53.904444, 27.587389))
                            .title("1")
            );
            googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(53.872054, 27.567347))
                            .title("2")
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case MainActivity.REQUEST_CODE_SOME_FEATURES_PERMISSIONS: {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        isPermisson_granted = true;

                    } else {
                        isPermisson_granted = false;
                    }
                }
                addCurrentPosition();
            }
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
            if (fragment != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .remove(fragment)
                        .commit();
            }
        }
    }
}
