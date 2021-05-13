package com.mapo.eco100.views.map

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mapo.eco100.R
import com.mapo.eco100.databinding.FragmentMapBinding

class MapViewFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMyLocationClickListener,
    GoogleMap.OnMyLocationButtonClickListener {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding.root
    }

    companion object {
        fun newInstance(): MapViewFragment {
            return MapViewFragment()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val myLocation = LatLng(37.564009984338014, 126.90923531625434)
        val initCam: CameraPosition = CameraPosition.builder().target(myLocation).zoom(15f).build()

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(initCam))
        googleMap.addMarker(
            MarkerOptions().position(myLocation).title("회사").snippet("돈버는 곳")
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation))
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10f))
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

}