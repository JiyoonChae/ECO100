package com.mapo.eco100.entity.challenge

import java.io.File
import java.io.Serializable
import java.sql.Date
import java.util.*
import javax.security.auth.Subject


data class ChallengePost (
    val challengePostId :Int,
    val imageUrl: String,
    val contents : String,
    val subject: String,
    val date: String

) :Serializable