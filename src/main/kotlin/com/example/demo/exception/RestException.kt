package com.example.demo.exception

import org.springframework.http.HttpStatus

data class RestException ( //data는 get,set 메서드라고 보면 될듯 에러를 친절하게 보여주기위해서
    val httpStatus: HttpStatus,
    val errMsg: String
) : Exception() // Exception타입을 리턴함