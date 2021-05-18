package com.mapo.eco100.service

import com.mapo.eco100.entity.comment.Comment
import com.mapo.eco100.entity.comment.CommentRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentService {
    @POST("board/comment/create")
    fun write(
        @Body commentRequest: CommentRequest
    ) : Call<Void>

    @GET("board/comment/{boardId}")
    suspend fun getComments(
        @Path("boardId") boardId : Long
    ) : List<Comment>
}