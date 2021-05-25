package com.mapo.eco100.views.map

import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class MyItem(
    private val position: LatLng,
    private val title: String?,
    private val snippet: String,
    private val icon: BitmapDescriptor
) :
    ClusterItem {

    override fun getSnippet(): String = snippet

    override fun getTitle(): String? = title

    override fun getPosition(): LatLng = position

    fun getIcon(): BitmapDescriptor = icon

}