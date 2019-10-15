package com.example.demo.service

import com.example.demo.domain.entity.Order
import com.example.demo.exception.RestException
import com.example.demo.repository.OrderDetailRepository
import com.example.demo.repository.OrderRepository
import com.example.demo.repository.ProductRepository
import com.example.demo.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@Service
class OrderService (
        val orderRepository: OrderRepository,
        val productRepository: ProductRepository,
        val orderDetailRepository: OrderDetailRepository,
        val userRepository: UserRepository
){


    fun getFindAll(): Flux<Order> = Mono
            .fromSupplier {
                val result = orderRepository.findAll()
                if (result.isEmpty()) {
                    throw RestException(HttpStatus.NOT_FOUND, "안됨")
                } else {
                    result
                }
            }.flatMapMany {
                Flux.fromIterable(it)
            }

//    fun createOrder(
//            userId : Int,
//            productId : Int
//    ): Mono<Order> = Mono.fromSupplier {
//        // 먼저 Order테이블에 데이터가 입력되고 order-detail 테이블에 입력이 되야함
//
//    }

}