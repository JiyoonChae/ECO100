package com.mapo.eco100.entity.challenge

import java.io.File
import java.io.Serializable
import java.sql.Date
import java.util.*


data class ChallengePost (
    val challengeId: Int,
    val challengePostId :Int,
    val image: String,
    val contents : String,

) :Serializable