package com.sg.eco100.entity.board

import java.io.Serializable


data class Board (
    val board_id : Long,
    val user_nickname : String,
    var title : String,
    var contents : String,
    var image_url : String?,
    var comments_cnt : Int,
    var likes_cnt : Int
) : Serializable