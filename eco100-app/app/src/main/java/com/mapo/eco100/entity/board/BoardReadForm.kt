package com.mapo.eco100.entity.board

import com.mapo.eco100.entity.comment.Comment
import java.io.Serializable

data class BoardReadForm (
    val boardId : Long,
    val userId: Long,
    val nickname : String,
    val title : String,
    val contents : String,
    val imageUrl : String?,
    val comments : List<Comment>,
    var commentsCnt : Int,
    var likesCnt : Int,
    var canClickLikes : Boolean,
    val date : String
) : Serializable