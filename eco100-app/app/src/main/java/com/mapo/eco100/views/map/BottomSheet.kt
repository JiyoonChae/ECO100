package com.mapo.eco100.views.map

import android.content.Context
import android.os.Bundle
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
    private lateinit var listData: MutableList<ShopData>
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
        getZeroShopList()
        //getListTest()
        return binding.root
    }


    private fun getListTest(){
            println("response!! >> getListTest()")
             val service : MapService = NetworkSettings.retrofit.build().create(MapService::class.java)
             println("response!! >> $service")
             service.getZeroShopList().enqueue(object : Callback<ZeroShop> {
                 override fun onResponse(call: Call<ZeroShop>, response: Response<ZeroShop>) {
                     if (response.isSuccessful) {
                         println("response!! >> ${response.body()}")
                     }
                 }

                 override fun onFailure(call: Call<ZeroShop>, t: Throwable) {
                     println("response!! >> ${t.message}")
                 }
             })
    }

    private fun getZeroShopList() {

        listData = mutableListOf()
        listData.apply {
            add(ShopData("알맹상점", "서울특별시 마포구 월드컵로 49 2층 알맹상점", "껍데기는 가라! 알맹이만 오라! 리필스테이션"))
            add(ShopData("디어얼스", "서울특별시 서대문구 수색로 43 104호", "Earth-friendly from beginning to end!"))
            add(ShopData("타이거릴리", "서울특별시마포구 포은로 119 1층", "낭비 없는 사회를 꿈꾸는 비건 제로 웨이스트샵"))
            add(ShopData("더피커", "서울특별시 마포구 월드컵로 49 2층 알맹상점", "껍데기는 가라! 알맹이만 오라! 리필스테이션"))
            add(ShopData("송포어스", "서울특별시 서대문구 수색로 43 104호", "Earth-friendly from beginning to end!"))
            add(ShopData("동그라미리필러리", "서울특별시마포구 포은로 119 1층", "낭비 없는 사회를 꿈꾸는 비건 제로 웨이스트샵"))
            add(ShopData("유민얼랏", "서울특별시마포구 포은로 119 1층", "낭비 없는 사회를 꿈꾸는 비건 제로 웨이스트샵"))
            add(ShopData("알맹상점", "서울특별시 마포구 월드컵로 49 2층 알맹상점", "껍데기는 가라! 알맹이만 오라! 리필스테이션"))
            add(ShopData("디어얼스", "서울특별시 서대문구 수색로 43 104호", "Earth-friendly from beginning to end!"))
            add(ShopData("타이거릴리", "서울특별시마포구 포은로 119 1층", "낭비 없는 사회를 꿈꾸는 비건 제로 웨이스트샵"))
            add(ShopData("더피커", "서울특별시 마포구 월드컵로 49 2층 알맹상점", "껍데기는 가라! 알맹이만 오라! 리필스테이션"))
            add(ShopData("송포어스", "서울특별시 서대문구 수색로 43 104호", "Earth-friendly from beginning to end!"))
            add(ShopData("동그라미리필러리", "서울특별시마포구 포은로 119 1층", "낭비 없는 사회를 꿈꾸는 비건 제로 웨이스트샵"))
            add(ShopData("유민얼랏", "서울특별시마포구 포은로 119 1층", "낭비 없는 사회를 꿈꾸는 비건 제로 웨이스트샵"))
            shopListAdapter.listData = listData
            shopListAdapter.notifyDataSetChanged()
        }
    }

    /* override fun onActivityCreated(savedInstanceState: Bundle?) {
         super.onActivityCreated(savedInstanceState)
         view?.findViewById<AppCompatButton>(R.id.)
     }*/
}