package com.mapo.eco100.service

import com.mapo.eco100.entity.board.BoardReadForm
import com.mapo.eco100.entity.board.BoardModifyForm
import com.mapo.eco100.entity.board.BoardWriteForm
import com.mapo.eco100.entity.board.Boards
import com.mapo.eco100.entity.likes.LikesRequestDto
import retrofit2.Call
import retrofit2.http.*

interface BoardService {
    @POST("board/create")
    fun write(
        @Body boardWriteForm: BoardWriteForm
    ) : Call<Boards>

    @GET("board/read/{id}")
    fun read(
        @Path("id") id: Long
    ) : Call<BoardReadForm>

    @GET("board/{current}")
    suspend fun boards(
        @Path("current") currentPage: Int
    ) : ArrayList<Boards>

    @GET("board/{current}")
    fun refreshBoards(
        @Path("current") currentPage: Int
    ) : Call<ArrayList<Boards>>


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