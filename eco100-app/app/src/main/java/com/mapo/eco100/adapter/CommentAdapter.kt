package com.mapo.eco100.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mapo.eco100.R
import com.mapo.eco100.databinding.ItemCommentBinding
import com.mapo.eco100.entity.comment.Comment

class CommentAdapter(comments:List<Comment>,val onClickDeleteBtn : (comment:Comment) -> Unit, val context:Context) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    private var comments: List<Comment>

    init {
        this.comments = comments
    }

    class CommentViewHolder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateComments(items: List<Comment>) {
        comments = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_comment, viewGroup, false)

        return CommentViewHolder(ItemCommentBinding.bind(view))
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment: Comment = comments[position]
        holder.binding.apply {
            commentNickname.text = comment.writer
            commentDate.text = comment.date
            commentContents.text = comment.contents
            deleteBtn.setOnClickListener {
                onClickDeleteBtn(comment)
            }
            if(comment.writer != context.getSharedPreferences("login",Context.MODE_PRIVATE)
                    .getString("nickname","")
            ) {
                deleteBtn.visibility = View.GONE
            }
        }
    }

    override fun getItemCount() = comments.size
}