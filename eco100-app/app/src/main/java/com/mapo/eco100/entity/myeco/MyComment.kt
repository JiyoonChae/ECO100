package com.mapo.eco100.entity.myeco

import java.io.Serializable

data class MyComment(
    val commentId: Long,
    val boardId: Long,
    val writer: String,
    val date: String,
    val contents: String
):Serializable