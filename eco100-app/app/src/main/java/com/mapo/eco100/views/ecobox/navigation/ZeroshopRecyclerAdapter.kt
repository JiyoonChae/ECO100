package com.mapo.eco100.views.ecobox.navigation

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mapo.eco100.R
import com.mapo.eco100.entity.staticmodel.Contents
import com.mapo.eco100.entity.staticmodel.ZeroShop
import com.mapo.eco100.views.ecobox.ContentDetailActivity

class ZeroshopRecyclerAdapter
    : RecyclerView.Adapter<ZeroshopRecyclerAdapter.ZeroshopViewHolderClass>() {

    var data = mutableListOf<ZeroShop>()

    // 항목 구성을 위해 사용할 ViewHolder 객체가 필요할 때 호출
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZeroshopViewHolderClass {
        //항목으로 사용할 view객체 생성.
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_ecobox_zeroshop, parent, false)

        return ZeroshopViewHolderClass(itemView)
    }

    // ViewHolder를 통해 항목을 구성할 때 항목 내의 view 객체에 데이터를 셋팅
    override fun onBindViewHolder(holder: ZeroshopViewHolderClass, position: Int) {
        holder.onBind((data[position]))


    }

    override fun getItemCount(): Int {
        return data.size
    }

    // ViewHolder 클래스
    inner class ZeroshopViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 항목 View 내부의 View 객체의 주소값 담기

        val zeroshopImageView: ImageView = itemView.findViewById(R.id.zeroshop_imageView)
        val zeroshopNameTextView: TextView = itemView.findViewById(R.id.zeroshop_name)
        val zeroshopInfoTextView: TextView = itemView.findViewById(R.id.zeroshop_info)

        fun onBind(data : ZeroShop) {

            zeroshopNameTextView.text = data.name
            zeroshopInfoTextView.text = data.name



            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ContentDetailActivity::class.java)
                intent.apply {
                    this.putExtra("category",data.category) // 데이터 넣기
                    this.putExtra("title",data.title)
                    this.putExtra("imgRes",data.imgRes)
                    this.putExtra("detail",data.detail)
                    this.putExtra("webUrl",data.webUrl)
                }
                ContextCompat.startActivity(itemView.context, intent, null)
            }

        }



    }
}