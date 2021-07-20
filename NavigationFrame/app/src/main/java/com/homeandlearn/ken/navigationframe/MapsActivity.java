package com.homeandlearn.ken.navigationframe;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.OnConnectionFailedListener,NavigationView.OnNavigationItemSelectedListener
{
    private Button normButton;
    private GoogleMap mMap;
    private GPSTracker gpstracker;
    ArrayList<LatLng> mMarkerPoints;
    private Location mLocation;
    private LatLng origin;
    private Polyline line;
    private LocationRequest mLocationRequest;
    private List<Address> list;
    int PROXIMITY_RADIUS = 10000;
    double end_latitude, end_longitude;
    private TextView display_location;
    private Marker mp;
    private double latitude;
    private double longitude;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private AutoCompleteTextView mSearchtext;
    private PlaceAutocompleteAdapter mplaceAuto;
    private GoogleApiClient mGoogleApiClient;
    private LatLng dest;
    private String TAG;
    private Button searchb;
    private Button reset;
    private Polyline p;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(new LatLng(-40, -168), new LatLng(71, 136));
    private Object url;
    private int newPoint;
    ArrayList<LatLng> points;
    private Button direction;
    private LatLng p2;
    private Button b1,b2,b3;
    private DrawerLayout drawerLayout;
    private FirebaseAuth firebaseauth;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        b1=(Button)findViewById(R.id.bt_zin);
        b2=(Button)findViewById(R.id.bt_zmout);
        b3=(Button)findViewById(R.id.map_type);
        normButton=(Button)findViewById(R.id.norm_type);
        points = new ArrayList<LatLng>();
        reset = (Button) findViewById(R.id.reset_id);
        mSearchtext = (AutoCompleteTextView) findViewById(R.id.text_search);
        gpstracker = new GPSTracker(getApplicationContext());
        mLocation = gpstracker.getLocation();
        searchb = (Button) findViewById(R.id.search_button);
        direction=(Button)findViewById(R.id.direct);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        searchb.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {

                EditText et = (EditText) findViewById(R.id.text_search);
                LatLng p2;
                String location = et.getText().toString();
                Geocoder geocoder = new Geocoder(MapsActivity.this);
                try {
                    list = geocoder.getFromLocationName(location, 1);
                } catch (IOException e) {

                }
                if (list != null && list.size() != 0) {
                    Address add = list.get(0);
                    String locality = add.getLocality();
                    LatLng ll = new LatLng(add.getLatitude(), add.getLongitude());
                    CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 15);
                    mMap.moveCamera(update);
                    if (mp != null)
                        mp.remove();
                    MarkerOptions markerOptions = new MarkerOptions()
                            .title(locality)
                            .position(new LatLng(add.getLatitude(), add.getLongitude()))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green) );
                    PolylineOptions polylineoptions=new PolylineOptions();
                    polylineoptions.add(ll);
                    polylineoptions.color(Color.RED);
                    polylineoptions.width(15);
                    mp = mMap.addMarker(markerOptions);
                    mMap.addPolyline(polylineoptions);
                    checkLocationPermission();
                }

            }
        });
        reset.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                mSearchtext.setText("");
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           mMap.animateCamera(CameraUpdateFactory.zoomIn());
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           mMap.animateCamera(CameraUpdateFactory.zoomOut());
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           mMap.setMapType(MAP_TYPE_SATELLITE);
            }
        });
        normButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.setMapType(MAP_TYPE_NORMAL);
            }
        });
    }

    @SuppressLint("NewApi")
    private void init() {
        Log.d(TAG, "init:initializing");
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, (GoogleApiClient.OnConnectionFailedListener) this)
                .build();
        mplaceAuto = new PlaceAutocompleteAdapter(this, mGoogleApiClient, LAT_LNG_BOUNDS, null);
        mSearchtext.setAdapter(mplaceAuto);
        mSearchtext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || keyEvent.getAction() == keyEvent.ACTION_DOWN || keyEvent.getAction() == keyEvent.KEYCODE_ENTER) {
                    geoLocate();
                }
                return false;
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void geoLocate() {
        Log.d(TAG, "geoLocate:geoLocating");
        String searchstring = mSearchtext.getText().toString();
        Geocoder geocoder = new Geocoder(this);
        List<Address> List = new ArrayList<>();

        try {
            List = geocoder.getFromLocationName(searchstring, 1);
        } catch (IOException e) {
            Log.e(TAG, "geoLocate:IOException:" + e.getMessage());
        }
        if (List.size() > 0) {
            Address address = List.get(0);
            Log.d(TAG, "address:addresses");
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if(mLocation!=null )
        {
            LatLng sydney = new LatLng(mLocation.getLatitude(),mLocation.getLongitude());
            mMap.addMarker(new MarkerOptions().position(sydney).title("You're here").icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue) ));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            mMap.setMyLocationEnabled(true);
            PolylineOptions polylineoptions=new PolylineOptions();
            polylineoptions.color(Color.RED);
            polylineoptions.width(15);
            polylineoptions.add(sydney);
            mMap.addPolyline(polylineoptions);
        }

        init();
        checkLocationPermission();
    }
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent i=new Intent(MapsActivity.this,MainActivity.class );
            startActivity(i);
        } else if(id==R.id.nav_search)
        {
            Intent i=new Intent(MapsActivity.this, MapsActivity.class);
            startActivity(i);
        }
        else if (id == R.id.nav_help) {
            Intent i=new Intent(MapsActivity.this,Help.class );
            startActivity(i);
        } else if (id == R.id.nav_db) {
            Intent i=new Intent(MapsActivity.this,Donate.class );
            startActivity(i);
        } else if (id == R.id.nav_Rb) {
            Intent i=new Intent(MapsActivity.this,RequestActivity.class );
            startActivity(i);
        }else if(id==R.id.nav_about){
            Intent i=new Intent(MapsActivity.this,About.class);
            startActivity(i);
        }else if(id==R.id.nav_rb){
            Intent i=new Intent(MapsActivity.this,Request2.class);
            startActivity(i);
        }else if(id==R.id.nav_share){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String ShareBody="Your Body Here";
            String Sharesub="Your Subject Here";
            intent.putExtra(Intent.EXTRA_SUBJECT,Sharesub);
            intent.putExtra(Intent.EXTRA_TEXT,ShareBody);
            startActivity(Intent.createChooser(intent,"Share Using"));
            Toast.makeText(this, "Share Application", Toast.LENGTH_SHORT).show();
        }else if(id==R.id.nav_feed){
            Intent intent=new Intent(Intent.ACTION_SEND);
            String[] to={"Enter G-mail Here"};
            intent.putExtra(Intent.EXTRA_EMAIL,to);
            intent.putExtra(Intent.EXTRA_SUBJECT,"Feedback Of Application");
            intent.putExtra(Intent.EXTRA_TEXT,getString(R.string.mail));
            intent.setType("nessage/rfc822");
            startActivity(Intent.createChooser(intent,"Send Email"));
        }
        DrawerLayout drawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}