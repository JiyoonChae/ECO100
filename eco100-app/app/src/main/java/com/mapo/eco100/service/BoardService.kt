package com.mapo.eco100.service

import com.mapo.eco100.entity.board.BoardReadForm
import com.mapo.eco100.entity.board.BoardModifyForm
import com.mapo.eco100.entity.board.BoardWriteForm
import com.mapo.eco100.entity.board.Boards
import com.mapo.eco100.entity.likes.LikesRequestDto
import com.mapo.eco100.entity.myeco.MyBoardList
import com.mapo.eco100.entity.myeco.MyCommentList
import retrofit2.Call
import retrofit2.http.*

interface BoardService {
    @POST("board/create")
    fun write(
        @Body boardWriteForm: BoardWriteForm
    ) : Call<Boards>

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
        @Path("id") id:Long
    ) : Call<Void>

    @GET("board/search/{word}")
    suspend fun search(
        @Path("word") word:String
    ) : ArrayList<Boards>

    //내가 쓴 글 조회
    @GET("board/read/{userId}")
    fun readAll(
        @Path("userId")id : Long
    ):Call<MyBoardList>

    //내가 쓴 댓글 조회
    @GET("board/comment/all/{userId}")
    fun commentAll(
        @Path("userId")id: Long
    ):Call<MyCommentList>

    //글 1개 읽기
    @GET("board/read/{boardId}/{userId}")
    fun readOne(
        @Path("boardId")id: Long,
        @Path("userId")userId: Long
    ):Call<BoardReadForm>

    @GET("user/badgelist/{userId}")
    fun getMyBadge(
        @Path("userId")id: Long
    ):Call<ArrayList<Boolean>>

    @GET("board/read/{boardId}/{userId}")
    fun read(
        @Path("boardId") boardId: Long,
        @Path("userId") userId: Long
    ) : Call<BoardReadForm>
}