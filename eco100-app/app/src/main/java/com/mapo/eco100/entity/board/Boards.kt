package com.mapo.eco100.entity.board

import java.io.Serializable

data class Boards (
    val boardId : Long,
    val nickname : String,
    var title : String,
    var commentsCnt : Int,
    var likesCnt : Int,
    var date : String
) : Serializable