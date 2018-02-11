package megha.google_map_app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

/**
 * Created by Megha Chauhan on 10-Feb-18.
 */

public class ActivityUtil extends AppCompatActivity {

    public static void addFragmentToActivity(FragmentManager fragmentManager, Fragment fragment,int fragmentId,String tag){
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(fragmentId,fragment);
        fragmentTransaction.commit();
    }

}
