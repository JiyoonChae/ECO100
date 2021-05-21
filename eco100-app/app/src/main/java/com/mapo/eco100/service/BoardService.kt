package com.mapo.eco100.service

import com.mapo.eco100.entity.board.*
import com.mapo.eco100.entity.likes.LikesRequestDto
import retrofit2.Call
import retrofit2.http.*

interface BoardService {
    @POST("board/create")
    fun write(
        @Body boardWriteForm: BoardWriteForm
    ) : Call<Boards>

    @GET("board/read/{boardId}/{userId}")
    fun read(
        @Path("boardId") boardId: Long,
        @Path("userId") userId: Long
    ) : Call<BoardReadForm>

    @GET("board/{current}")
    suspend fun boards(
        @Path("current") currentPage: Int
    ) : ArrayList<Boards>

    @GET("board/likes/{current}")
    suspend fun boardsOrderByLikes(
        @Path("current") currentPage: Int
    ) : ArrayList<Boards>

    @GET("board/{current}")
    fun refreshBoards(
        @Path("current") currentPage: Int
    ) : Call<ArrayList<Boards>>

    @GET("board/likes/{current}")
    fun addBoardsOrderByLikes(
        @Path("current") currentPage: Int
    ) : Call<ArrayList<Boards>>

    @PUT("board/update")
    fun modify(
        @Body boardModifyForm: BoardModifyForm
    ) : Call<Void>

    @PATCH("board/likes")
    suspend fun increaseLikes(
        @Body likesRequestDto: LikesRequestDto
    ) : Int

    @DELETE("board/delete/{id}")
    fun delete(
        @Path("id") boardId:Long
    ) : Call<Void>

    @GET("board/search/{word}")
    suspend fun search(
        @Path("word") word:String
    ) : ArrayList<Boards>
}