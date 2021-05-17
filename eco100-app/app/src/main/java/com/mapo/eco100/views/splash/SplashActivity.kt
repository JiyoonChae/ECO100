package com.mapo.eco100.views.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.mapo.eco100.views.MainActivity


class SplashActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handler.postDelayed({

        }, 2000L)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}
