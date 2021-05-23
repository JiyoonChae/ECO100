package com.mapo.eco100.entity.board

import java.io.Serializable

data class Boards (
    val board_id : Long,
    val user_nickname : String,
    var title : String,
    var comments_cnt : Int,
    var likes_cnt : Int,
    var date : String
) : Serializable