package com.mapo.eco100.config

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class KaKaoInit : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this,"27c8e2d5025be3e8979d18947a200d88")
    }
}