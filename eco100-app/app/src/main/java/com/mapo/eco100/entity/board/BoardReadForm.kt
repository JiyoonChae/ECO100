package com.mapo.eco100.entity.board

import com.mapo.eco100.entity.comment.Comment
import java.io.Serializable

data class BoardReadForm (
    val board_id : Long,
    val user_nickname : String,
    val title : String,
    val contents : String,
    val image_url : String?,
    var comments_cnt : Int,
    var likes_cnt : Int,
    val comments : List<Comment>,
    val date : String
) : Serializable