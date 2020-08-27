package com.motion.warmcloudweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.motion.warmcloudweather.base.BaseView;
import com.motion.warmcloudweather.location.ILocationContract;
import com.motion.warmcloudweather.location.LocationPresenter;

public class MainActivity extends AppCompatActivity implements ILocationContract.ILocationView {


    private ILocationContract.ILocationPresenter mLocationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLocationPresenter = new LocationPresenter(this.getApplicationContext());
        mLocationPresenter.setView(this);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0x001);
        } else {
            mLocationPresenter.startLocation();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mLocationPresenter.startLocation();
    }

    @Override
    public void setLocationInfo(String cityName) {
        Log.e("wanghaha", cityName);
        Toast.makeText(this, cityName, Toast.LENGTH_SHORT).show();
    }
}