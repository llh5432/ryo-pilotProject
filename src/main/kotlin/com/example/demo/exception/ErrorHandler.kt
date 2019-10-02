package com.example.demo.exception

import com.fasterxml.jackson.core.JsonParseException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.support.HttpRequestHandlerServlet
import reactor.netty.http.server.HttpServerRequest

@ControllerAdvice
class ErrorHandler {
    @ExceptionHandler(JsonParseException::class)
    fun JsonmParseExceptionHandler(
            servletRequest: HttpRequestHandlerServlet,
            exception: Exception
    ): ResponseEntity<String> {
        return ResponseEntity("JSON Error", HttpStatus.BAD_REQUEST)
    }
}