package com.mapo.eco100.views.map

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment.STYLE_NORMAL
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mapo.eco100.R
import com.mapo.eco100.config.LocalDataBase.Companion.zeroShopList
import com.mapo.eco100.config.NetworkSettings
import com.mapo.eco100.databinding.FragmentMapBinding
import com.mapo.eco100.entity.staticmodel.ZeroShop
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapViewFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationClickListener,
    GoogleMap.OnMyLocationButtonClickListener{

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var bitmapDraw: BitmapDrawable
    private lateinit var bitmap: Bitmap
    private lateinit var listData: MutableList<ZeroShop>
    private lateinit var gMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMapBinding.inflate(inflater, container, false)
        listData = ArrayList()

        // radioBtn
        binding.mapShopBtn.isChecked = true
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->

            println("mapCheckedId >> $checkedId")
            when (checkedId) {
                R.id.mapShopBtn -> {
                    println("mapCheckedId >> 종량제")

                }
                R.id.mapZeroBtn -> {
                    println("mapCheckedId >> 제로웨잇")
                    getZeroWasteShopList()
                }
                else -> {
                    println("mapCheckedId >> $checkedId")
                }
            }
        }

        // map
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        bitmapDraw = resources.getDrawable(R.drawable.ic_map_ecoduck) as BitmapDrawable
        bitmap = Bitmap.createScaledBitmap(bitmapDraw.bitmap, 112, 188, false)


        // list
        binding.openList.setOnClickListener {
            val bottomSheet = BottomSheet()
            bottomSheet.setStyle(STYLE_NORMAL, R.style.Map_BottomSheetDialog)
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }

        return binding.root
    }

    // getZeroWasteShopList
    private fun getZeroWasteShopList() {
        listData = zeroShopList
        listData.forEach {
            println("listData >> $it")
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {

        gMap = googleMap

        listData.forEach {
            val markerOptions = MarkerOptions()
            markerOptions.position(LatLng(it.latitude.toDouble(), it.longitude.toDouble()))
                .title(it.name)
            gMap.addMarker(markerOptions)
        }

        gMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(37.52487, 126.92723)))


    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(context, "Current location:\n$location", Toast.LENGTH_LONG)
            .show()
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(context, "MyLocation button clicked", Toast.LENGTH_SHORT)
            .show()

        return false
    }

    companion object {
        fun newInstance(): MapViewFragment {
            return MapViewFragment()
        }
    }
}