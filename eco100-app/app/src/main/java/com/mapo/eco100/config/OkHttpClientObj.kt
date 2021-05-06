package com.mapo.eco100.config

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

object OkHttpClientObj {
    var client: OkHttpClient
    var retrofit = RetrofitObj.retrofit
        private set

    init {
        client = OkHttpClient.Builder()
            .addInterceptor(Interceptor { chain ->
                val origin = chain.request()//원래의 request 주소
                val newRequest = origin.newBuilder()
                    .addHeader("Authorization","Bearer ")
                    .build()
                chain.proceed(newRequest)
            }).addInterceptor(RetryInterceptor()).build()
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
}