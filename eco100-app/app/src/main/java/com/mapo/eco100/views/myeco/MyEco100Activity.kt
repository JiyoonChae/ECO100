package com.mapo.eco100.views.myeco

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.mapo.eco100.R

class MyEco100Activity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_eco100)

        // Enables Always-on
        setAmbientEnabled()
    }
}