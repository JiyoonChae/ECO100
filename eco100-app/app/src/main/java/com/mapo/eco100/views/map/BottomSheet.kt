package com.mapo.eco100.views.map

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mapo.eco100.config.NetworkSettings
import com.mapo.eco100.databinding.BottomSheetMapListBinding
import com.mapo.eco100.entity.staticmodel.ZeroShop
import com.mapo.eco100.views.network.NoConnectedDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetMapListBinding? = null
    private val binding get() = _binding!!
    private lateinit var shopListAdapter: MapListAdapter
    private lateinit var listData: MutableList<ZeroShop>
    private lateinit var mainActivityContext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mainActivityContext = requireContext()
        _binding = BottomSheetMapListBinding.inflate(inflater, container, false)

        shopListAdapter = MapListAdapter(this)
        binding.shopList.adapter = shopListAdapter
        //getZeroShopList()
        getListTest()
        return binding.root
    }


    private fun getListTest() {
        println("response!! >> getListTest()")
        listData = mutableListOf()
        val service: MapService = NetworkSettings.retrofit.build().create(MapService::class.java)
        service.getZeroShopList().enqueue(object : Callback<ArrayList<ZeroShop>> {
            override fun onResponse(
                call: Call<ArrayList<ZeroShop>>,
                response: Response<ArrayList<ZeroShop>>
            ) {
                if (response.isSuccessful) {
                    println("response >> Success!!")
                    println("response.body >> ${response.body()}")
                    val resultList: ArrayList<ZeroShop>? = response.body()
                    println("result >> ")
                    println("result >> ${resultList?.size}")
                    resultList?.forEach { it ->
                        listData.add(
                            ZeroShop(
                                it.id,
                                it.name,
                                it.address,
                                it.phoneNum,
                                it.runningInfo,
                                it.webUrl,
                                it.latitude,
                                it.longitude,
                                it.imgUrl,
                                it.logoUrl
                            )
                        )
                    }
                    shopListAdapter.listData = listData
                    shopListAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<ArrayList<ZeroShop>>, t: Throwable) {

                println("response!! >> ${t.message}")
            }
        })
    }

}