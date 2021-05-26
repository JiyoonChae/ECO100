package com.mapo.eco100.views.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.os.Build
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
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
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.mapo.eco100.R
import com.mapo.eco100.config.LocalDataBase.Companion.garbageBagShopInfos
import com.mapo.eco100.config.LocalDataBase.Companion.zeroShopList
import com.mapo.eco100.databinding.FragmentMapBinding

class MapViewFragment : Fragment(), PermissionListener, OnMapReadyCallback,
    GoogleMap.OnMyLocationClickListener,
    GoogleMap.OnMyLocationButtonClickListener {

    // 뷰 바인딩
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    // 맵 설젇
    private lateinit var mMap: GoogleMap
    private lateinit var mapFragment: SupportMapFragment

    // 바텀시트(목록 열기) 창 설정
    private val bottomSheetShop = BottomSheetShop()
    private val bottomSheetZeroShop = BottomSheetZeroShop()

    // 현재 위치 설정
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var myLocationCallBack: MyLocationCallBack? = null

    // 데이터 클러스터링 설정
    private lateinit var clusterManager: ClusterManager<MyItem>

    // 지도 아이콘 설정
    private lateinit var bitmapDraw: BitmapDrawable
    private lateinit var bitmap: Bitmap

    // 선택된 샵 정보 설정
    private var selectedShop: LatLng? = null
    private var selectedShopName: String? = null

    // 내 위치 저장
    private lateinit var myLocation: LatLng

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMapBinding.inflate(inflater, container, false)

        // 선택된 샵 정보가 있는지 확인한다.
        selectedShopName = arguments?.getString("name")
        val resultLat = arguments?.getDouble("lat")
        val resultLong = arguments?.getDouble("long")
        Log.d("map", "resultLat : $resultLat , resultLong : $resultLong")

        if (arguments != null) {
            selectedShop = arguments?.let { LatLng(resultLat!!, resultLong!!) }
            Log.d("map", "selectedShop: $selectedShop")

        }

        // 맵 위치 권한 설정을 확인한다.
        if (Build.VERSION.SDK_INT >= 23) {
            TedPermission.with(binding.root.context)
                .setPermissionListener(this)
                .setRationaleMessage("위치 정보 제공이 필요한 서비스입니다..")
                .setDeniedMessage("[설정] -> [권한]에서 권한 변경이 가능합니다.")
                .setDeniedCloseButtonText("닫기")
                .setGotoSettingButtonText("설정")
                .setRationaleTitle("ECO100")
                .setPermissions(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                .check()
        }

        // 비트맵 생성
        bitmapDraw = ResourcesCompat.getDrawable(
            resources,
            R.drawable.ic_map_ecoduck,
            null
        ) as BitmapDrawable
        bitmap = Bitmap.createScaledBitmap(bitmapDraw.bitmap, 96, 146, false)

        // 맵
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // 라디오 버튼이 눌렸을 때 해당 리스트의 데이터를 가져온다.
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->

            mMap.clear()
            clusterManager.clearItems()
            binding.openList.visibility = View.VISIBLE

            when (checkedId) {

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
                    getGarbageShopList()
                }

                else -> Log.d("map", "checkedId : $checkedId, 잘못된 접근")
            }

        }

        binding.goMyLocation.setOnClickListener {
            setMyLocation()
        }

        binding.openList.setOnClickListener {
            bottomSheetZeroShop.setStyle(STYLE_NORMAL, R.style.Map_BottomSheetDialog)
            bottomSheetZeroShop.show(childFragmentManager, bottomSheetZeroShop.tag)
        }

        return binding.root
    }

    // 위치 권한이 제공됐는지 확인한다.
    private fun checkPermissions(): Boolean {
        val fineLocalPermission = ContextCompat.checkSelfPermission(
            binding.root.context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val coarseLocationPermission = ContextCompat.checkSelfPermission(
            binding.root.context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (fineLocalPermission == PackageManager.PERMISSION_GRANTED && coarseLocationPermission == PackageManager.PERMISSION_GRANTED) {
            mFusedLocationClient?.requestLocationUpdates(locationRequest, myLocationCallBack, null)
            return true
        }

        return false
    }

    // 초기 위치를 설정한다.
    @SuppressLint("VisibleForTests")
    private fun initLocation() {

        if (!checkPermissions()) {
            val latLng = LatLng(37.566168, 126.901609)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
        } else {
            mFusedLocationClient = FusedLocationProviderClient(binding.root.context)
            myLocationCallBack = MyLocationCallBack()
            locationRequest =
                LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            //.setInterval(10000).setFastestInterval(5000)
        }

    }

    private fun addLocationListener() {
        checkPermissions()
    }

    // 종량제 샵 리스트를 지도에 뿌려준다.
    private fun getGarbageShopList() {

        // 종량제샵 마커 이미지 설정
        val bitmapDrawGarbageShop = ResourcesCompat.getDrawable(
            resources,
            R.drawable.img_map_select_garbage,
            null
        ) as BitmapDrawable
        val bitmapGarbageShop =
            Bitmap.createScaledBitmap(bitmapDrawGarbageShop.bitmap, 60, 86, false)

        // 종량제 샵 리스트에서 데이터를 가져온다.
        for (garbageShop in garbageBagShopInfos) {
            clusterManager.addItem(
                MyItem(
                    1,
                    LatLng(garbageShop.latitude, garbageShop.longitude),
                    garbageShop.name,
                    garbageShop.address1.toString(),
                    BitmapDescriptorFactory.fromBitmap(bitmapGarbageShop)
                )
            )
        }

        setMyLocation()
        clusterManager.cluster()

    }

    // 제로웨이스트 샵 리스트를 지도에 뿌려준다.
    private fun getZeroWasteShopList() {

        // 제로샵 마커 이미지 설정
        val bitmapDrawZeroShop = ResourcesCompat.getDrawable(
            resources,
            R.drawable.img_map_select_zero,
            null
        ) as BitmapDrawable
        val bitmapZeroShop = Bitmap.createScaledBitmap(bitmapDrawZeroShop.bitmap, 60, 86, false)

        // 제로샵 리스트에서 데이터를 가져온다.
        for (zeroShop in zeroShopList) {
            clusterManager.addItem(
                MyItem(
                    2,
                    LatLng(zeroShop.latitude.toDouble(), zeroShop.longitude.toDouble()),
                    zeroShop.name,
                    zeroShop.address,
                    BitmapDescriptorFactory.fromBitmap(bitmapZeroShop)
                )
            )
        }
        setMyLocation()
        clusterManager.cluster()

    }

    // 내 위치 설정을 맵에 추가한다.
    private fun setMyLocation() {

        mMap.addMarker(
            MarkerOptions().position(myLocation).title("나 여기!")
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
        )?.showInfoWindow()
        mMap.moveCamera(
            CameraUpdateFactory.newLatLng(
                myLocation
            )
        )
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14f))
    }

    // 선택된 제로샵 위치를 보여준다.
    private fun getSelectedShoInfo() {

        selectedShop?.let {
            binding.radioGroup.visibility = View.GONE
            Log.d("map", "In selectedShop: $it")
            mMap.moveCamera(CameraUpdateFactory.newLatLng(it))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
            val markerOptions = MarkerOptions()
            markerOptions.position(it).title(selectedShopName)
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
            mMap.addMarker(markerOptions)
        }
    }

    // 맵 처음 진입시 리스트 설정
    private fun setInitList() {

        /*if (arguments != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(selectedShop))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15f))
            val markerOptions = MarkerOptions()
            markerOptions.position(selectedShop).title(selectedShopName)
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
            mMap.addMarker(markerOptions)
        } else {*/

        // 제로샵 마커 이미지 설정
        val bitmapDrawZeroShop = ResourcesCompat.getDrawable(
            resources,
            R.drawable.img_map_select_zero,
            null
        ) as BitmapDrawable
        val bitmapZeroShop = Bitmap.createScaledBitmap(bitmapDrawZeroShop.bitmap, 60, 86, false)

        // 제로샵 리스트에서 데이터를 가져온다.
        for (zeroShop in zeroShopList) {
            clusterManager.addItem(
                MyItem(
                    2,
                    LatLng(zeroShop.latitude.toDouble(), zeroShop.longitude.toDouble()),
                    zeroShop.name,
                    zeroShop.address,
                    BitmapDescriptorFactory.fromBitmap(bitmapZeroShop)
                )
            )
        }
        clusterManager.cluster()

    }

    override fun onResume() {
        super.onResume()
        addLocationListener()
    }

    // 맵 실행
    override fun onMapReady(googleMap: GoogleMap) {

        // init & seMapType
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        // 초기 위치 설정
        initLocation()

        // 클러스터 설정
        clusterManager = ClusterManager(binding.root.context, mMap)
        clusterManager.renderer = MarkerRender(binding.root.context, mMap, clusterManager)

        // 리스너 추가
        mMap.setOnCameraIdleListener(clusterManager)
        mMap.setOnMarkerClickListener(clusterManager)

        setInitList()
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


    /** PERMISSION CHECK **/
    override fun onPermissionGranted() {
        Toast.makeText(binding.root.context, "위치 정보 제공이 완료되었습니다.", Toast.LENGTH_SHORT).show()
    }

    override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
        Toast.makeText(binding.root.context, "위치 정보 제공이 거부되었습니다.", Toast.LENGTH_SHORT).show()
    }

    // 내 위치 정보 업데이트
    inner class MyLocationCallBack : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)

            val location = locationResult.lastLocation
            location.run {
                mMap.clear()
                val latLng = LatLng(latitude, longitude)
                myLocation = latLng

                val me = mMap.addMarker(
                    MarkerOptions().position(latLng).title("나 여기!")
                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap))
                )
                me?.showInfoWindow()
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            }
        }
    }

    // 마커 클러스터링 전 랜더링
    inner class MarkerRender(
        context: Context?,
        map: GoogleMap?, clusterManager: ClusterManager<MyItem>?
    ) : DefaultClusterRenderer<MyItem>(context, map, clusterManager) {

        override fun onBeforeClusterItemRendered(item: MyItem, markerOptions: MarkerOptions) {
            super.onBeforeClusterItemRendered(item, markerOptions)
            when (item.getCategory()) {
                1 -> {
                    val bitmapDrawZeroShop = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.img_map_select_garbage,
                        null
                    ) as BitmapDrawable
                    val bitmapZeroShop =
                        Bitmap.createScaledBitmap(bitmapDrawZeroShop.bitmap, 60, 86, false)
                    markerOptions.icon(
                        BitmapDescriptorFactory.fromBitmap(bitmapZeroShop)
                    )
                }
                2 -> {
                    val bitmapDrawZeroShop = ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.img_map_select_zero,
                        null
                    ) as BitmapDrawable
                    val bitmapZeroShop =
                        Bitmap.createScaledBitmap(bitmapDrawZeroShop.bitmap, 60, 86, false)
                    markerOptions.icon(
                        BitmapDescriptorFactory.fromBitmap(bitmapZeroShop)
                    )
                }
                else -> {
                    return
                }
            }
        }

        override fun setOnClusterItemClickListener(listener: ClusterManager.OnClusterItemClickListener<MyItem>?) {
            super.setOnClusterItemClickListener(listener)
        }
    }

}