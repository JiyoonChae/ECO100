package com.mapo.eco100.viewmodel

import android.util.Log
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
            val boards: ArrayList<Boards> = try {
                service.boards(0)
            } catch (e: Exception) {
                arrayListOf()
            }
            boardsLiveData.value = boards

            loadingLiveData.value = false
        }
    }

    fun searchBoards(word:String) {
        loadingLiveData.value = true

        viewModelScope.launch {
            val boards: ArrayList<Boards> = try {
                service.search(word)
            } catch (e: Exception) {
                arrayListOf()
            }
            boardsLiveData.value = boards

            loadingLiveData.value = false
        }
    }

    fun fetchBoardsOrderByLikes() {
        loadingLiveData.value = true

        viewModelScope.launch {
            val boards: ArrayList<Boards> = try {
                service.boardsOrderByLikes(0)
            } catch (e: Exception) {
                arrayListOf()
            }
            boardsLiveData.value = boards

            loadingLiveData.value = false
        }
    }
}