package com.example.ushtinfeld.battleship_uriel;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ushtinfeld.battleship_uriel.data.DataHandler;
import com.example.ushtinfeld.battleship_uriel.data.Record;
import com.example.ushtinfeld.battleship_uriel.data.ScoreTable;

import entities.MapListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ScoresMapFragment extends Fragment implements OnMapReadyCallback {
    private final double UNKNOWN_LONG = 33.356765;
    private final double UNKNOWN_LAT = 32.487563;
    private GoogleMap myMap;

    private MarkerOptions userMarker;
    private MapListener listener;
    DataHandler dh = new DataHandler();

    public ScoresMapFragment() {
        // Required empty public constructor

    }

    public void setListener(MapListener listener) {
        this.listener = listener;
    }

    public static ScoresMapFragment newInstance() {
        ScoresMapFragment fragment = new ScoresMapFragment();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scores_map, container, false);
        SupportMapFragment supportmapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_scores_map_layout);
        supportmapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
        ScoreTable st;
        try {
            st = dh.getData(getContext());
        } catch (Exception e) {
            st = new ScoreTable();
        }
        ArrayList<Record> scores = st.getScoreTable();
        for (Record r : scores) {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses;
            double latitude;
            double longitude;
            try {
                addresses = geocoder.getFromLocation(r.getLatitude(), r.getLongitude(), 2);
                if (addresses.size() > 0) {
                    latitude = addresses.get(0).getLatitude();
                    longitude = addresses.get(0).getLongitude();
                    myMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))).setTitle(addresses.get(0).getAddressLine(0));
                } else {
                }
            } catch (IOException e) {
                myMap.addMarker(new MarkerOptions().position(new LatLng(UNKNOWN_LAT, UNKNOWN_LONG))).setTitle("Failed to get location");
            }
        }
        listener.mapReadyNotification();
    }

    public void markUserLocation(LatLng latLng) {
        userMarker = new MarkerOptions().position(latLng).title("Current Location");
        myMap.addMarker(userMarker);
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
    }

}
