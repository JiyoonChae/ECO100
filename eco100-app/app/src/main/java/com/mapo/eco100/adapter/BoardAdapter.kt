package com.mapo.eco100.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
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

    fun sortByRecent() {
        boards.sortByDescending { it.boardId }
        notifyDataSetChanged()
    }

    fun sortByLikes() {
        boards.sortByDescending { it.likesCnt }
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
            nickname.text = board.nickname
            date.text = board.date
            titleTextView.text = board.title
            commentsCnt.text = board.commentsCnt.toString()
            likesCnt.text = board.likesCnt.toString()
            root.setOnClickListener {
                onClickItem(board)
            }
        }
    }

    override fun getItemCount() = boards.size

    fun refreshBoards(newBoards: ArrayList<Boards>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun getOldListSize() = boards.size
            override fun getNewListSize() = 5
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = false
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) = false
        }
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.convertOldPositionToNew(0)
        boards = newBoards
        diffResult.dispatchUpdatesTo(this)
    }

    fun addBoards(item: ArrayList<Boards>) {
        boards.addAll(item)
        notifyDataSetChanged()
    }

    //fun isEmpty() = boards.isEmpty()
}