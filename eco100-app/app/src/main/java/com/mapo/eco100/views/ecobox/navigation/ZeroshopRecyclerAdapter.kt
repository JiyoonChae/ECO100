package com.mapo.eco100.views.ecobox.navigation

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mapo.eco100.R
import com.mapo.eco100.entity.staticmodel.ZeroShop
import com.mapo.eco100.views.ecobox.ZeroshopDetailActivity

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

        val zeroshopLogoImageView: ImageView = itemView.findViewById(R.id.zeroshop_logo_imageView)
        val zeroshopNameTextView: TextView = itemView.findViewById(R.id.zeroshop_name)
        val zeroshopInfoTextView: TextView = itemView.findViewById(R.id.zeroshop_info)

        fun onBind(data : ZeroShop) {
            if (data.logoUrl == null) {
                zeroshopLogoImageView.setImageResource(R.drawable.ecobox_zeroshop_no_img)
            } else {
                Glide.with(zeroshopLogoImageView).load(data.logoUrl).override(166).into(zeroshopLogoImageView)
            }

            zeroshopNameTextView.text = data.name
            zeroshopInfoTextView.text = data.detailInfo


            itemView.setOnClickListener {

                val intent = Intent(itemView.context, ZeroshopDetailActivity::class.java)
                intent.apply {
                    this.putExtra("name",data.name) // 데이터 넣기
                    this.putExtra("detailInfo",data.detailInfo)
                    this.putExtra("imgUrl",data.imgUrl)
                    this.putExtra("phoneNum",data.phoneNum)
                    this.putExtra("adress",data.address)
                    this.putExtra("runInfo",data.runningInfo)
                    this.putExtra("webUrl",data.webUrl)
                    this.putExtra("lat",data.latitude.toDouble())
                    this.putExtra("long",data.longitude.toDouble())

                    Log.d("ecobox", "data >> ${data.latitude} , ${data.longitude}")

                }


                ContextCompat.startActivity(itemView.context, intent, null)
            }

        }



    }
}