package com.example.demo.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.demo.domain.dao.LoginForm
import com.example.demo.domain.dao.SearchAccount
import com.example.demo.domain.entity.User
import com.example.demo.exception.RestException
import com.example.demo.interceptor.MyInterceptor
import com.example.demo.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@Service
class UserService(
        val userRepository: UserRepository,
        val myInterceptor: MyInterceptor
) {

    fun createUser(user: User): Mono<User> =
            user.toMono() // 바로 .toMono로 리액터객체로 만듦 user를 데이터처리할거니깐
                    .doOnNext(this::checkAccount) // 파리미터 값 user가 성공적일 때 다음 행동을 함
                    .doOnNext(this::checkEmail) // this란 해당 클래스를 의미함 this클래스의 checkEmail 함수를 사용하는것을 행동(.doOnNext)한다.
//                    .map(userRepository::save)  유형 유추실패 유추할 정보가 충분하지않다는 에러 발생
                    .map { userRepository.save(it) }


    fun checkAccount(user: User) { // Id값만 체크하는 funtion
        if (userRepository.findByUserAccountIsLike(user.userAccount) != null)
            throw RestException(HttpStatus.INTERNAL_SERVER_ERROR, "입력하신 ${user.userAccount} 은 이미 가입한 계정입니다.")
    }

    fun checkEmail(user: User) { // Email값만 체크하면 funtion
        if (userRepository.findByUserEmailIsLike(user.userEmail) != null)
            throw RestException(HttpStatus.INTERNAL_SERVER_ERROR, "입력하신 ${user.userEmail} 은 이미 가입한 이메일입니다.")
    }


    fun readUserAll(): Flux<User> = Mono
            .fromSupplier {
                userRepository.findAll()
            }
            .flatMapMany {
                if (it.isNotEmpty()) {
                    Flux.fromIterable(it)
                } else {
                    throw RestException(HttpStatus.NOT_FOUND, "멤버가 없습니다.")
                }
            }

    fun readUserAccount(
           searchAccount: SearchAccount
    ): Mono<String> = Mono
            .fromSupplier {
                userRepository.findByUserEmailAndUserNameEquals(searchAccount.userEmail, searchAccount.userName)
                        ?.userAccount
                        ?: throw RestException(HttpStatus.NOT_FOUND, "${searchAccount.userEmail} , ${searchAccount.userName} 으로 찾은 ID가 없습니다.")
            }

    fun readUserPassword(
            userAccount: String,
            userEmail: String
    ): Mono<String> {
        return Mono.fromSupplier {
            userRepository.findByUserAccountIsLikeAndAndUserEmailLike(userAccount, userEmail)
                    ?.userPassword
                    ?: throw RestException(HttpStatus.NOT_FOUND, "$userAccount, $userEmail 으로 찾은 PASSWORD가 없습니다.")
        }
    }

    fun testCheck(
            loginForm: LoginForm
    ): Mono<String> =
            Mono.fromSupplier {
                userRepository.findByUserAccountEqualsAndUserPasswordEquals(loginForm.userAccount, loginForm.userPassword)
                ?.let { "성공" }
                        ?: throw RestException(HttpStatus.NOT_FOUND, "틀렸어")
            }


    fun loginCheck(
            userAccount: String,
            userPassword : String
    ): Mono<String> = Mono
            .fromSupplier {
                userRepository.findByUserAccountEqualsAndUserPasswordEquals(userAccount, userPassword)
                        ?.let { createToken(userAccount) }
                        ?:throw RestException(HttpStatus.NOT_FOUND, "아이디 및 패스워드가 틀렸습니다.")
            }

    fun createToken(userAccount: String): String{
        // 아이디 패스워드 체크가 완료 되었을 시에 토큰을 생성해서 토큰 값을 return 해줌
        val algorithm = Algorithm.HMAC256(myInterceptor.tokenKey) // algorithm 메소드의 HMAC256함수를 사용해서 토큰을 담을 그릇을 만듦(암호화 시킬 key)

        val tokenBuilder = JWT
                .create() // token builder를 반환함
                .withIssuer("pilot-project") // 보통 프로젝트명을 적는거같음
                .withClaim("user", "user") // .WITH 어쩌고 하나씩 PAYLOAD 쪽에 원하는 데이터 추가 할수있음
        return tokenBuilder.sign(algorithm) // 새로운 JWT를 작성하고 지정된 알고리즘으로 서명함 그리고 return
    }

    fun searchById(userId: Int): User? { //리턴 값으로 nullable체크
        return userRepository.findByIdOrNull(userId) //findBytId 가 없다면 null을 가져옴 nullable한 기능
    }

}// 서비스 끝