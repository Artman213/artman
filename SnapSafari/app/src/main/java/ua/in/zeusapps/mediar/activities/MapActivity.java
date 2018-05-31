package ua.in.zeusapps.mediar.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import ua.in.zeusapps.mediar.R;
import ua.in.zeusapps.mediar.adapters.CustomInfoWindowAdapter;
import ua.in.zeusapps.mediar.adapters.RecyclerDistanceAdapter;
import ua.in.zeusapps.mediar.models.Event;

/**
 * Created by- slava on 12.01.18.
 */

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    final static String TAG = "MapActivity";

    private GoogleMap mMap;
    private List<Event> events;
    private Location location;
    private List<String> distances;
    private RecyclerDistanceAdapter mAdapter;
    private SupportMapFragment mapFragment;

    private CameraPosition myStartPosition;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        events = getIntent().getBundleExtra("data").getParcelableArrayList("events");
        if (events == null) {
            events = new ArrayList<>();
        }

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (lm != null) {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        initData();

        mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initRecycler();
    }



    private void initData() {
        if (location != null) {
            distances = updateDistanceList();
            myStartPosition = new CameraPosition.Builder().target(
                    new LatLng(location.getLatitude(), location.getLongitude()))
                    .zoom(18.0f)
                    .bearing(300)
                    .build();
        }
    }

    @Override
    protected void onDestroy() {
        mapFragment.getMapAsync(null);
        super.onDestroy();
    }

    private void initRecycler() {
        mAdapter = new RecyclerDistanceAdapter(distances, events, this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_animals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    private List<String> updateDistanceList() {
        List<String> distances = new ArrayList<>();
        for (Event event : events) {
            distances.add(String.format(
                                    getResources().getString(R.string.distance_model)
                                    , location.distanceTo(event.getArPoint().getLocation())));
        }

        return distances;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        boolean success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                        this, R.raw.style_json));

        if (!success) {
            Log.e(TAG, "Style parsing failed.");
        }else {
            Toast.makeText(this,"success",Toast.LENGTH_LONG).show();
        }
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(getLayoutInflater(), events, this));
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setMyLocationEnabled(true);
        if (myStartPosition != null) {
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(myStartPosition));
        }

        setMarkers();
    }

    public void setMarkers() {

        if (mMap == null || events == null) {
            return;
        }

        for (Event animal : events) {
            mMap.addMarker(new MarkerOptions()
                    .position(
                            new LatLng(
                                    animal.getArPoint().getCoordinate().getLatitude(),
                                    animal.getArPoint().getCoordinate().getLongitude())
                    )
                    .title(animal.getCard().getTitle()));
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        initData();
        mAdapter.updateList(distances);


        Log.e(TAG, "My location: " + location.toString());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
