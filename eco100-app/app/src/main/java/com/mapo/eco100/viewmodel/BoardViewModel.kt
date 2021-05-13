package com.mapo.eco100.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapo.eco100.entity.board.Boards
import com.mapo.eco100.service.BoardService
import com.mapo.eco100.config.NetworkSettings
import kotlinx.coroutines.launch
import java.lang.Exception

class BoardViewModel : ViewModel() {

    val boardsLiveData = MutableLiveData<ArrayList<Boards>>()
    val loadingLiveData = MutableLiveData<Boolean>()

    private val service : BoardService

    init {
        service = NetworkSettings.retrofit.build().create(BoardService::class.java)

        fetchBoards()
    }

    fun fetchBoards() {
        loadingLiveData.value = true

        viewModelScope.launch {
            var boards:ArrayList<Boards>? = null
            boards = try {
                service.boards(0)
            } catch (e: Exception) {
                null
            }
            boardsLiveData.value = boards

            loadingLiveData.value = false
        }
    }
}