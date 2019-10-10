package com.example.demo.exception

import org.springframework.http.HttpStatus

data class NotFoundMemberException(
        val httpStatus: HttpStatus,
        val err_Msg: String
) : Exception()