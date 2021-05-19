package com.mapo.eco100.views.ecobox.navigation

import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mapo.eco100.R
import com.mapo.eco100.config.LocalDataBase.Companion.FAQ_list
import com.mapo.eco100.entity.staticmodel.FAQ

class FaqRecyclerAdapter: RecyclerView.Adapter<FaqRecyclerAdapter.FaqViewHolderClass>() {

    // Item의 클릭 상태를 저장할 array 객체
    private val selectedItems: SparseBooleanArray = SparseBooleanArray()
    var faqList = FAQ_list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolderClass {
        //항목으로 사용할 view객체 생성.
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_ecobox_faq, parent, false)
        val holder = FaqViewHolderClass(itemView)

        return holder
    }


    override fun onBindViewHolder(holder: FaqViewHolderClass, position: Int) {

        holder.qmarkImageView.setImageResource(R.drawable.ic_ecobox_q_mark)
        holder.faqCategoryTextView.text = faqList[position].category
        holder.faqQuestionTextView.text = faqList[position].question
        holder.faqAnswerTextView.text = faqList[position].answer


        holder.faqArrowDownBtn.setOnClickListener {
            if (selectedItems.get(position)) {
                // VISIBLE -> INVISIBLE
                selectedItems.delete(position)
                holder.layoutExpand.visibility = View.GONE
                holder.faqArrowDownBtn.setImageResource(R.drawable.ic_faq_arrow_down)
            } else {
                // INVISIBLE -> VISIBLE
                selectedItems.put(position, true)
                holder.layoutExpand.visibility = View.VISIBLE
                holder.faqArrowDownBtn.setImageResource(R.drawable.ic_faq_arrow_up)
            }
        }

    }

    override fun getItemCount(): Int {
        return faqList.size
    }

    inner class FaqViewHolderClass(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val qmarkImageView = itemView.findViewById<ImageView>(R.id.qmarkImageView)
        val faqCategoryTextView = itemView.findViewById<TextView>(R.id.faqCategoryTextView)
        val faqQuestionTextView = itemView.findViewById<TextView>(R.id.faqQuestionTextView)
        val faqArrowDownBtn = itemView.findViewById<ImageButton>(R.id.faqArrowDownBtn)
        val layoutExpand = itemView.findViewById<LinearLayout>(R.id.layout_expand)
        val faqAnswerTextView = itemView.findViewById<TextView>(R.id.faqAnswerTextView)

    }
    fun onDataChanged(faq:MutableList<FAQ>) {
        faqList.addAll(faq)
        notifyDataSetChanged()
    }

}