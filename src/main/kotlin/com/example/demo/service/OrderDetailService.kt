package com.example.demo.service

import com.example.demo.domain.entity.OrderDetail
import com.example.demo.repository.OrderDetailRepository
import com.example.demo.repository.OrderRepository
import com.example.demo.repository.ProductRepository
import com.example.demo.repository.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class OrderDetailService (
        val orderDetailRepository: OrderDetailRepository,
        val productRepository: ProductRepository,
        val userRepository: UserRepository,
        val orderRepository: OrderRepository
) {

    fun selectAll(): Flux<OrderDetail> = Mono
            .fromSupplier {
                orderDetailRepository.findAll()
            }.flatMapMany {
                Flux.fromIterable(it)
            }
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