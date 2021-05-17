package com.mapo.eco100.entity.challenge

import java.io.Serializable


data class Challenge (
    val challengeId : Long,
    val subject : String,
    var numOfParticipants : Int,
    var imageUrl: String,
    var myParticipationCnt: Int

) : Serializable