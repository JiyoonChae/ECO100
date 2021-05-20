package com.mapo.eco100.views.map

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mapo.eco100.R
import com.mapo.eco100.entity.staticmodel.ZeroShop
import java.lang.ClassCastException
import kotlin.contracts.contract

class MapListAdapter(private val context: BottomSheet) :
    RecyclerView.Adapter<MapListAdapter.ViewHolder>() {

    var listData: MutableList<ZeroShop> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapListAdapter.ViewHolder {
        val view = LayoutInflater.from(context.activity)
            .inflate(R.layout.bottom_sheet_map_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MapListAdapter.ViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val shopImg: ImageView = view.findViewById(R.id.shopImg)
        private val shopName: TextView = view.findViewById(R.id.shopName)
        private val shopAddress: TextView = view.findViewById(R.id.shopAddress)


        fun bind(item: ZeroShop) {
            if (item.logoUrl == null) {
                shopImg.setImageResource(R.drawable.img_map_no_logo)
            } else {
                Glide.with(context).load(item.logoUrl).into(shopImg)
            }
            shopName.text = item.name
            shopAddress.text = item.address

            itemView.setOnClickListener{
                val position : Int = adapterPosition
                MapViewFragment().onClickItem(position)
            }
        }

    }

}