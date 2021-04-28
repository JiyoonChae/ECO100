package com.sg.eco100.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mapo.eco100.R
import com.mapo.eco100.databinding.ItemBoardBinding
import com.sg.eco100.entity.board.Board

class BoardAdapter(
    private var boards: ArrayList<Board>,
    val onClickItem: (board:Board) -> Unit
) : RecyclerView.Adapter<BoardAdapter.BoardViewHolder>() {

    class BoardViewHolder(val binding: ItemBoardBinding) : RecyclerView.ViewHolder(binding.root)

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

    override fun getItemCount(): Int {
        return boards.size
    }

    fun setData(newData: ArrayList<Board>) {
        newData.forEach {
            Log.d("newData",it.title)
        }
        boards = newData
        boards.forEach {
            Log.d("boards",it.title)
        }
        //반드시 입력하여 데이터가 변화했음을 리싸이클러 뷰에게 알려준다
        notifyDataSetChanged()
    }
}