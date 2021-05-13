package com.mapo.eco100.config

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.mapo.eco100.R

class KaKaoInit : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, getString(R.string.kakao_app_key).substring(5))
    }
}