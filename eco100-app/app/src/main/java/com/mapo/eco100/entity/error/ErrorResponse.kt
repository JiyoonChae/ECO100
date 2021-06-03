package com.mapo.eco100.entity.error

import java.io.Serializable

data class ErrorResponse (
    private val code: Int,
    private val message: String
) : Serializable