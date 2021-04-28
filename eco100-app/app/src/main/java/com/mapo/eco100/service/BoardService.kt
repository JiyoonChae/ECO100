package com.mapo.eco100.service

import com.sg.eco100.entity.board.Board
import com.sg.eco100.entity.board.BoardModifyForm
import com.sg.eco100.entity.board.BoardWriteForm
import com.sg.eco100.entity.likes.LikesRequestDto
import retrofit2.Call
import retrofit2.http.*

interface BoardService {
    @POST("board/create")
    fun write(
        @Body boardWriteForm: BoardWriteForm
    ) : Call<Board>

    @GET("board/read/{id}")
    fun read(
        @Path("id") id: Long
    ) : Call<Board>

    @GET("board/{current}")
    fun boards(
        @Path("current") currentPage: Int
    ) : Call<ArrayList<Board>>

    @PUT("board/update")
    fun modify(
        @Body boardModifyForm: BoardModifyForm
    ) : Call<Void>

    @PATCH("board/likes")
    fun increaseLikes(
        @Body likesRequestDto: LikesRequestDto
    ) : Call<Boolean>

    @DELETE("board/delete/{id}")
    fun delete(
        @Path("id") id:Long
    ) : Call<Void>
}