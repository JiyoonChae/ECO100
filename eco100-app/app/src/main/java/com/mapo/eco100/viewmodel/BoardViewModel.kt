package com.sg.eco100.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sg.eco100.entity.board.Board

class BoardViewModel(boards:ArrayList<Board>) : ViewModel() {

    val recentBoards: MutableLiveData<ArrayList<Board>> by lazy {
        MutableLiveData<ArrayList<Board>>()
    }

    init {
        recentBoards.value = boards
    }

    fun refreshBoards(board:Board) {
        recentBoards.value?.let{
            it.add(0,board)
            //it.removeAt(it.size-1)
        }
    }
}