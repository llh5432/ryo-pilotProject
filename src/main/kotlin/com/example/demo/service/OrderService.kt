package com.example.demo.service

import com.example.demo.domain.OrderForm
import com.example.demo.domain.entity.Order
import com.example.demo.domain.entity.OrderDetail
import com.example.demo.domain.entity.Product
import com.example.demo.exception.RestException
import com.example.demo.repository.OrderDetailRepository
import com.example.demo.repository.OrderRepository
import com.example.demo.repository.ProductRepository
import com.example.demo.repository.UserRepository
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@Service
class OrderService(
        val orderRepository: OrderRepository,
        val productRepository: ProductRepository,
        val orderDetailRepository: OrderDetailRepository,
        val userService: UserService
) {

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

    fun sumProducts(productId: List<Int>): Int {

//        var list = productRepository.findByProductId(productId).map {
//            it.price
//        }
//        var sum = 0
//        for (i in 0..list.size + 1) {
//            sum = list[i]
//        }
//        return sum
        return productRepository.findByProductId(productId)
                .sumBy { it.price }
    }


    fun createOrder(
            userId: Int,
            orderForm: OrderForm
    ): Mono<Order> = Mono
            .fromSupplier {
                userService.searchById(userId)
                        ?: throw RestException(HttpStatus.NOT_FOUND, "user 를 찾지 못했습니다. userId: $userId")
            }
            .map {
                val orderTotalPrice = orderForm.orderTuples.sumBy { orderTuple -> orderTuple.product.price }
                val orderTotalNum = orderForm.orderTuples.sumBy { orderTuple -> orderTuple.quantity }
                val orderDetails = orderForm.orderTuples.map { orderTuple ->
                    OrderDetail(
                            order = null,
                            product = orderTuple.product,
                            quantity = orderTuple.quantity
                    )
                }
                val order = Order(
                        user = it,
                        orderTotalPrice = orderTotalPrice,
                        orderTotalNum = orderTotalNum,
                        orderDetails = orderDetails
                )
                orderRepository.save(order)

                orderDetails.forEach {
                    it.order = order
                }

               order.orderDetails = orderDetailRepository.saveAll(orderDetails)
                orderRepository.save(order)
            }

}

