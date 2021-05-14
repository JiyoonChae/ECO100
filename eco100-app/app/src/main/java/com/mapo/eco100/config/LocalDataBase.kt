package com.mapo.eco100.config

class LocalDataBase {
    companion object {
        var isLoading = false
        val trashBagInfos: MutableList<GarbageBagShopInfo> = mutableListOf()
        val FAQ_JEJU_list: MutableList<FAQ_JEJU> = mutableListOf()
        val FAQ_list: MutableList<FAQ> = mutableListOf()
        val zeroShopList: MutableList<ZeroShop> = mutableListOf()
    }
}

data class GarbageBagShopInfo(

)

data class FAQ(
    val id: Int,
    val category: String,
    val question: String,
    val answer: String
)

data class FAQ_JEJU(
    val id: Int,
    val type: String,
    val name: String,
    val details: String,
    val precautions: String
)

data class ZeroShop(
    val id: Int,
    val name: String,
    val address: String,
    val phoneNum: String,
    val runningInfo: String,
    val webUrl: String,
    val latitude: Float,
    val longitude: Float,
    val imgUrl: String,
    val logoUrl: String
)