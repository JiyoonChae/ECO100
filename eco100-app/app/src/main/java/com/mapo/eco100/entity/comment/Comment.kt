package com.mapo.eco100.entity.comment

import java.io.Serializable

data class Comment (
    val commentId: Long,
    val boardId: Long,
    val writer : String,
    val contents : String,
    val date : String
) : Serializable