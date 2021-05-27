package com.mapo.eco100.views.map

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mapo.eco100.R
import com.mapo.eco100.config.LocalDataBase.Companion.garbageBagShopInfos
import com.mapo.eco100.databinding.BottomSheetShopListBinding

class BottomSheetShop : BottomSheetDialogFragment() {

    private var _binding: BottomSheetShopListBinding? = null
    private val binding get() = _binding!!
    private lateinit var shopListAdapter: MapShopListAdapter
    private var mainActivityContext: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mainActivityContext = requireContext()

        _binding = BottomSheetShopListBinding.inflate(inflater, container, false)

        shopListAdapter = MapShopListAdapter(this)
        shopListAdapter.shopListData = garbageBagShopInfos

        binding.shopList.adapter = shopListAdapter

        // 종량제 샵 리스트 클릭 이벤트
        shopListAdapter.setListItemClickListener(object :
            MapShopListAdapter.OnListItemClickListener {
            override fun onClick(view: View, position: Int) {
                val item = garbageBagShopInfos[position]

                val bundle = Bundle()
                bundle.putString("name", item.name)
                bundle.putDouble("lat", item.latitude)
                bundle.putDouble("long", item.longitude)

                val mapViewFragment = MapViewFragment.newInstance()
                mapViewFragment.arguments = bundle

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.contents, mapViewFragment)
                    .commit()
                shopListAdapter.notifyDataSetChanged()
                dismiss()
            }
        })

        return binding.root
    }

}