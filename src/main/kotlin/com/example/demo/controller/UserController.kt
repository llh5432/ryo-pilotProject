package com.example.demo.controller

import com.example.demo.domain.entity.User
import com.example.demo.service.UserService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/user")
class UserController (
        val userService: UserService
){

    @GetMapping("/select/all")
    fun selectUserAll(
    ): Flux<User> = userService.selectUserAll()

    @GetMapping("/search/account")
    fun searchAccount(
            @RequestParam userEmail: String,
                           userName: String
    ): Mono<String> = userService.selectUserAccount(userEmail, userName)

    @GetMapping("/search/password")
    fun searchPassword(
            @RequestParam userAccount : String,
                          userEmail: String
    ): Mono<String> = userService.selectUserPassword(userAccount, userEmail)


}