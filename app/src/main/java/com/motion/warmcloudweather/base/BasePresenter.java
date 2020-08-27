package com.motion.warmcloudweather.base;

public interface BasePresenter<V extends BaseView> {
    public void setView(V view);
}
