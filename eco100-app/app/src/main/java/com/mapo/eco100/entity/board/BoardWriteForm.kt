package com.sg.eco100.entity.board

data class BoardWriteForm(
    private val userId:Long,
    private val title:String,
    private val contents:String
)