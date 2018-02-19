package megha.google_map_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

/**
 * Created by Megha Chauhan on 08-Feb-18.
 */

public class GoogleMapFragment extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,OnMapReadyCallback
{
    private MapView mapView;
    private GoogleApiClient googleApiClient;
    GoogleMap map;
    LocationManager locationManager;
    Location location;
    FusedLocationProviderClient fusedLocationProviderClient;
    String provider;
    Criteria criteria;
    private final int[]  MAP_TYPES={GoogleMap.MAP_TYPE_HYBRID,GoogleMap.MAP_TYPE_NORMAL,GoogleMap.MAP_TYPE_SATELLITE,
                                    GoogleMap.MAP_TYPE_NONE,GoogleMap.MAP_TYPE_NONE};


    public static GoogleMapFragment newInstance(){
        return new GoogleMapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.map_fragment,viewGroup,false);

        mapView=view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        return view;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        googleApiClient = new GoogleApiClient.Builder( getActivity() )
                .addConnectionCallbacks( this )
                .addOnConnectionFailedListener( this )
                .addApi( LocationServices.API )
                .build();
                locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                criteria = new Criteria();
                provider = locationManager.getBestProvider(criteria, true);
                location = locationManager.getLastKnownLocation(provider);
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        task.isComplete();
                    }
                });
                initCamera();


    }

    @Override
    public void onStart(){
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    public void onStop(){
        super.onStop();
        if(googleApiClient!=null && googleApiClient.isConnected()){
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onResume(){
        mapView.onResume();
        super.onResume();
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @SuppressLint("MissingPermission")
    private void initCamera(){

        CameraPosition position=CameraPosition.builder()
                .target(new LatLng(1.00,2.00))
                .zoom(16f)
                .bearing(0.0f)
                .tilt(0.0f)
                .build();

            map.animateCamera(CameraUpdateFactory.newCameraPosition(position), null);
            map.setMapType(MAP_TYPES[1]);
            map.setTrafficEnabled(true);
            map.setMyLocationEnabled(true);
            map.getUiSettings().setZoomControlsEnabled(true);

    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
    }
}



