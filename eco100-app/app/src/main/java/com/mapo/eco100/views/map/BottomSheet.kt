package com.mapo.eco100.views.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mapo.eco100.R
import com.mapo.eco100.databinding.BottomSheetMapListBinding

class BottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetMapListBinding? = null
    private val binding get() = _binding!!
    private lateinit var shopListAdapter: MapListAdapter
    private lateinit var listData: MutableList<ShopData>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = BottomSheetMapListBinding.inflate(inflater, container, false)

        shopListAdapter = MapListAdapter(this)
        binding.shopList.adapter = shopListAdapter
        getZeroShopList()

        return binding.root
    }

    private fun getZeroShopList() {
        listData = mutableListOf()
        listData.apply {
            add(ShopData("알맹상점", "서울특별시 마포구 월드컵로 49 2층 알맹상점", "껍데기는 가라! 알맹이만 오라! 리필스테이션"))
            add(ShopData("디어얼스", "서울특별시 서대문구 수색로 43 104호", "Earth-friendly from beginning to end!"))
            add(ShopData("타이거릴리", "서울특별시마포구 포은로 119 1층", "낭비 없는 사회를 꿈꾸는 비건 제로 웨이스트샵"))

            shopListAdapter.listData = listData
            shopListAdapter.notifyDataSetChanged()
        }
    }

    /* override fun onActivityCreated(savedInstanceState: Bundle?) {
         super.onActivityCreated(savedInstanceState)
         view?.findViewById<AppCompatButton>(R.id.)
     }*/
}