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
class OrderDetailController(
        val orderDetailService: OrderDetailService
) {

//    @PostMapping("/order/{userId}/{productId}")
//    fun createOrder(
//            @PathVariable userId : Int,
//            @PathVariable productId : Int,
//            @RequestBody orderDetail: OrderDetail
//    ): Mono<OrderDetail> {
//        return orderDetailService.createdOrder(userId, productId, orderDetail)
//    }

}