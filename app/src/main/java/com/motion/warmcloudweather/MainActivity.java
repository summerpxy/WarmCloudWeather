package com.motion.warmcloudweather;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.motion.warmcloudweather.location.ILocationContract;
import com.motion.warmcloudweather.location.LocationPresenter;
import com.permissionx.guolindev.PermissionX;

import timber.log.Timber;


public class MainActivity extends AppCompatActivity implements ILocationContract.ILocationView {


    private ILocationContract.ILocationPresenter mLocationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLocationPresenter = new LocationPresenter(this.getApplicationContext());
        mLocationPresenter.setView(this);

        PermissionX.init(this)
                .permissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        mLocationPresenter.startLocation();
                    } else {

                    }
                });


    }

    @Override
    public void setLocationInfo(String cityName) {
        Timber.e(cityName);
        Toast.makeText(this, cityName, Toast.LENGTH_SHORT).show();
    }
}