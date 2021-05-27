package com.mapo.eco100.config

import android.os.Build
import android.text.Editable
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.mapo.eco100.entity.staticmodel.FAQ
import com.mapo.eco100.entity.staticmodel.FAQ_JEJU
import com.mapo.eco100.entity.staticmodel.GarbageBagShopInfo
import com.mapo.eco100.entity.staticmodel.ZeroShop
import java.util.stream.Collectors

class LocalDataBase {
    companion object {
        val appInitLoading = MutableLiveData<Boolean>()
        val garbageBagShopInfos: MutableList<GarbageBagShopInfo> = mutableListOf()
        val FAQ_JEJU_list: MutableList<FAQ_JEJU> = mutableListOf()
        val FAQ_list: MutableList<FAQ> = mutableListOf()
        val zeroShopList: MutableList<ZeroShop> = mutableListOf()

        @RequiresApi(Build.VERSION_CODES.N)
        fun search_FAQ_JEJU(word:Editable) : MutableList<FAQ_JEJU>? {
            return FAQ_JEJU_list.stream().filter { faq ->
                faq.name.contains(word)
            }.collect(Collectors.toList())
        }

        @RequiresApi(Build.VERSION_CODES.N)
        fun search_FAQ(word:String) : MutableList<FAQ>? {
            return FAQ_list.stream().filter { faq ->
                faq.question.contains(word)
            }.collect(Collectors.toList())
        }
    }
}