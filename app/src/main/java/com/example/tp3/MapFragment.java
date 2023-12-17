package com.example.tp3;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {

    GoogleMap gMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment supp = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.MY_MAP);
        supp.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                gMap = googleMap; // Assign the GoogleMap to the member variable

                // Add default markers
                addDefaultMarkers();

                // Rest of your code for handling map clicks can go here
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        MarkerOptions mk = new MarkerOptions();
                        mk.position(latLng);
                        mk.title(latLng.latitude + " KG " + latLng.longitude);
                        gMap.clear();
                        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
                        gMap.addMarker(mk);
                    }
                });
            }
        });
        return view;
    }

    private void addDefaultMarkers() {
        LatLng mapEnsaFes = new LatLng(33.996679209345885, -4.991836426998822);
        LatLng mapEnsaAgadir = new LatLng(30.406085945162136, -9.529883239731834);

        LatLng mapEnsaMarrakech = new LatLng(31.65990974101816, -8.023517370518274);
        LatLng mapEnsaOujda = new LatLng(34.966120174475805, -1.9183119445915966);
        LatLng mapEnsaTetouan = new LatLng(35.56231630546612, -5.364557524611684);
        LatLng mapEnsaElHoceima = new LatLng(35.211989518006945, -3.8538081153190893);
        LatLng mapEnsaKhouribga = new LatLng(32.89723873261541, -6.9136887938067435);
        LatLng mapEnsaKenitra = new LatLng(34.26562448457443, -6.5777415972578055);

        gMap.addMarker(new MarkerOptions().position(mapEnsaFes).title("ENSA FEZ"));
        gMap.addMarker(new MarkerOptions().position(mapEnsaAgadir).title("ENSA Agadir"));
        gMap.addMarker(new MarkerOptions().position(mapEnsaMarrakech).title("ENSA Marrakech"));
        gMap.addMarker(new MarkerOptions().position(mapEnsaOujda).title("ENSA Oujda"));
        gMap.addMarker(new MarkerOptions().position(mapEnsaTetouan).title("ENSA Tetouan"));
        gMap.addMarker(new MarkerOptions().position(mapEnsaElHoceima).title("ENSA El houceima"));
        gMap.addMarker(new MarkerOptions().position(mapEnsaKhouribga).title("ENSA Khouribga"));
        gMap.addMarker(new MarkerOptions().position(mapEnsaKenitra).title("ENSA Kenitra"));

        // Move the camera to the first marker (ENSA FEZ)
        gMap.moveCamera(CameraUpdateFactory.newLatLng(mapEnsaFes));
    }
}
