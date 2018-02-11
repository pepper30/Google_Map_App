package megha.google_map_app;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Megha Chauhan on 08-Feb-18.
 */

public class GoogleMapFragment extends SupportMapFragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener
{

    private GoogleApiClient googleApiClient;
    GoogleMap map;
    private final int[]  MAP_TYPES={GoogleMap.MAP_TYPE_HYBRID,GoogleMap.MAP_TYPE_NORMAL,GoogleMap.MAP_TYPE_SATELLITE,
                                    GoogleMap.MAP_TYPE_NONE,GoogleMap.MAP_TYPE_NONE};


    public static GoogleMapFragment newInstance(){
        return new GoogleMapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,Bundle savedInstanceState){

        return inflater.inflate(R.layout.map_fragment,viewGroup,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        googleApiClient = new GoogleApiClient.Builder( getActivity() )
                .addConnectionCallbacks( this )
                .addOnConnectionFailedListener( this )
                .addApi( LocationServices.API )
                .build();

        initListeners(map);
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
    @SuppressLint("MissingPermission")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Location location = LocationServices.getFusedLocationProviderClient(getActivity()).getLastLocation().getResult();
        initCamera(location);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker marker) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    private void initListeners( GoogleMap map) {

    }

    @SuppressLint("MissingPermission")
    private void initCamera(Location location){
        CameraPosition position=CameraPosition.builder()
                .target(new LatLng(location.getLatitude(),location.getLongitude()))
                .zoom(16f)
                .bearing(0.0f)
                .tilt(0.0f)
                .build();

        map.animateCamera(CameraUpdateFactory.newCameraPosition(position),null);
        map.setMapType(MAP_TYPES[1]);
        map.setTrafficEnabled(true);
        map.setMyLocationEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
    }



}



