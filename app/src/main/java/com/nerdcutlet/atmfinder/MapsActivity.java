package com.nerdcutlet.atmfinder;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.Manifest;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.nerdcutlet.atmfinder.network.AsyncHttpTask;
import com.nerdcutlet.atmfinder.network.AsyncResponse;
import com.nerdcutlet.atmfinder.network.MapData;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMyLocationButtonClickListener,
        View.OnClickListener,
        com.google.android.gms.location.LocationListener,
        AsyncResponse {

    private static final String LOG_TAG = "MapsActivity";


    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;


    GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    private LocationManager mLocationManager;

    boolean succesfulLocationData = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        /*
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        enableMyLocation();


    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {


        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(15000);
        mLocationRequest.setFastestInterval(15000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        Log.d(LOG_TAG, " Point : " + latLng);


    }





    //Location Listeners


    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;


        Log.d(LOG_TAG, "LAT : " + location.getLatitude() + " " + "LONG : " + location.getLongitude());
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());


        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        mMap.animateCamera(yourLocation);



        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);  //https://stackoverflow.com/questions/28436317/remove-location-updates-issue-location-class-separate-from-where-it-is-used-ca

        /*
        https://stackoverflow.com/questions/14441653/how-can-i-let-google-maps-api-v2-go-directly-to-my-location
        https://stackoverflow.com/questions/14074129/google-maps-v2-set-both-my-location-and-zoom-in
        https://stackoverflow.com/questions/13756758/how-to-directly-move-camera-to-current-location-in-google-maps-android-api-v2
        https://stackoverflow.com/questions/12458070/locationmanager-removeupdateslistener-not-removing-location-listener

        IMP : https://stackoverflow.com/questions/28097809/cant-remove-updates-from-fusedlocationapi?rq=1

         */
        if (!succesfulLocationData) {
            //If false, ie first time call Function
            GetDataFromAPI(location.getLatitude(), location.getLongitude());
        } else {
            Log.d(LOG_TAG, "Already called!");

        }


    }


    public void GetDataFromAPI(double latitude, double longitude) {
        succesfulLocationData = true;

        AsyncHttpTask task = new AsyncHttpTask();
        //this to set delegate/listener back to this class
        task.delegate = this;
        Object[] toPass = new Object[5];
        toPass[0] = mMap;
        toPass[1] = latitude;
        toPass[2] = longitude;
        toPass[3] = "3000";
        toPass[4] = "";
        task.execute(toPass);

    }

    @Override
    public void processFinish(String nextpage, double lat, double lon, String rad) {
//        if (nextpage == null || nextpage == "") {
//            //Dont call again
//        }else{
//            Log.d(LOG_TAG, "processFinish !");
//
//            final AsyncHttpTask task = new AsyncHttpTask();
//            //this to set delegate/listener back to this class
//            task.delegate = this;
//            final Object[] toPass = new Object[5];
//            toPass[0] = mMap;
//            toPass[1] = lat;
//            toPass[2] = lon;
//            toPass[3] = rad;
//            toPass[4] = nextpage;
//
//            // Execute some code after 5 seconds have passed
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    task.execute(toPass);                }
//            }, 5000);
//
//
//
//
//        }
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        MapData mapData = (MapData) marker.getTag();

        Intent intent = new Intent(getApplicationContext(), AtmDetail.class);

        intent.putExtra("EXTRA_ATM_NAME", mapData.getName());
        intent.putExtra("EXTRA_ATM_VICINITY",mapData.getVicinity() );
        intent.putExtra("EXTRA_ATM_PLACEID",mapData.getPlace_id() );

        startActivity(intent);

    }
}
