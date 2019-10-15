package com.example.demo.service

import com.example.demo.domain.entity.User
import com.example.demo.exception.RestException
import com.example.demo.interceptor.MyInterceptor
import com.example.demo.repository.UserRepository
import org.omg.CORBA.Object
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono
import java.lang.reflect.Constructor

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


    fun selectUserAll(): Flux<User> = Mono
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

    fun selectUserAccount(
            userEmail: String,
            userName: String
    ): Mono<String> {
        val account = userRepository.findByUserEmailAndUserNameEquals(userEmail, userName)
        if (account == null) {
            throw RestException(HttpStatus.NOT_FOUND, "$userEmail , $userName 으로 찾은 ID가 없습니다.")
        } else {
            return account.userAccount.toMono()
        }
    }

    fun selectUserPassword(
            userAccount: String,
            userEmail: String
    ): Mono<String> {
        return Mono.fromSupplier {
            userRepository.findByUserAccountIsLikeAndAndUserEmailLike(userAccount, userEmail)
                    ?.userPassword
                    ?: throw RestException(HttpStatus.NOT_FOUND, "$userAccount, $userEmail 으로 찾은 PASSWORD가 없습니다.")
        }
    }

    fun loginCheck(
            userAccount: String,
            userPassword : String
    ): Mono<String> = Mono
            .fromSupplier {
                val result = userRepository.findByUserAccountEqualsAndUserPasswordEquals(userAccount, userPassword)
                        if (result == null) {
                            throw RestException(HttpStatus.NOT_FOUND, "아이디 및 패스워드가 틀렸습니다.")
                        } else {

                            result.userAccount+" 님 login함"
                        }
            }



}// 서비스 끝