package megha.google_map_app;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{
    android.support.v4.app.FragmentManager fragmentManager;
    String FRAGMENT_TAG = "tag";
    private static final int PERMISSION_REQUEST = 0;
    boolean camPermission, locPermission;
    GoogleMapFragment mapFragment;
    GoogleMap gMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager=getSupportFragmentManager();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            setUpFragment();
        }
        else {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CAMERA},1);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permission, @NonNull int[] grantResult){
        super.onRequestPermissionsResult(requestCode,permission,grantResult);
        switch (requestCode){
            case PERMISSION_REQUEST:
                if(grantResult.length>0){
                     camPermission=grantResult[0]==PackageManager.PERMISSION_GRANTED;
                     locPermission=grantResult[1]==PackageManager.PERMISSION_GRANTED;
                }
                if(camPermission && locPermission){
                    setUpFragment();
                }
                else {
                  Toast.makeText(this,"permission denied",Toast.LENGTH_SHORT).show();
                }
        }

    }

    public void setUpFragment(){
         mapFragment=(GoogleMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        if(mapFragment==null){
            mapFragment= GoogleMapFragment.newInstance();
        }
        mapFragment.getMapAsync(this);
        ActivityUtil.addFragmentToActivity(fragmentManager,mapFragment,R.id.map,FRAGMENT_TAG);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap=googleMap;
    }
}
