package com.mapo.eco100.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapo.eco100.config.RetrofitObj
import com.mapo.eco100.entity.board.Boards
import com.mapo.eco100.service.BoardService
import kotlinx.coroutines.launch
import java.lang.Exception

class BoardViewModel : ViewModel() {

    val boardsLiveData = MutableLiveData<ArrayList<Boards>>()
    val loadingLiveData = MutableLiveData<Boolean>()

    private val service : BoardService

    init {
        service = RetrofitObj.retrofit.build().create(BoardService::class.java)

        fetchBoards()
    }

    fun refresh(board:Boards) {
        boardsLiveData.value?.let{
            it.add(0,board)
            //it.removeAt(it.size-1)
        }
    }

    fun fetchBoards() {
        loadingLiveData.value = true

        viewModelScope.launch {
            var boards:ArrayList<Boards>? = null
            try {
                boards = service.boards(0)
            } catch (e: Exception) {
                boards = null
            }
            boardsLiveData.value = boards

            loadingLiveData.value = false
        }
    }
}