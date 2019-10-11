package com.example.demo.service

import com.example.demo.domain.entity.Order
import com.example.demo.exception.RestException
import com.example.demo.repository.OrderRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@Service
class OrderService (
        val orderRepository: OrderRepository
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

}