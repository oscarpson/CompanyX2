package joslabs.companyx;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import static joslabs.companyx.R.id.map;

public class RegisterActivity extends AppCompatActivity  implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener, OnMapReadyCallback {

public static final String TAG = MapsActivity.class.getSimpleName();

private GoogleApiClient googleApiClient;

private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
private boolean mLocationPermissionGranted;
private Location mLastKnownLocation;
private CameraPosition mCameraPosition;
    StorageReference storageRef;
    ImageView imgquiz;
    Bitmap bitmap;
    private static final int SELECT_PICTURE = 100;
    private View mProgressView;
    TextView txtprogress;
private static final String KEY_CAMERA_POSITION = "camera_position";
    FloatingActionButton fab;
private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
private final LatLng mDefaultLocation = new LatLng(-1.2921, 36.8219);
private static final int DEFAULT_ZOOM = 15;
private GoogleMap mMap; // Might be null if Google Play services APK is not available.

        GoogleApiClient mGoogleApiClient;
private LocationRequest mLocationRequest;
private GoogleMap googleMap;
        MapView mapView;
        String lats,longts;
Button btnreg,btngetlocation;
    EditText username,lname,phone,location;
String userKey,photourl;
    SharedPreferences pref;
    DatabaseReference dbref;
    ImageView profilepic,imgadd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pref=getApplicationContext().getSharedPreferences("regd",0);
        imgquiz= (ImageView) findViewById(R.id.imgsales);
        imgadd= (ImageView) findViewById(R.id.addimg);
        Firebase.setAndroidContext(this);
        dbref= FirebaseDatabase.getInstance().getReference();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        storageRef = storage.getReferenceFromUrl("gs://brucecompany-dca0f.appspot.com/");
        mapView = (MapView) findViewById(map);
        username= (EditText) findViewById(R.id.edtfname);
        phone= (EditText) findViewById(R.id.edtphone);
        lname= (EditText) findViewById(R.id.edtfsecondname);
        location= (EditText) findViewById(R.id.edtlocation);
        Bundle extras=getIntent().getExtras();


      // String latsl= extras.getString("lats");
      /* if (extras.getString("lats")!=null)
       {
           location.setText(extras.getString("lats"));
       }*/
      imgadd.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent i = new Intent();
              i.setType("image/*");
              i.setAction(Intent.ACTION_GET_CONTENT);
              startActivityForResult(Intent.createChooser(i, "Select Picture"),SELECT_PICTURE );
          }
      });
        btngetlocation= (Button) findViewById(R.id.btngetlocation);
        btngetlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapView.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);

                //Intent intent=new Intent(getApplicationContext(),mapsActivity.class);
                //startActivity(intent);
            }
        });
    fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapView.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

            }
        });
        btnreg= (Button) findViewById(R.id.btnreg);
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisterActivity.this, "created", Toast.LENGTH_SHORT).show();
                if (validated()) {
                   final String fname= username.getText().toString();
                    final String lnames= lname.getText().toString();
                    final String phones= phone.getText().toString();
                    String locations=location.getText().toString();
                    Log.e("dataupx",fname+"\n"+lnames+phones);
                    Random rnd = new Random();
                    final int randoms = 100000 + rnd.nextInt(900000);
                    StorageReference myfileRef = storageRef.child(randoms + "quizimg.jpg");
                    imgquiz.setDrawingCacheEnabled(true);
                    imgquiz.buildDrawingCache();
                    bitmap = imgquiz.getDrawingCache();

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] data = baos.toByteArray();
                    imgquiz.setImageDrawable(null);
                    // imgquiz.setBackground(null);
                    //  imgquiz.destroyDrawingCache();
                    UploadTask uploadTask = myfileRef.putBytes(data);

                    uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(RegisterActivity.this,  taskSnapshot.getBytesTransferred()+"", Toast.LENGTH_SHORT).show();

                        }
                    });
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(RegisterActivity.this, "TASK FAILED", Toast.LENGTH_SHORT).show();
                            Log.i("errorx", exception.getMessage());
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(RegisterActivity.this, "TASK SUCCEEDED", Toast.LENGTH_SHORT).show();
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            String DOWNLOAD_URL = downloadUrl.getPath();
                            Log.v("DOWNLOAD URL", DOWNLOAD_URL);
                            Log.d("Downx", DOWNLOAD_URL + "\n" + downloadUrl);
                            userKey = dbref.push().getKey();
                            photourl = downloadUrl.toString();
                            Toast.makeText(RegisterActivity.this, DOWNLOAD_URL, Toast.LENGTH_SHORT).show();
                            //userId = dbref.push().getKey();
                            Date today = Calendar.getInstance().getTime();
                            DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.UK);
                            final String reportDate = df.format(today);
                            // ChatsReal chatsReal = new ChatsReal(userKey, photourl, reportDate, sedtchat);
                            // dbref.child("chats").child(userId).setValue(chatsReal);


                            users adduser=new users(fname,lnames,phones,lats,longts,reportDate,photourl,userKey);
                            dbref.child("salesrep").child(userKey).setValue(adduser).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    SharedPreferences.Editor editor=pref.edit();
                                    editor.putString("username",username.getText().toString());
                                    editor.putString("phone",phone.getText().toString());
                                    editor.apply();
                                    dbref.child("userkeys").child(randoms+"").setValue(userKey);
                                    Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), NewSalesKey.class);
                                    intent.putExtra("salep",randoms+"");
                                    startActivity(intent);
                                }
                            });
                        }
                    });


                }
                else
                {
                    username.setError("You must enter username");
                    phone.setError("You must enter phone or usename");
                }
            }
        });


        pref = getApplicationContext().getSharedPreferences("location", 0);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mGoogleApiClient = new GoogleApiClient
                .Builder(getApplicationContext())
                //.enableAutoManage(getActivity(), 34992, this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        locationChecker(mGoogleApiClient, this);
        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds


        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                googleMap.setTrafficEnabled(true);
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {

                        googleMap.clear();
                        googleMap.addMarker(new MarkerOptions().position(latLng).title("Customer location").snippet("@joslas.co.ke").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_maphere)));
                        Toast.makeText(RegisterActivity.this, "here"+latLng, Toast.LENGTH_LONG).show();
                        lats=String.valueOf(latLng.latitude);
                        longts=String.valueOf(latLng.longitude);
                        Log.e("latx",lats);
                        location.setText("location set");

                    }
                });

                //updateLocationUI();

                // Get the current location of the device and set the position of the map.
                // getDeviceLocation();
                //   googleMap.setMyLocationEnabled(true);
                // editor.putString("lats",currentLatitude+"");
                // editor.putString("longs",currentLongitude+"");
                String lats, longs;
                lats = pref.getString("lats", null);
                longs = pref.getString("longs", null);
                if (lats != null) {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(Double.valueOf(lats),
                                    Double.valueOf(longs)), DEFAULT_ZOOM));
                    LatLng now = new LatLng(Double.valueOf(lats),
                            Double.valueOf(longs));
                    LatLng petrol = new LatLng(-1.2179869, 36.8902669);
                    // googleMap.addMarker(new MarkerOptions().position(petrol).title("Abundance Spares").snippet("Opposite Mwala Auto Spares +254711368518").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_maphere)));
                    googleMap.addMarker(new MarkerOptions().position(now).title("Previous position").snippet("@joslas.co.ke").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_maphere)));

                    //  Toast.makeText(getContext(), "now"+now, Toast.LENGTH_SHORT).show();
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(now, DEFAULT_ZOOM));


                } else {
                    // googleMap.addMarker(new MarkerOptions().position(mDefaultLocation).title("Marker jos").snippet("i will add stations"));


                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                }

                //googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                // CameraPosition cameraPosition=new CameraPosition.Builder().target(locations).zoom(15).build();
                // googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        });

    }

    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode==RESULT_OK){
            if(requestCode==SELECT_PICTURE){
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    Log.i("IMAGE PATH TAG", "Image Path : " + path);
                    // Set the image in ImageView
                    imgquiz.setImageURI(selectedImageUri);

                }
            }
        }
    }
    private String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private boolean validated() {
        if (username.length()<3||username.getText().toString()=="")
        {
            username.setError("must input correct data");
            return  false;
                      }
        if (lname.length()<3||lname.getText().toString()=="")
        {
            username.setError("must input correct data");
            return  false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mapView.getMapAsync(this);
            // mapView = (MapView) View.findViewById(R.id.map);
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.setTrafficEnabled(true);
        float x = 9;
        mMap.addMarker(new MarkerOptions().position(new LatLng(-1.2921, 36.8219)));


    }

    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
        //  Toast.makeText(getContext(), "this are"+location, Toast.LENGTH_SHORT).show();
        Log.d("newlocx2", "" + location);
        mMap.clear();
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("lats", currentLatitude + "");
        editor.putString("longs", currentLongitude + "");
        editor.commit();
        // Toast.makeText(getContext(), "latslots"+currentLatitude+"\n"+currentLongitude, Toast.LENGTH_SHORT).show();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        //  googleMap.addMarker(new MarkerOptions().position(latLng).title("Your current location").snippet("@joslas.co.ke").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_current)));

      /*  Circle circle = mMap.addCircle(new CircleOptions()
                .center(latLng)
                .radius(200)
                .fillColor(0x5500ff00)
                .strokeWidth(3).strokeColor(Color.BLACK));
        circle.setCenter(latLng);*/
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mLastKnownLocation = location;
        // mMap.addCircle(circle)

        //mMap.addMarker(new MarkerOptions().position(new LatLng(currentLatitude, currentLongitude)).title("Current Location"));
       /* MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("Your Locaion Check nearby stations");
        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));*/
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            mLastKnownLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);
        } else {
            handleNewLocation(location);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    /*
     * Google Play services can resolve some errors it detects.
     * If the error has a resolution, try sending an Intent to
     * start a Google Play services activity that can resolve
     * error.
     */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            /*
             * Thrown if Google Play services canceled the original
             * PendingIntent
             */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
        /*
         * If no resolution is available, display a dialog to the
         * user with the error.
         */
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setTrafficEnabled(true);
    }

    public void locationChecker(final GoogleApiClient mGoogleApiClient, final Activity activity) {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.

                        //  Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                        //  handleNewLocation(location);
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    activity, 1000);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }


    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }

    /*
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getParent(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        if (mLocationPermissionGranted) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);

        } else {
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mLastKnownLocation = null;
        }
    }

    private void getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getParent(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
        if (mLocationPermissionGranted) {
            mLastKnownLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);
          /*  LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String bestProvider = locationManager.getBestProvider(criteria, true);
            Location location = locationManager.getLastKnownLocation(bestProvider);
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));*/
            // Toast.makeText(this, "trying to get current location", Toast.LENGTH_SHORT).show();
        }

        // Set the map's camera position to the current location of the device.
        if (mCameraPosition != null) {
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));
            mMap.setMaxZoomPreference(15);
            mMap.addMarker(new MarkerOptions().title("you are here"));
        } else if (mLastKnownLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mLastKnownLocation.getLatitude(),
                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
        } else {
            Log.d(TAG, "Current location is null. Using defaults.");
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
        // A step later in the tutorial adds the code to get the device location.
    }
}
