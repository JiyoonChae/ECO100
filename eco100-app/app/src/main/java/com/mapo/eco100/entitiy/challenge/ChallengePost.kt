package com.mapo.eco100.entitiy.challenge

import java.io.Serializable

data class ChallengePost (
    val challengeId: Int,
    val challengePostId :Int,
    val image: String,
    val contents : String,

 ) :Serializable