package com.mapo.eco100.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapo.eco100.entity.comment.Comment
import com.mapo.eco100.service.BoardService
import com.mapo.eco100.config.NetworkSettings
import com.mapo.eco100.service.CommentService
import kotlinx.coroutines.launch
import java.lang.Exception

class ShowBoardViewModel : ViewModel() {

    val likesAndCommentsCntLiveData = MutableLiveData<LikesAndCommentsCnt>()
    val commentsLiveData = MutableLiveData<List<Comment>>()
    val loadingLiveData = MutableLiveData<Boolean>()

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

    fun fetchLikesAndCommentsCnt(boardId: Long) {
//        loadingLiveData.value = true
//        viewModelScope.launch {
//            likesAndCommentsCntLiveData.value = try {
//                val board = NetworkSettings.retrofit.build().create(BoardService::class.java).read(boardId,1).en
//                LikesAndCommentsCnt(board.)
//            } catch (e:Exception) {
//                emptyList()
//            }
//
//            loadingLiveData.value = false
//        }
    }

    class LikesAndCommentsCnt(val likesCnt:Int, val commentsCnt:Int)
}