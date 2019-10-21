package com.example.demo.controller

import com.example.demo.domain.entity.User
import com.example.demo.service.UserService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/api/v1/logins")
class LoginController (
    val userService : UserService
){
    @GetMapping("/Form")
    fun loginForm(): String {
        return "Login Form"
    }

    @GetMapping("/join")
    fun joinForm(): String {
        return "JoinForm"
    }

    @PostMapping("/create")
    fun createUser(
            @RequestBody user: User
    ): Mono<User> = userService.createUser(user)

    @PostMapping("/check")
    fun loginCheck(
            @RequestParam userAccount : String,
            @RequestParam userPassword : String
    ): Mono<String> {
        return userService.loginCheck(userAccount, userPassword)
    }

}