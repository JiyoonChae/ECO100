package com.mapo.eco100.views.map

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.DialogFragment.STYLE_NORMAL
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mapo.eco100.R
import com.mapo.eco100.config.LocalDataBase.Companion.zeroShopList
import com.mapo.eco100.databinding.FragmentMapBinding

class MapViewFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationClickListener,
    GoogleMap.OnMyLocationButtonClickListener, ShopLocationListener {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var mMap: GoogleMap
    private lateinit var bitmapDraw: BitmapDrawable
    private lateinit var bitmap: Bitmap
    private val bottomSheet = BottomSheet()
    private lateinit var mapFragment : SupportMapFragment
    var testLocation = LatLng(37.54870902828827, 127.04403673987844)

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapFragment.onSaveInstanceState(outState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMapBinding.inflate(inflater, container, false)

        // radioBtn
        binding.mapShopBtn.isChecked = true

        // map
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // marker bitmap
        bitmapDraw = ResourcesCompat.getDrawable(
            resources,
            R.drawable.img_map_zeroshop,
            null
        ) as BitmapDrawable
        bitmap = Bitmap.createScaledBitmap(bitmapDraw.bitmap, 54, 72, false)

        return binding.root
    }

    private fun setGoogleMap(map : GoogleMap){
        mMap = map
        Log.d("map", "mMap >> $mMap")
        Log.d("map", "map >> $map")

    }

    // map
    override fun onMapReady(googleMap: GoogleMap) {

        setGoogleMap(googleMap)
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        if (ActivityCompat.checkSelfPermission(
                binding.root.context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                binding.root.context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        googleMap.isMyLocationEnabled = true

        // 각 항목별 가게 리스트를 보여준다.
        binding.openList.setOnClickListener {
            bottomSheet.setStyle(STYLE_NORMAL, R.style.Map_BottomSheetDialog)
            bottomSheet.show(childFragmentManager, bottomSheet.tag)
        }

        // 라디오 버튼이 눌렸을 때 해당 리스트의 데이터를 가져온다.
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            mMap.clear()
            when (checkedId) {
                R.id.mapShopBtn -> {
                    binding.openListIcon.setColorFilter(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.primary_color
                        )
                    )
                    binding.openListText.setTextColor(
                        (ContextCompat.getColor(
                            binding.root.context,
                            R.color.primary_color
                        ))
                    )
                    getShopList()
                }
                R.id.mapZeroBtn -> {
                    binding.openListIcon.setColorFilter(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.point_color
                        )
                    )
                    binding.openListText.setTextColor(
                        (ContextCompat.getColor(
                            binding.root.context,
                            R.color.point_color
                        ))
                    )
                    getZeroWasteShopList()
                }
                else -> Log.d("map", "checkedId : $checkedId")
            }
        }

    }

    // 제로웨이스트 샵 리스트를 가져온다.
    private fun getZeroWasteShopList() {

        for (zeroShop in zeroShopList) {
            val markerOptions = MarkerOptions()
            markerOptions.position(
                LatLng(
                    zeroShop.latitude.toDouble(),
                    zeroShop.longitude.toDouble()
                )
            ).title(zeroShop.name).icon(BitmapDescriptorFactory.fromBitmap(bitmap))
            mMap.addMarker(markerOptions)
        }
        Log.d("map", "getZeroshopList map >> $mMap")

        mMap.moveCamera(CameraUpdateFactory.newLatLng(testLocation)) // d위치 변경하기
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
    }

    // 종량제 봉투 판매업소 리스트를 가져온다.
    private fun getShopList() {

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

    // 선택된 가게의 이름을 가져온다.
    override fun setShopName(shopName: String) {
        Log.d("map", "$shopName 선택")
    }

    // 선택된 가게에 대한 위경도 정보를 가져온다.
    override fun setShopLocation(latitude: Double, longitude: Double) {
        testLocation = LatLng(latitude, longitude)
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(selectedShopLocation))
        //getGoogleMap().animateCamera(CameraUpdateFactory.zoomTo(15f))
    }

    fun test(){
        Log.d("map","test")
    }
}