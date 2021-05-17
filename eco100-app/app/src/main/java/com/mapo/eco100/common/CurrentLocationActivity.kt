package com.mapo.eco100.common

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.mapo.eco100.R


class CurrentLocationActivity : AppCompatActivity() {
    /**
     * Fused Location Provider Api 에서
     * 위치 업데이트를위한 서비스 품질등 다양한요청을
     * 설정하는데 사용하는 객체.
     */
    private lateinit var mLocationRequest: LocationRequest

    /**
     * 현재위치정보를 나타내는 객체
     */
    private lateinit var mCurrentLocation: Location

    /**
     * 현재 위치제공자(Provider)와 상호작용하는 진입점
     */
    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    /**
     * 현재 단말기에 설정된 위치 Provider
     */
    private lateinit var currentProvider: String
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)

        /**
         * 인텐트로 넘어온 현재 설정된 위치제공자를 가져온다
         */
        currentProvider = intent.getStringExtra("provider")!!

        /*
         * 6.0 이상일 경우 위치퍼미션에 대한 사용자 허락을 받는다
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkMyPermissionLocation()
        } else {
            initGoogleMapLocation()
        }
    }

    private val permissionListener = object : PermissionListener {
        override fun onPermissionGranted() {
            initGoogleMapLocation()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            Toast.makeText(applicationContext, "위치제공 허락을 해야 앱이 정상적으로 작동합니다", Toast.LENGTH_SHORT)
                .show()
            finish()
        }
    }

    private fun checkMyPermissionLocation() {
        TedPermission.with(this)
            .setPermissionListener(permissionListener)
            .setRationaleMessage("맵을 사용하기 위해서는 위치제공 허락이 필요합니다")
            .setPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            .check()
    }

    /**
     * 위치 이벤트에 대한 콜백을 제공.
     * 단말기위치정보가 update 되면 자동으로 호출
     * Fused Location Provider API 에 등록된
     * 위치알림을 수신하는 데 사용
     */
    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        /**
         *  성공적으로 위치정보와 넘어왔을때를 동작하는 Call back 함수
         */
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            mCurrentLocation = locationResult.locations[0]
            val options = MarkerOptions()
            options.position(LatLng(mCurrentLocation.latitude, mCurrentLocation.longitude))
            val icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)
            options.icon(icon)
            options.title("내 현재 위치")
            val marker = mMap.addMarker(options)

            /*
             * 단말기 현재 위치로 이동한다
             */
            mMap.animateCamera(
                CameraUpdateFactory.newLatLngZoom(marker!!.position, 16f)
            )
            /**
             * 지속적으로 위치정보를 받으려면
             * mLocationRequest.setNumUpdates(1)를 추가하고
             * 아래의 코드 주석을 푼다
             */
            //mFusedLocationClient.removeLocationUpdates(mLocationCallback)
        }

        /**
         * 현재 콜백이 동작가능한지에 대한 여부
         */
        override fun onLocationAvailability(availability: LocationAvailability) {
            //boolean isLocation = availability.isLocationAvailable()
        }
    }

    /**
     * 현재 위치를 알아내는 코드구성
     */
    @SuppressLint("MissingPermission")
    fun initGoogleMapLocation() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment
        /*
         * 비동기 방식으로 GoogleMap 초기설정을 진행한다
         */
        mapFragment.getMapAsync { googleMap: GoogleMap ->
            mMap = googleMap
            mMap.uiSettings.isZoomControlsEnabled = true
            val options = MarkerOptions()

            /*
             * 처음 위치를 마포구청으로 세팅
             */
            options.position(LatLng(37.566168, 126.901609))

            /*
             * 마커 등록
             */
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN))
            val marker = mMap.addMarker(options)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker!!.position, 16f))
            /**
             * FusedLocationProviderApi 에서
             * 위치 업데이트를위한 서비스 품질등 다양한요청을
             * 설정하는데 사용하는 데이터객체인
             * LocationRequest 를 획득
             */
            mLocationRequest = LocationRequest.create().apply {
                priority =
                    if (currentProvider.equals(LocationManager.GPS_PROVIDER, ignoreCase = true)) {
                        LocationRequest.PRIORITY_HIGH_ACCURACY
                    } else {
                        //배터리와 정확도의 밸런스를 고려하여 위치정보를 획득(정확도 다소 높음)
                        LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
                    }
                interval = 100 //위치가 update 되는 주기
                fastestInterval = 50 //위치 획득 후 update 되는 주기
                numUpdates = 1 //update 되는 횟수(여기선 1번만 설정)

                priority =
                    if (currentProvider.equals(LocationManager.GPS_PROVIDER, ignoreCase = true)) {
                        LocationRequest.PRIORITY_HIGH_ACCURACY
                    } else {
                        //배터리와 정확도의 밸런스를 고려하여 위치정보를 획득(정확도 다소 높음)
                        LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
                    }
            }
            /**
             * 위치서비스 설정 정보를 저장하기 위한 빌더객체획득
             */
            val builder = LocationSettingsRequest.Builder().apply {
                /**
                 * 현재 위치정보 Setting 정보가 저장된 LocationRequest
                 * 객체를 등록
                 */
                addLocationRequest(mLocationRequest)
            }

            /**
             * 위치정보 요청을 수행하기 위해 단말기에서
             * 관련 시스템 설정(Gps,Network)이 활성화되었는지 확인하는 클래스인
             * SettingClient 를 획득한다
             */
            val mSettingsClient = LocationServices.getSettingsClient(this)

            /**
             * 위치 서비스 유형을 저장하고
             * 위치 설정에도 사용하기위해
             * LocationSettingsRequest 객체를 획득
             */
            val mLocationSettingsRequest = builder.build()
            val locationResponse = mSettingsClient.checkLocationSettings(mLocationSettingsRequest)

            /**
             * 현재 위치제공자(Provider)와 상호작용하는 진입점인
             * FusedLocationProviderClient 객체를 획득
             */
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

            /**
             * 정상적으로 위치정보가 설정되었다면
             * 위치업데이트를 요구하고, 설정이 잘못되었다면
             * onFailure 를 처리한다
             */
            with(locationResponse) {
                addOnSuccessListener {
                    Toast.makeText(applicationContext, "위치 받기 성공", Toast.LENGTH_SHORT).show()
                    mFusedLocationClient.requestLocationUpdates(
                        mLocationRequest,
                        mLocationCallback,
                        Looper.getMainLooper()
                    )
                }
                addOnFailureListener { e ->
                    val exception = e as ApiException
                    Toast.makeText(applicationContext, "위치 받기 실패: $exception", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    /**
     * 현재 화면을 나갈때 반드시 등록된
     * 위치정보 알림을 제거
     */
    override fun onStop() {
        super.onStop()
        if (this::mFusedLocationClient.isInitialized) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback)
        }
    }
}