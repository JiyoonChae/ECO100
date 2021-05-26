package com.mapo.eco100.entity.myeco

import java.io.Serializable


data class MyBoard(
    val boardId: Long,
    val nickname: String,
    val title: String,
    val date: String
):Serializable