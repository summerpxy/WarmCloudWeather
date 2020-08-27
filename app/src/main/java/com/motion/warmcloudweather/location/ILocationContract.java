package com.motion.warmcloudweather.location;

import com.motion.warmcloudweather.base.BasePresenter;
import com.motion.warmcloudweather.base.BaseView;

public interface ILocationContract {

    public interface ILocationView extends BaseView {
        void setLocationInfo(String cityName);
    }

    public interface ILocationPresenter extends BasePresenter {

        void startLocation();

        void stopLocation();

        void destory();

    }


}
