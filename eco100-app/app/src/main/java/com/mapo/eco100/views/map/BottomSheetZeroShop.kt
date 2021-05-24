package com.mapo.eco100.views.map

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mapo.eco100.R
import com.mapo.eco100.config.LocalDataBase.Companion.zeroShopList
import com.mapo.eco100.databinding.BottomSheetZeroShopListBinding

class BottomSheetZeroShop : BottomSheetDialogFragment() {

    private var _binding: BottomSheetZeroShopListBinding? = null
    private val binding get() = _binding!!
    private lateinit var shopListAdapter: MapZeroShopListAdapter
    private var mainActivityContext: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mainActivityContext = requireContext()

        _binding = BottomSheetZeroShopListBinding.inflate(inflater, container, false)

        shopListAdapter = MapZeroShopListAdapter(this)
        shopListAdapter.shopListData = zeroShopList

        binding.shopList.adapter = shopListAdapter

        // 제로샵 리스트 클릭 이벤트
        shopListAdapter.setListItemClickListener(object :
            MapZeroShopListAdapter.OnListItemClickListener {
            override fun onClick(view: View, position: Int) {
                val item = zeroShopList[position]

                val bundle = Bundle()
                bundle.putString("name", item.name)
                bundle.putDouble("lat", item.latitude.toDouble())
                bundle.putDouble("long", item.longitude.toDouble())

                val mapViewFragment = MapViewFragment()
                mapViewFragment.arguments = bundle

                parentFragmentManager.beginTransaction()
                    .replace(R.id.map, mapViewFragment)
                    .commit()
                shopListAdapter.notifyDataSetChanged()
                dismiss()
            }
        })

        return binding.root
    }

}