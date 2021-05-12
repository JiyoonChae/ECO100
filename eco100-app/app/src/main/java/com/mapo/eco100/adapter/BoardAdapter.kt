package com.mapo.eco100.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mapo.eco100.R
import com.mapo.eco100.databinding.ItemBoardBinding
import com.mapo.eco100.entity.board.Boards

class BoardAdapter(
    val onClickItem: (board: Boards) -> Unit
) : RecyclerView.Adapter<BoardAdapter.BoardViewHolder>() {

    private var boards: ArrayList<Boards> = ArrayList()

    class BoardViewHolder(val binding: ItemBoardBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateBoards(items: ArrayList<Boards>) {
        boards = items
        notifyDataSetChanged()
    }

    fun addBoard(item: Boards) {
        boards.add(0, item)
        notifyDataSetChanged()
    }

    fun sortByRecent() {
        boards.sortByDescending { it.board_id }
        notifyDataSetChanged()
    }

    fun sortByLikes() {
        boards.sortByDescending { it.likes_cnt }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BoardViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_board, viewGroup, false)

        return BoardViewHolder(ItemBoardBinding.bind(view))
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        val board: Boards = boards[position]
        holder.binding.apply {
            nickname.text = board.user_nickname
            date.text = board.date
            titleTextView.text = board.title
            commentsCnt.text = board.comments_cnt.toString()
            likesCnt.text = board.likes_cnt.toString()
            root.setOnClickListener {
                onClickItem(board)
            }
        }
    }

    override fun getItemCount() = boards.size
}