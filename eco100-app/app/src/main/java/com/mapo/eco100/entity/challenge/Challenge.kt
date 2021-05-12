package com.mapo.eco100.entity.challenge

import java.io.Serializable


data class Challenge (
    val challenge_id : Long,
    val contents : String,
    var participant_cnt : Int,
    var img_url: String,
    var participation_cnt: Int

) : Serializable