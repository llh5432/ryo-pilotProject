package com.example.demo.controller

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono




@Component
class MyInterceptor: WebFilter {
    override fun filter(serverWebExchange: ServerWebExchange,
                        webFilterChain: WebFilterChain): Mono<Void> {

        val authorizationHeader = serverWebExchange.request.headers["authorization"]?.get(0) ?: ""
        return if (authorizationHeader.isNotEmpty()) {
            val token = authorizationHeader.replace("Bearer ", "")

            try {
                verifier!!.verify(token)
            } catch (ex: JWTVerificationException) {
                serverWebExchange.response.statusCode = HttpStatus.FORBIDDEN
                return Mono.empty()
            }

            webFilterChain.filter(serverWebExchange)
        } else {
            serverWebExchange.response.statusCode = HttpStatus.UNAUTHORIZED
            Mono.empty()
        }
    }

    constructor() {
        val algorithm = Algorithm.HMAC256(tokenKey)
        verifier = JWT.require(algorithm)
                .withIssuer("delibird-auth-service")
                .build()
    }

    var verifier: JWTVerifier? = null
    var tokenKey = "COAA542hQ+ECDVb7zQ77Rx5+Om+Gw+TcpIKQa3Q0E4eqZ/h6h9uijbd39/+r0+t4iGlY/ZudC2VjiTfKCJrWwk1U1hXDIWvgvULLYqiVq7GtG2FoA94LuHk9tAeKJ4x5kbd1iXE2XYpi"
}