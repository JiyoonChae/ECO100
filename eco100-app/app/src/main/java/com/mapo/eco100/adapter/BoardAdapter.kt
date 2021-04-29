package com.mapo.eco100.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mapo.eco100.R
import com.mapo.eco100.databinding.ItemBoardBinding
import com.mapo.eco100.entity.board.Board

class BoardAdapter(
    val onClickItem: (board:Board) -> Unit
) : RecyclerView.Adapter<BoardAdapter.BoardViewHolder>() {

    private var boards:ArrayList<Board> = ArrayList()

    class BoardViewHolder(val binding: ItemBoardBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateBoards(items: ArrayList<Board>) {
        boards = items
        notifyDataSetChanged()
    }

    fun addBoard(item: Board) {
        boards.add(0,item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BoardViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_board,viewGroup,false)

        return BoardViewHolder(ItemBoardBinding.bind(view))
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        val board : Board = boards[position]
        holder.binding.apply {
            titleTextView.text = board.title
            contentTextView.text = board.contents
            root.setOnClickListener {
                onClickItem(board)
            }
        }
    }

    override fun getItemCount() = boards.size
}