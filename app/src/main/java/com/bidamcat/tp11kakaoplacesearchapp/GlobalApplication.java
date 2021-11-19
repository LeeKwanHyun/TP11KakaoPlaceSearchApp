package com.bidamcat.tp11kakaoplacesearchapp;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class GlobalApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //카카오 SDK 초기화설정
        KakaoSdk.init(this, "a1a1aef696a9ec349fcd7e881a56d232");
    }
}
