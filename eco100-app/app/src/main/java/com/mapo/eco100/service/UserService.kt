package com.mapo.eco100.service

import com.mapo.eco100.entity.user.JoinRequest
import com.mapo.eco100.entity.user.JoinResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @POST("user/create")
    fun join(
        @Body joinRequest: JoinRequest
    ) : Call<JoinResponse>

    @DELETE("user/delete/{userId}")
    fun delete(
        @Path("userId") userId:Long
    ) : Call<Void>
}