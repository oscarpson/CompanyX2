package joslabs.companyx.viewsperson;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import joslabs.companyx.R;

public class Viewsalesmap extends AppCompatActivity {

    MapView mapView;
    private GoogleMap googleMap;
    private final LatLng mDefaultLocation = new LatLng(-1.2921,36.8219);
    private static final int DEFAULT_ZOOM = 15;
    private  String flat,fStationName,fCost;
    private  String flong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewsalesmap);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        final Bundle extras=getIntent().getExtras();
        flat=extras.getString("lats");

      flong=extras.getString("longts");
        fStationName=extras.getString("username");
        //fCost=extras.getString("fCost");

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                googleMap.setTrafficEnabled(true);
                float lat=Float.valueOf(flat);
                float llong=Float.valueOf(flong);
                //   googleMap.setMyLocationEnabled(true);
                //flat=new LatLng(flat)
                LatLng fStation = new LatLng(lat,llong);


         //   LatLng x=LatLng.extras.getString("");
                googleMap.addMarker(new MarkerOptions().position(fStation).title(fStationName).icon(BitmapDescriptorFactory.fromResource(R.drawable.gasb)));


                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(fStation, DEFAULT_ZOOM));
                //googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                // CameraPosition cameraPosition=new CameraPosition.Builder().target(locations).zoom(15).build();
                // googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        });



    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

}
