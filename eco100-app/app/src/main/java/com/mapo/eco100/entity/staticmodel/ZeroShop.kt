package com.mapo.eco100.entity.staticmodel

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ZeroShop(
    val id: Int,
    val name: String,
    val address: String,
    @SerializedName("phone_num")
    val phoneNum: String?,
    @SerializedName("running_info")
    val runningInfo: String?,
    @SerializedName("web_url")
    val webUrl: String?,
    val latitude: Float,
    val longitude: Float,
    @SerializedName("img_url")
    val imgUrl: String?,
    @SerializedName("logo_url")
    val logoUrl: String?
) : Serializable
