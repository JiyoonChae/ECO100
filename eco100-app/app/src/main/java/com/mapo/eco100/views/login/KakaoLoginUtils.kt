package com.mapo.eco100.views.login

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.mapo.eco100.config.NetworkSettings
import com.mapo.eco100.entity.user.JoinRequest
import com.mapo.eco100.entity.user.JoinResponse
import com.mapo.eco100.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KakaoLoginUtils(val context: Context) {
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            //login fail
        } else if (token != null) {
            UserApiClient.instance.me { user, error ->
                if (error != null) {

                } else if (user != null) {
                    NetworkSettings.retrofit.build().create(UserService::class.java).join(
                            JoinRequest(user.id, user.kakaoAccount?.profile?.nickname!!)
                        ).enqueue(object : Callback<JoinResponse> {
                            override fun onResponse(
                                call: Call<JoinResponse>,
                                response: Response<JoinResponse>
                            ) {
                                if (response.isSuccessful) {
                                    val user = response!!.body()
                                    val sp = context.getSharedPreferences("login",Context.MODE_PRIVATE)
                                    val editor: SharedPreferences.Editor = sp.edit()
                                    editor.putLong("userId",user!!.userId)
                                    editor.putString("nickname",user!!.nickname)
                                    editor.apply()
                                    Toast.makeText(context,"로그인 하였습니다.",Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<JoinResponse>, t: Throwable) {

                            }
                        })
                }
            }
        }
    }

    fun login() {
        val userId: Long = context.getSharedPreferences("login", Context.MODE_PRIVATE)
            .getLong("userId",-1)

        if (userId == -1L) {
            UserApiClient.instance.run {
                if (isKakaoTalkLoginAvailable(context)) {
                    loginWithKakaoTalk(context, callback = callback)
                } else {
                    loginWithKakaoAccount(context, callback = callback)
                }
            }
        }
    }

    fun logout() {
        val sp = context.getSharedPreferences("login",Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sp.edit()
        editor.clear()
        editor.apply()
        Toast.makeText(context,"로그아웃 하였습니다.",Toast.LENGTH_SHORT).show()
    }

    fun deleteAccount() {
        if(!isLogin()) {
            Toast.makeText(context, "로그인 되어있어야 합니다.", Toast.LENGTH_SHORT).show()
            return
        }

        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Toast.makeText(context, "회원 탈퇴 실패", Toast.LENGTH_SHORT).show()
            } else {

                NetworkSettings.retrofit.build().create(UserService::class.java).delete(
                    context.getSharedPreferences("login",Context.MODE_PRIVATE).getLong("userId",-1)
                ).enqueue(object : Callback<Void> {
                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {
                        if (response.isSuccessful) {
                            logout()
                            Toast.makeText(context, "회원 탈퇴 성공", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {

                    }
                })
            }
        }
    }

    fun isLogin() : Boolean = context.getSharedPreferences("login",Context.MODE_PRIVATE).getLong("userId",-1) != -1L
}