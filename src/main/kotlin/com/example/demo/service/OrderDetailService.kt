package com.example.demo.service

import com.example.demo.domain.entity.Member
import com.example.demo.domain.entity.Order
import com.example.demo.domain.entity.OrderDetail
import com.example.demo.exception.RestException
import com.example.demo.repository.MemberRepository
import com.example.demo.repository.OrderDetailRepository
import com.example.demo.repository.OrderRepository
import com.example.demo.repository.ProductRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@Service
class OrderDetailService (
        val orderDetailRepository: OrderDetailRepository,
        val productRepository: ProductRepository,
        val memberRepository: MemberRepository,
        val orderRepository: OrderRepository
) {

//    fun createdOrder(
//            userId : Int,
//            productId : Int,
//            orderDetail: OrderDetail
//    ): Mono<OrderDetail> =
//            Mono.fromSupplier {
//                memberRepository.findById(userId)
//                        .map {
//                            orderDetail.member = it
//                            orderDetail.orderDetailAccount = it.memberAccount
//                        }
//            }.map {
//                productRepository.findById(productId).map {
//                    orderDetail.orderDetailType = it.menuType
//                    orderDetail.orderDetailMenuName = it.menu
//                    orderDetail.orderDetailMenuPrice = it.price
//                }
//
//                orderDetailRepository.save(orderDetail)
//            }


}