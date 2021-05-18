package com.mapo.eco100.entity.challenge

import java.io.Serializable
import java.sql.Date
import java.util.*


data class ChallengePost (
    val challenge_post_id :Long,
    val created_at:Date,
    val image_url:String,
    val contents : String,
    val challenge_id:Long,
    val user_id:Long
) :Serializable