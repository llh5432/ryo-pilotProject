package com.example.demo.service

import com.example.demo.domain.entity.OrderDetail
import com.example.demo.exception.RestException
import com.example.demo.repository.OrderDetailRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class OrderDetailService (
        val orderDetailRepository: OrderDetailRepository
) {

    fun readAll(): Flux<OrderDetail?> = Mono
            .fromSupplier {
                orderDetailRepository.findAll()
            }.onErrorResume {
                throw RestException(httpStatus = HttpStatus.NOT_FOUND, errMsg = "해당하는 데이터를 찾지 못했습니다.")
            }.flatMapMany {
                Flux.fromIterable(it!!)
            }



}