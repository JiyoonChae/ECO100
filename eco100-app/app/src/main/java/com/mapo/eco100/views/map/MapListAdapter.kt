package com.mapo.eco100.views.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mapo.eco100.R

class MapListAdapter(private val context: BottomSheet) : RecyclerView.Adapter<MapListAdapter.ViewHolder>(){

    var listData = mutableListOf<ShopData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapListAdapter.ViewHolder {
        val view = LayoutInflater.from(context.activity).inflate(R.layout.bottom_sheet_map_list_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MapListAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val shopImg : ImageView = view.findViewById(R.id.shopImg)
        private val shopName : TextView = view.findViewById(R.id.shopName)
        private val shopDescription : TextView = view.findViewById(R.id.shopDescription)
        private val shopAddress : TextView = view.findViewById(R.id.shopAddress)

        fun bind(item: ShopData) {
            shopName.text = item.name
            shopDescription.text = item.running_info
            shopAddress.text = item.address
        }
    }
}