package com.example.demo.interceptor

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono



@Component // 사용자가 직접 작성한 class를 bean으로 등록하기 위한 어노테이션 @Bean 과 동일한 역할
class MyInterceptor: WebFilter { // kotlin 에서 java의 interceptor 대신 WebFilter 라는 걸 제공해줌
    override fun filter(serverWebExchange: ServerWebExchange,
                        webFilterChain: WebFilterChain): Mono<Void> { // void를 리턴함..
        // WebFilter 예외처리..
        if (serverWebExchange.request.path.toString().contains("logins")) return webFilterChain.filter(serverWebExchange)


        if (serverWebExchange.request.method === HttpMethod.OPTIONS) return webFilterChain.filter(serverWebExchange) // OPTION에 대한 필터도 추가

        val authorizationHeader = serverWebExchange.request.headers["authorization"]?.get(0) ?: "" // token을 받아옴
        return if (authorizationHeader.isNotEmpty()) { // token이 있다면
            val token = authorizationHeader.replace("Bearer ", "") // tokne 앞의 bearer 을 ""공백으로 지워줌

            try {
                verifier!!.verify(token) // ()안의 token값을 확인해서 decoding된 JWT를 return 함
            } catch (ex: JWTVerificationException) {
                serverWebExchange.response.statusCode = HttpStatus.FORBIDDEN // response 해줌 상태코드는 forbidden으로 403인가
                return Mono.empty() //
            }

            webFilterChain.filter(serverWebExchange) // 흠..?
        } else { //autohrizationHeader가 없는 경우 token이 없는 경우

            serverWebExchange.response.statusCode = HttpStatus.UNAUTHORIZED // 서버 측 처리를 노출시킬수있다? 상태코드를 정해서 response해줄 수 있다
            Mono.empty() // mono.empty
        }
    }

    constructor() { // 생성자 : 객체가 생성될 때 자동으로 실행되는 특수 메서드
        val algorithm = Algorithm.HMAC256(tokenKey) // Algorithm클래스의 HMAC256함수를 사용 (secretKey) 새알고리즘을 만들어서 줌 ㄱ걸 변수에 저장
        verifier = JWT.require(algorithm) //
                .withIssuer("pilot-project") //issuer는 동일하게....
                .build() // create
    }

    var verifier: JWTVerifier? = null // JWTVerifier 클래스가 verify 토큰을 제공해서 주어진 토큰이 올바른지 일치여부를 가려줌
    var tokenKey = "COAA542hQ+ECDVb7zQ77Rx5+Om+Gw+TcpIKQa3Q0E4eqZ/h6h9uijbd39/+r0+t4iGlY/ZudC2VjiTfKCJrWwk1U1hXDIWvgvULLYqiVq7GtG2FoA94LuHk9tAeKJ4x5kbd1iXE2XYpi"
    // tokenKey 이 키를 가지고 token을 해독해서 씀
}