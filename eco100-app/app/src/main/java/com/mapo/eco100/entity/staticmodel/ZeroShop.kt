package com.mapo.eco100.entity.staticmodel

data class ZeroShop(
    val id: Int,
    val name: String,
    val address: String,
    val phoneNum: String?,
    val runningInfo: String?,
    val webUrl: String?,
    val latitude: Float,
    val longitude: Float,
    val imgUrl: String?,
    val logoUrl: String?
)
