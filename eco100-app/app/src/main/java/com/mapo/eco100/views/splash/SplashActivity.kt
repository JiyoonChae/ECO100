package com.mapo.eco100.views.splash

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.mapo.eco100.R
import com.mapo.eco100.common.BaseActivity
import com.mapo.eco100.databinding.ActivitySplashBinding
import com.mapo.eco100.views.MainActivity

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val handler = Handler(Looper.getMainLooper())

    private val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("splash", "binding!")

        requirePermissions(permissions, 999)
        if (isNetworkAvailable()) {
            handler.postDelayed({

                val mListener = getSystemService(LOCATION_SERVICE) as LocationManager
                /*
                 * 현재 단말기의 위치설정 여부를 확인한다
                 */
                val isGPSLocation = mListener.isProviderEnabled(LocationManager.GPS_PROVIDER)
                val isNetworkLocation =
                    mListener.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            }, 2500L)
        } else {
            Toast.makeText(
                this,
                "네트워크가 연결되지 않아 종료합니다", Toast.LENGTH_SHORT
            ).show()
            finish()
        }

        Glide.with(this).asGif().load(R.raw.splash_eco100).into(binding.splashImg)
    }


    private fun startProcess() {
        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }

    override fun permissionGranted(requestCode: Int) {
        startProcess()
    }

    override fun permissionDenied(requestCode: Int) {
        Toast.makeText(this, "권한 승인이 필요합니다.", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onRestart() {
        super.onRestart()
        /**
         * 위치설정이 되어있지않아 사용자가 위치설정을
         * 강제한 후 Back Key 를 눌렀을때 실행되는 코드
         */
        val intent = Intent(this, MainActivity::class.java)
        finish()
        startActivity(intent)
    }

    private fun isNetworkAvailable(): Boolean {
        Log.d("splash", "isNetwork()")
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = cm.activeNetwork ?: return false
            val networkCapabilities = cm.getNetworkCapabilities(nw) ?: return false
            return when {
                //현재 단말기의 연결유무(Wifi, Data 통신)
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                //단말기가 아닐경우(ex:: IoT 장비등)
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //블루투스 인터넷 연결유뮤
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            return cm.activeNetworkInfo?.isConnected ?: false
        }
    }
}
