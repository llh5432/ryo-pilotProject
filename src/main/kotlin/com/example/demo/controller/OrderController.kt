package com.example.demo.controller

import com.example.demo.domain.OrderForm
import com.example.demo.domain.entity.Order
import com.example.demo.domain.entity.OrderDetail
import com.example.demo.domain.entity.Product
import com.example.demo.exception.RestException
import com.example.demo.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/orders")
class OrderController(
        val orderService: OrderService
) {

    @GetMapping
    fun readAll(): Flux<Order> = orderService.getFindAll()

    @PostMapping("/{userId}")
    fun create(
            @PathVariable userId : Int,
            @RequestBody @Valid orderForm : OrderForm
    ): Mono<Order> = orderService.createOrder(userId, orderForm)


}