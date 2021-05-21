package com.mapo.eco100.service

import com.mapo.eco100.entity.board.BoardReadForm
import com.mapo.eco100.entity.board.BoardWriteForm
import com.mapo.eco100.entity.board.Boards
import com.mapo.eco100.entity.challenge.ChallengeList
import com.mapo.eco100.entity.challenge.ChallengePost
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ChallengeService {

    @GET("challenge/{userId}")
    fun challengeList( @Path("userId") id: Long) : Call<ChallengeList>

    @Multipart
    @POST("/challenge/create")
    fun challengeUpload(
        @Part ("userId")userId: String,
        @Part ("challengeId")challengeId: String,
        @Part ("contents")contents: String,
        @Part image: MultipartBody.Part
    ): Call<ChallengePost>

}