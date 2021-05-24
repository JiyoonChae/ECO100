package com.mapo.eco100.views.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mapo.eco100.R
import com.mapo.eco100.entity.staticmodel.GarbageBagShopInfo

class MapShopListAdapter(private val context: BottomSheetShop) :
    RecyclerView.Adapter<MapShopListAdapter.ViewHolder>() {

    var shopListData: MutableList<GarbageBagShopInfo> = ArrayList()
    private lateinit var listener: OnListItemClickListener

    fun setListItemClickListener(listener: OnListItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MapShopListAdapter.ViewHolder {
        val view = LayoutInflater.from(context.activity)
            .inflate(R.layout.bottom_sheet_shop_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MapShopListAdapter.ViewHolder, position: Int) {
        val item = shopListData[position]
        holder.itemView.setOnClickListener {
            listener.onClick(it, position)
        }
        holder.apply {
            bind(item)
        }

    }

    override fun getItemCount(): Int = shopListData.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val shopImg: ImageView = view.findViewById(R.id.shopImg)
        private val shopName: TextView = view.findViewById(R.id.shopName)
        private val shopAddress: TextView = view.findViewById(R.id.shopAddress)

        fun bind(item: GarbageBagShopInfo) {
            shopImg.setImageResource(R.drawable.img_map_shop_logo)
            shopName.text = item.name
            shopAddress.text = item.address1
        }
    }

    interface OnListItemClickListener {
        fun onClick(view: View, position: Int)
    }
}