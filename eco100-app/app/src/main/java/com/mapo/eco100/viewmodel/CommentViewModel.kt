package com.mapo.eco100.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapo.eco100.config.RetrofitObj
import com.mapo.eco100.entity.comment.Comment
import kotlinx.coroutines.launch

class CommentViewModel : ViewModel() {

    val commentsLiveData = MutableLiveData<ArrayList<Comment>>()

    init {
        fetchBoards()
    }

    fun refresh(comment:Comment) {
        commentsLiveData.value?.let{
            it.add(0,comment)
        }
    }

    fun fetchBoards() {
        viewModelScope.launch {
            val boards = service.boards(0)
            boardsLiveData.value = boards

            loadingLiveData.value = false
        }
    }
}