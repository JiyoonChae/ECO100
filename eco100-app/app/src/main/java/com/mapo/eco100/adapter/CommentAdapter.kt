package com.mapo.eco100.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mapo.eco100.R
import com.mapo.eco100.databinding.ItemCommentBinding
import com.mapo.eco100.entity.comment.Comment

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    private var comments: ArrayList<Comment> = ArrayList()

    init {
        comments.add(Comment("홍길동","test 댓글","2021-05-11"))
        comments.add(Comment("김아무개","test 댓글2","2021-05-12"))
        comments.add(Comment("김삿갓","test 댓글3","2021-05-13"))
    }

    class CommentViewHolder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateComments(items: ArrayList<Comment>) {
        comments = items
        notifyDataSetChanged()
    }

    fun addComment(item: Comment) {
        comments.add(0, item)
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
            commentNickname.text = comment.user_nickname
            commentDate.text = comment.date
            commentContents.text = comment.contents
        }
    }

    override fun getItemCount() = comments.size
}