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
import com.mapo.eco100.views.ecobox.ContentDetailActivity

class ContentRecyclerAdapter
    : RecyclerView.Adapter<ContentRecyclerAdapter.ContentViewHolderClass>() {

    var data = mutableListOf<Contents>()

    // 항목 구성을 위해 사용할 ViewHolder 객체가 필요할 때 호출
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolderClass {
        //항목으로 사용할 view객체 생성.
        val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.item_ecobox_content, parent, false)


        return ContentViewHolderClass(itemView)
    }

    // ViewHolder를 통해 항목을 구성할 때 항목 내의 view 객체에 데이터를 셋팅
    override fun onBindViewHolder(holder: ContentViewHolderClass, position: Int) {
        holder.onBind((data[position]))


    }

    override fun getItemCount(): Int {
        return data.size
    }

    // ViewHolder 클래스
    inner class ContentViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 항목 View 내부의 View 객체의 주소값 담기

        val contentImageView: ImageView = itemView.findViewById(R.id.content_imageView)
        val contentCategoryTextView: TextView = itemView.findViewById(R.id.content_category)
        val contentTitleTextView: TextView = itemView.findViewById(R.id.content_title)

        fun onBind(data : Contents) {
            contentImageView.setImageResource(data.imgRes)
            contentCategoryTextView.text = data.category
            contentTitleTextView.text = data.title
            Glide.with(contentImageView).load(data.imgRes).override(1024).into(contentImageView)

            var categorySt = contentCategoryTextView.text.toString()
            when (categorySt) {
                "[다큐]" -> contentCategoryTextView.setTextColor(ContextCompat.getColor(itemView.context,R.color.primary_color))
                "[환경툰]" -> contentCategoryTextView.setTextColor(ContextCompat.getColor(itemView.context,R.color.content_toon_color))
                "[대외활동]" -> contentCategoryTextView.setTextColor(ContextCompat.getColor(itemView.context,R.color.content_activity_color))
            }

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