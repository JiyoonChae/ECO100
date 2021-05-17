package com.mapo.eco100.views.splash

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
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.mapo.eco100.common.CurrentLocationActivity
import com.mapo.eco100.common.LocationSettingBox
import com.mapo.eco100.config.AppInit


class SplashActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isNetworkAvailable()) { //현재 단말기의 네트워크 가능여부를 알아내고 시작한다
            handler.postDelayed({

                val mListener = getSystemService(LOCATION_SERVICE) as LocationManager
                /*
                 * 현재 단말기의 위치설정 여부를 확인한다
                 */
                val isGPSLocation = mListener.isProviderEnabled(LocationManager.GPS_PROVIDER)
                val isNetworkLocation = mListener.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                Log.d(
                    "TAG",
                    """isGPSLocation : $isGPSLocation", isNetworkLocation = "$isGPSLocation"""
                )
                /*
                 * GPS 또는 Network Provide 로 위치설정이 되었다면 바로 이동한다
                 */
                when {
                    isGPSLocation -> {
                        val target = Intent(this, CurrentLocationActivity::class.java)
                        target.putExtra("provider", LocationManager.GPS_PROVIDER)
                        startActivity(target)
                        finish()
                    }
                    isNetworkLocation -> {
                        val target = Intent(this, CurrentLocationActivity::class.java)
                        target.putExtra("provider", LocationManager.NETWORK_PROVIDER)
                        startActivity(target)
                        finish()
                    }
                    else -> {
                        /*
                                 * 단말기에 위치설정이 되어있지않으면 위치설정 화면으로 이동한다
                                 * PermissionCheckUtil kotlin 파일의 Top-Level 클래스를 호출
                                 */
                        val manager: FragmentManager = supportFragmentManager
                        LocationSettingBox.newInstance(this@SplashActivity).showNow(manager, "위치설정")
                    }
                }
            }, 2000L)
        } else {
            Toast.makeText(
                AppInit.applicationContext(),
                "네트웍이 연결되지 않아 종료합니다", Toast.LENGTH_SHORT
            ).show()
            finish()
        }

    }

    override fun onRestart() {
        super.onRestart()
        /**
         * 위치설정이 되어있지않아 사용자가 위치설정을
         * 강제한 후 Back Key 를 눌렀을때 실행되는 코드
         */
        val target = Intent(this, CurrentLocationActivity::class.java)
        target.putExtra("provider", LocationManager.NETWORK_PROVIDER)
        finish()
        startActivity(target)
    }

    private fun isNetworkAvailable(): Boolean {
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