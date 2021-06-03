package com.mapo.eco100.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mapo.eco100.R
import com.mapo.eco100.databinding.ItemCommentBinding
import com.mapo.eco100.entity.comment.Comment

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    private var comments: ArrayList<Comment> = ArrayList()

    class CommentViewHolder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateComments(items: ArrayList<Comment>) {
        comments = items
        notifyDataSetChanged()
    }

    fun addComent(item: Comment) {
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