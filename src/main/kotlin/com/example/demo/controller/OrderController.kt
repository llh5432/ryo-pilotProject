package com.example.demo.controller

import com.example.demo.domain.entity.Order
import com.example.demo.service.OrderService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class OrderController(
        val orderService: OrderService
) {

    @GetMapping("/order/select/all")
    fun getAllOrderById (): Flux<Order> =
        orderService.getFindAll()

//    @PostMapping("/order/select/{userId}/{productId}")
//    fun createOrder(
//            @PathVariable userId : Int,
//            @PathVariable productId : Int
//    ): Mono<Order> = orderService.createOrder(userId, productId)

}