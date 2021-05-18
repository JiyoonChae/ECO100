package com.mapo.eco100.views.map

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mapo.eco100.R
import com.mapo.eco100.entity.staticmodel.ZeroShop
import java.net.URL

class MapListAdapter(private val context: BottomSheet) : RecyclerView.Adapter<MapListAdapter.ViewHolder>(){

    var listData = mutableListOf<ZeroShop>()
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
        private val shopAddress : TextView = view.findViewById(R.id.shopAddress)

        fun bind(item: ZeroShop) {
            //val url = URL(item.logoUrl)
            //shopImg.setImageBitmap(BitmapFactory.decodeStream(url.openConnection().getInputStream()))
            shopName.text = item.name
            shopAddress.text = item.address
        }
    }
}