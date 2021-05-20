package com.mapo.eco100.views.map

import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.content.Intent.getIntentOld
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mapo.eco100.config.LocalDataBase.Companion.zeroShopList
import com.mapo.eco100.config.NetworkSettings
import com.mapo.eco100.databinding.BottomSheetMapListBinding
import com.mapo.eco100.entity.staticmodel.ZeroShop
import com.mapo.eco100.views.network.NoConnectedDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.ClassCastException

class BottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetMapListBinding? = null
    private val binding get() = _binding!!
    private lateinit var shopListAdapter: MapListAdapter
    private var mainActivityContext: Context? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mainActivityContext = requireContext()
        _binding = BottomSheetMapListBinding.inflate(inflater, container, false)

        shopListAdapter = MapListAdapter(this)
        shopListAdapter.listData = zeroShopList

        shopListAdapter.setListItemClickListener(object : MapListAdapter.OnListItemClickListener {
            override fun onClick(view: View, position: Int) {
                val item = zeroShopList[position]
                Log.d("map", item.name)
                MapViewFragment().setShopName(item.name)
                MapViewFragment().setShopLocation(
                    item.latitude.toDouble(),
                    item.longitude.toDouble(),
                )
                shopListAdapter.notifyDataSetChanged()
                dismiss()
            }
        })

        binding.shopList.adapter = shopListAdapter
        return binding.root
    }
}