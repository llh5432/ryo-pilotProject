package com.example.demo.controller

import com.example.demo.domain.entity.Order
import com.example.demo.domain.entity.OrderDetail
import com.example.demo.exception.RestException
import com.example.demo.service.OrderDetailService
import com.example.demo.service.OrderService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.support.WebExchangeBindException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/orderDetails")
class OrderDetailController(
        val orderDetailService: OrderDetailService
) {

    @RequestMapping
    fun readAll():Flux<OrderDetail?> =
            orderDetailService.readAll()

}