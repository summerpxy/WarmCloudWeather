package com.motion.warmcloudweather.location;

import android.content.Context;
import android.text.TextUtils;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.motion.warmcloudweather.base.BaseView;


public class LocationPresenter implements ILocationContract.ILocationPresenter {

    private volatile LocationClient mLocationClient;
    private ILocationContract.ILocationView mLocationView;

    private void createClient(Context context) {
        if (mLocationClient == null) {
            synchronized (LocationPresenter.class) {
                if (mLocationClient == null) {
                    mLocationClient = new LocationClient(context);
                }
            }
        }
    }

    private final BDAbstractLocationListener LISTENER = new LocationListenerImpl();

    class LocationListenerImpl extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            String name = bdLocation.getCity();
            if (!TextUtils.isEmpty(name)) {
                mLocationView.setLocationInfo(name);
            }
        }
    }

    public LocationPresenter(Context context) {
        createClient(context);
        //声明LocationClient类实例并配置定位参数
        LocationClientOption locationOption = new LocationClientOption();

        //可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
        locationOption.setScanSpan(1000);
        //可选，设置是否需要地址信息，默认不需要
        locationOption.setIsNeedAddress(true);
        //可选，设置是否需要地址描述
        locationOption.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        locationOption.setLocationNotify(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        locationOption.setIgnoreKillProcess(true);
        //可选，默认false，设置是否开启Gps定位
        locationOption.setOpenGps(true);
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        mLocationClient.setLocOption(locationOption);
        mLocationClient.registerLocationListener(LISTENER);
    }

    @Override
    public void startLocation() {
        if (mLocationClient != null)
            mLocationClient.start();
    }

    @Override
    public void stopLocation() {
        if (mLocationClient != null)
            mLocationClient.stop();
    }

    @Override
    public void destory() {
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
            mLocationClient.unRegisterLocationListener(LISTENER);
            mLocationClient = null;
        }
    }


    @Override
    public void setView(BaseView view) {
        mLocationView = (ILocationContract.ILocationView) view;
    }
}

