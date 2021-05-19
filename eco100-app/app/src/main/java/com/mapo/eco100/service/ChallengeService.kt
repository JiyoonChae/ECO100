package com.mapo.eco100.service

import com.mapo.eco100.entity.board.BoardReadForm
import com.mapo.eco100.entity.board.BoardWriteForm
import com.mapo.eco100.entity.board.Boards
import com.mapo.eco100.entity.challenge.ChallengeList
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ChallengeService {

    @GET("challenge/{userId}")
    fun challengeList( @Path("userId") id: Long) : Call<ChallengeList>

    @Multipart
    @POST("/challenge/create")
    fun newChallenge(
        @Path("order_idx") orderIdx: Int,
        @Part file: MultipartBody.Part	// 이 부분이 우리가 넣을 데이터!!
    ): Call<FileResponse>
    fun write(
        @Body boardWriteForm: BoardWriteForm
    ) : Call<Boards>
}