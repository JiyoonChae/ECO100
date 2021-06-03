package com.mapo.eco100.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapo.eco100.entity.comment.Comment
import com.mapo.eco100.service.BoardService
import com.mapo.eco100.config.NetworkSettings
import com.mapo.eco100.entity.likes.LikesRequestDto
import com.mapo.eco100.service.CommentService
import kotlinx.coroutines.launch
import java.lang.Exception

class ShowBoardViewModel : ViewModel() {

    val numOfLikesLiveData = MutableLiveData<Int>()
    val commentsLiveData = MutableLiveData<List<Comment>>()
    val loadingLiveData = MutableLiveData<Boolean>()
    var context : Context? = null

    fun set(context: Context) {
        this.context = context
    }

    fun fetchComments(boardId:Long) {
        loadingLiveData.value = true
        viewModelScope.launch {
            commentsLiveData.value = try {
                NetworkSettings.retrofit.build().create(CommentService::class.java).getComments(boardId)
            } catch (e:Exception) {
                emptyList()
            }

            loadingLiveData.value = false
        }
    }

    fun fetchNumOfLikes(boardId: Long) {
        viewModelScope.launch {
            numOfLikesLiveData.value = try {
                NetworkSettings.retrofit.build().create(BoardService::class.java).increaseLikes(
                    LikesRequestDto(context!!.getSharedPreferences("login",Context.MODE_PRIVATE).getLong("userId",-1),boardId)
                )
            } catch (e:Exception) {
                Log.e("fetchNumOfLikes()","error: $e")
                0
            }
        }
    }
}