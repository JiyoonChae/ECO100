package com.mapo.eco100.views.map


interface ShopLocationListener {
    fun setShopName(shopName: String)
    fun setShopLocation(latitude: Double, longitude: Double)
}