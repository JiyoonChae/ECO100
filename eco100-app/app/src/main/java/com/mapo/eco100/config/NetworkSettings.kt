package com.mapo.eco100.config

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import com.mapo.eco100.R
import com.mapo.eco100.views.network.NoConnectedDialog
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkSettings {

    var client: OkHttpClient
    var retrofit : Retrofit.Builder
        private set

    init {
        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://rpinas.iptime.org:10122")

        client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val origin = chain.request()//원래의 request 주소
                val newRequest = origin.newBuilder()
                    .addHeader("Authorization", "Bearer ")
                    .build()
                chain.proceed(newRequest)
            }.addInterceptor(RetryInterceptor()).build()

        retrofit.client(client)
    }

    private class RetryInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            var response = chain.proceed(request)
            var cnt = 0
            var limit = 2
            while (!response.isSuccessful && cnt < limit) {
                cnt++
                response = chain.proceed(request)
            }
            return response
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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

    fun NoInternetConnectedCallback(context: Context) {
        if (!isNetworkAvailable(context)) {
            Toast.makeText(
                context, "네트워크가 연결되지 않았습니다.\nWi-Fi또는 데이터를 활성화 해주세요.", Toast.LENGTH_SHORT
            ).show()
            val dialog = NoConnectedDialog(context)
            dialog.show()
        } else {
            Toast.makeText(context, "네트워크가 연결되었습니다.\n다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
        }
    }
}