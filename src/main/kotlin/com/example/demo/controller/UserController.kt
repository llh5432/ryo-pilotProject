package com.example.demo.controller

import com.example.demo.domain.entity.User
import com.example.demo.service.UserService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/users")
class UserController (
        val userService: UserService
){

    @GetMapping
    fun readAll(
    ): Flux<User> = userService.readUserAll()


}