package com.mapo.eco100.entity.user

import java.io.Serializable

data class JoinResponse(
    val userId: Long,
    val nickname: String
) : Serializable