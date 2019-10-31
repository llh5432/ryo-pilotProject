package com.example.demo.controller

import com.example.demo.domain.dao.LoginForm
import com.example.demo.domain.dao.SearchAccount
import com.example.demo.domain.entity.User
import com.example.demo.service.UserService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono


@RestController
@RequestMapping("/api/v1/logins")
class LoginController(
        val userService: UserService
) {
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
            @RequestParam userAccount: String,
            @RequestParam userPassword: String
    ): Mono<String> {
        return userService.loginCheck(userAccount, userPassword)
    }

    @PostMapping("/test")
    fun test(
            @RequestBody loginForm: LoginForm
    ): Mono<String> = userService.testCheck(loginForm)

    @GetMapping("/search/account")
    fun searchAccount(
            searchAccount: SearchAccount
    ): Mono<String> = userService.readUserAccount(searchAccount)

    @GetMapping("/search/password")
    fun searchPassword(
            @RequestParam userAccount: String,
            @RequestParam userEmail: String
    ): Mono<String> = userService.readUserPassword(userAccount, userEmail)

}