package com.example.demo.controller

import com.example.demo.domain.entity.Order
import com.example.demo.service.OrderService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
class OrderController(
        val orderService: OrderService
) {

    @GetMapping("/order/select/all")
    fun getAllOrderById (): Flux<Order> =
        orderService.getFindAll()

}