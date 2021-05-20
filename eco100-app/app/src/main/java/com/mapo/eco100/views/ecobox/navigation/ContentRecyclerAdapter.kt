package com.mapo.eco100.views.ecobox.navigation

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mapo.eco100.R
import com.mapo.eco100.views.ecobox.RecycleGuidePaperActivity

class ContentRecyclerAdapter
    : RecyclerView.Adapter<ContentRecyclerAdapter.ContentViewHolderClass>() {

    var imgRes = intArrayOf(
        R.drawable.ecobox_content_documentary2, R.drawable.ecobox_content_webtoon1,
        R.drawable.ecobox_content_documentary1, R.drawable.ecobox_content_activity2,
        R.drawable.ecobox_content_activity1, R.drawable.ecobox_content_webtoon2
    )
    var category = arrayOf("[다큐]", "[환경툰]", "[다큐]", "[대외활동]", "[대외활동]", "[환경툰]")
    var title = arrayOf(
        "분리수거가 당신에게 가르쳐 주지 않는 것",
        "먹깨비툰 1화 '배달용기' 편",
        "플라스틱 없이 살아보기 part 1",
        "제9회 지구사랑 환경 그림그리기 공모전",
        "2021 한국석유공사 기업광고 포스터 공모전",
        "먹깨비즈의 슬기로운 집콕생활'분리배출숏툰 2화 - 음식"
    )

    // 항목 구성을 위해 사용할 ViewHolder 객체가 필요할 때 호출
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolderClass {
        //항목으로 사용할 view객체 생성.
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_ecobox_content, parent, false)


        return ContentViewHolderClass(itemView)
    }

    // ViewHolder를 통해 항목을 구성할 때 항목 내의 view 객체에 데이터를 셋팅
    override fun onBindViewHolder(holder: ContentViewHolderClass, position: Int) {

        holder.contentImageView.setImageResource(imgRes[position])
        holder.contentCategoryTextView.text = category[position]
        holder.contentTitleTextView.text = title[position]

//        holder.itemView.setOnClickListener{
//            val intent = Intent(context, ContentDetailActivity::class.java).apply {
//                putExtra("data", item)
//                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            }.run { context.startActivity(this) }
//        }

    }

    override fun getItemCount(): Int {
        return title.size
    }

    // ViewHolder 클래스
    inner class ContentViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // 항목 View 내부의 View 객체의 주소값 담기

        val contentImageView: ImageView = itemView.findViewById(R.id.content_imageView)
        val contentCategoryTextView: TextView = itemView.findViewById(R.id.content_category)
        val contentTitleTextView: TextView = itemView.findViewById(R.id.content_title)


    }
}