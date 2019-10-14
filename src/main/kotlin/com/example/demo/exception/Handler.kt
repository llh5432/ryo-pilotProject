package com.example.demo.exception

import com.fasterxml.jackson.core.JsonParseException
import javassist.NotFoundException
import org.hibernate.exception.SQLGrammarException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ServerWebInputException
import reactor.netty.http.server.HttpServerRequest
import javax.jws.HandlerChain
import java.lang.ClassNotFoundException as ClassNotFoundException1

@RestControllerAdvice // RestController단의 예외를 잡아 처리해주는 어노테이션 전역적으로 exception을 처리하고 싶다
object Handler { //타입이 object인 이유 : 컨트롤러단 안에 있는 모든 함수의 리턴값들이 서로 다르니 모든 리턴 타입을 충족시킬수있는 게 object가 아닐까 생각함
    @ExceptionHandler(RestException::class) // 예외발생해서 안으로 타고들어와 handler를 타고 들어감
    fun handleRestException(restException: RestException): ResponseEntity<String> { //
        val errMsg = restException.errMsg.replace("\"".toRegex(),"\\\\\"") // string.replace : 주어진 조건에 일치해서 얻은 문자열을 반환해줌
        return ResponseEntity
                .status(restException.httpStatus) // 상태코드
                .header("Content-Type","application/json") // header 정보 key,value 값
                .body("{\"error_msg\": \"$errMsg\"}") // ResponseBody String으로 넘기면 아무고토없이 그냥 그대로 출력됨 형식을 맞춰줌
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<String> {
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
        if (ex is ServerWebInputException) {
            httpStatus = ex.status
        }
        ex.printStackTrace()
        return handleRestException(RestException(httpStatus, "입력 값을 확인해주세요."))
    }






}