package com.example.demo.exception

import com.fasterxml.jackson.core.JsonParseException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import reactor.netty.http.server.HttpServerRequest

@ControllerAdvice
class ErrorHandler {
    @ExceptionHandler(JsonParseException::class)
    fun jsonParseExceptionHandler(
            servletRequest: HttpServerRequest,
            exception: Exception
    ): ResponseEntity<ErrorResponse>{
        return ResponseEntity(ErrorResponse("JSON Error", exception.message ?:"invalid json"),
                HttpStatus.BAD_REQUEST)
    }
}