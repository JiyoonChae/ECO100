package com.mapo.eco100.entity.staticmodel

import com.google.gson.annotations.SerializedName

data class GarbageBagShopInfo(
    val id: Int,
    val address1: String?,
    val address2: String?,
    val name: String,
    val latitude : Double,
    val longitude : Double
)
