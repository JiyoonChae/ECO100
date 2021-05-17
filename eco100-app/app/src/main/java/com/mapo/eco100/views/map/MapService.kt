package com.mapo.eco100.views.map

import com.mapo.eco100.entity.staticmodel.ZeroShop
import retrofit2.Call
import retrofit2.http.GET

interface MapService {

    @GET("shop")
    fun getZeroShopList() : Call<ZeroShop>
}