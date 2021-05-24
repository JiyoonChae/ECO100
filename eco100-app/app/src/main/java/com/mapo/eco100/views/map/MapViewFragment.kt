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
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.mapo.eco100.R
import com.mapo.eco100.config.LocalDataBase.Companion.garbageBagShopInfos
import com.mapo.eco100.config.LocalDataBase.Companion.zeroShopList
import com.mapo.eco100.databinding.FragmentMapBinding

class MapViewFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationClickListener,
    GoogleMap.OnMyLocationButtonClickListener {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var mMap: GoogleMap
    private lateinit var bitmapDraw: BitmapDrawable
    private lateinit var bitmap: Bitmap
    private val bottomSheetShop = BottomSheetShop()
    private val bottomSheetZeroShop = BottomSheetZeroShop()
    private lateinit var mapFragment: SupportMapFragment
    private var selectedShop: LatLng? = null
    private var selectedShopName: String? = null

    private lateinit var clusterManager: ClusterManager<MyItem>
    private lateinit var clusterRender: ClusterRenderer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMapBinding.inflate(inflater, container, false)

        // check args
        selectedShopName = arguments?.getString("name")
        val resultLat = arguments?.getDouble("lat")
        val resultLong = arguments?.getDouble("long")
        Log.d("map", "resultLat : $resultLat , resultLong : $resultLong")

        if (arguments != null) {
            selectedShop = arguments?.let { LatLng(resultLat!!, resultLong!!) }
            Log.d("map", "selectedShop: $selectedShop")
            binding.radioGroup.visibility = View.GONE
        }


        binding.radioGroup.visibility = View.VISIBLE

        // 비트맵
        bitmapDraw = ResourcesCompat.getDrawable(
            resources,
            R.drawable.img_map_select_garbage,
            null
        ) as BitmapDrawable
        bitmap = Bitmap.createScaledBitmap(bitmapDraw.bitmap, 54, 72, false)

        // 맵
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

// 라디오 버튼이 눌렸을 때 해당 리스트의 데이터를 가져온다.
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->

            mMap.clear()
            clusterManager.clearItems()

            when (checkedId) {

                // 종량제판매처 버튼 클릭시
                R.id.mapShopBtn -> {

                    binding.openListIcon.setColorFilter(
                        ContextCompat.getColor(binding.root.context, R.color.primary_color)
                    )
                    binding.openListText.setTextColor(
                        (ContextCompat.getColor(binding.root.context, R.color.primary_color))
                    )
                    // 각 항목별 가게 리스트를 보여준다.
                    binding.openList.setOnClickListener {
                        bottomSheetShop.setStyle(STYLE_NORMAL, R.style.Map_BottomSheetDialog)
                        bottomSheetShop.show(childFragmentManager, bottomSheetShop.tag)
                    }
                    getShopList()
                }

                // 제로웨잇샵 버튼 클릭시
                R.id.mapZeroBtn -> {

                    binding.openListIcon.setColorFilter(
                        ContextCompat.getColor(binding.root.context, R.color.point_color)
                    )
                    binding.openListText.setTextColor(
                        (ContextCompat.getColor(binding.root.context, R.color.point_color))
                    )
                    // 각 항목별 가게 리스트를 보여준다.
                    binding.openList.setOnClickListener {
                        bottomSheetZeroShop.setStyle(STYLE_NORMAL, R.style.Map_BottomSheetDialog)
                        bottomSheetZeroShop.show(childFragmentManager, bottomSheetZeroShop.tag)
                    }
                    getZeroWasteShopList()
                }

                else -> Log.d("map", "checkedId : $checkedId, 잘못된 접근")
            }

        }

        return binding.root
    }


    // 맵 실행
    override fun onMapReady(googleMap: GoogleMap) {

        // init & seMapType
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        clusterManager = ClusterManager(binding.root.context, mMap)
        clusterRender = ClusterRenderer(binding.root.context, mMap, clusterManager)

        mMap.setOnCameraIdleListener(clusterManager)
        mMap.setOnMarkerClickListener(clusterManager)

        // 초기 설정
        initLocation()

        // 현재 위치 정보 제공 동의 확인
        checkPermission()

        // 선택된 가게가 있다면 해당 가게로 지도를 이동시킨다.
        //getSelectedShoInfo()

        arguments?.clear()


    }

    // 사용자 현재 위치 제공여부를 확인한다.
    private fun checkPermission() {
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
        mMap.isMyLocationEnabled = true
    }

    private fun initLocation() {
        //getShopList()

        if (arguments != null) {
            getSelectedShoInfo()
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(37.566168, 126.901609)))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
        }
    }

    /** ecobox -> map  **/
    // 선택된 제로샵 위치를 보여준다.
    private fun getSelectedShoInfo() {

        selectedShop?.let {
            Log.d("map", "In selectedShop: $it")
            mMap.moveCamera(CameraUpdateFactory.newLatLng(it))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
            val markerOptions = MarkerOptions()
            markerOptions.position(it).title(selectedShopName)
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
            mMap.addMarker(markerOptions)
        }
    }

    // 제로웨이스트 샵 리스트를 지도에 뿌려준다.
    private fun getZeroWasteShopList() {

        // marker bitmap
        bitmapDraw = ResourcesCompat.getDrawable(
            resources,
            R.drawable.img_map_select_zero,
            null
        ) as BitmapDrawable
        bitmap = Bitmap.createScaledBitmap(bitmapDraw.bitmap, 54, 72, false)

        for (zeroShop in zeroShopList) {
            Log.d("map", "url : ${zeroShop.logoUrl}")
            clusterManager.addItem(
                MyItem(
                    LatLng(zeroShop.latitude.toDouble(), zeroShop.longitude.toDouble()),
                    zeroShop.name,
                    zeroShop.address,
                    BitmapDescriptorFactory.fromBitmap(bitmap)
                )
            )

        }
        mMap.moveCamera(
            CameraUpdateFactory.newLatLng(
                LatLng(zeroShopList[0].latitude.toDouble(), zeroShopList[0].longitude.toDouble())
            )
        )
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18f))
        clusterManager.cluster()

    }

    // 종량제 봉투 판매업소 리스트를 지도에 뿌려준다.
    private fun getShopList() {

        // marker bitmap
        bitmapDraw = ResourcesCompat.getDrawable(
            resources,
            R.drawable.img_map_select_garbage,
            null
        ) as BitmapDrawable
        bitmap = Bitmap.createScaledBitmap(bitmapDraw.bitmap, 54, 72, false)

        /*for (garbageShop in garbageBagShopInfos) {
            clusterManager.addItem(
                MyItem(
                    LatLng(garbageShop.latitude, garbageShop.longitude),
                    garbageShop.name,
                    garbageShop.address1.toString(),
                    BitmapDescriptorFactory.fromBitmap(bitmap)
                )
            )
        }*/

        mMap.moveCamera(
            CameraUpdateFactory.newLatLng(
                LatLng(garbageBagShopInfos[0].latitude, garbageBagShopInfos[0].longitude)
            )
        )
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12f))
        //clusterManager.cluster()
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