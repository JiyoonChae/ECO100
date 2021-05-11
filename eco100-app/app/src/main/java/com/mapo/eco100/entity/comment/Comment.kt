package com.mapo.eco100.entity.comment

import java.io.Serializable

data class Comment (
    val user_nickname : String,
    var contents : String,
    var date : String
) : Serializable