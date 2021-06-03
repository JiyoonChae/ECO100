package com.mapo.eco100.entity.comment

data class CommentRequest(
    val boardId: Long,
    val contents: String,
    val userId: Long
)
