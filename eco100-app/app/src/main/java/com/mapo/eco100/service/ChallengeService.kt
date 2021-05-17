package com.mapo.eco100.service

import com.mapo.eco100.entity.board.BoardReadForm
import com.mapo.eco100.entity.challenge.ChallengeList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ChallengeService {

    @GET("challenge/{1}")
    fun challengeList() : Call<ChallengeList>
}