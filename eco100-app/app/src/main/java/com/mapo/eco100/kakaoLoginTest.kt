package com.mapo.eco100

import android.util.Log
import android.widget.Toast
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
/*
val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
    if (error != null) {
        //login fail
    }
    else if (token != null) {
        Toast.makeText(parentContext,"로그인 성공", Toast.LENGTH_SHORT).show()
    }
}

binding.button2.setOnClickListener {
    UserApiClient.instance.run {
        if (isKakaoTalkLoginAvailable(parentContext)) {
            loginWithKakaoTalk(parentContext,callback = callback)
        } else {
            loginWithKakaoAccount(parentContext,callback = callback)
        }
    }
}

binding.button3.setOnClickListener {
    UserApiClient.instance.unlink { error ->
        if (error != null) {
            Toast.makeText(parentContext,"회원 탈퇴 실패", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(parentContext,"회원 탈퇴 성공", Toast.LENGTH_SHORT).show()
        }
    }
}

binding.button4.setOnClickListener {
    UserApiClient.instance.me { user, error ->
        if (error != null) {
            Toast.makeText(parentContext,"로그인 되어 있지 않음", Toast.LENGTH_SHORT).show()
        }
        else if (user != null) {
            Log.d("UserInformation",
                """
                            회원번호: ${user.id}
                           \n이메일: ${user.kakaoAccount?.email}
                           \n닉네임: ${user.kakaoAccount?.profile?.nickname}
                           \n프로필: ${user.kakaoAccount?.profile?.thumbnailImageUrl}
                            """
            )
        }
    }
}
 */
