package com.example.demo.service


import com.example.demo.domain.Enum.MenuType
import com.example.demo.domain.dao.*
import com.example.demo.domain.entity.Order
import com.example.demo.domain.entity.OrderDetail
import com.example.demo.exception.RestException
import com.example.demo.repository.OrderDetailRepository
import com.example.demo.repository.OrderRepository
import com.example.demo.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
@Service
class OrderService(
        val orderRepository: OrderRepository,
        val orderDetailRepository: OrderDetailRepository,
        val userRepository: UserRepository,
        val userService: UserService
) {

    fun readFindAll(): Flux<Order> = Mono
            .fromSupplier {
                val result = orderRepository.findAllByOrderByOrderCreatedAtDesc()
                if (result.isEmpty()) {
                    throw RestException(HttpStatus.NOT_FOUND, "안됨")
                } else {
                    result
                }
            }.flatMapMany {
                Flux.fromIterable(it!!)
            }

    fun readOrderById(userId: Int): Flux<Order> = Mono
            .fromSupplier {
                orderRepository.findAllByUserUserId(userId)
            }.flatMapMany {
                it?.let { Flux.fromIterable(it) } ?: throw RestException(HttpStatus.NOT_FOUND, "아직 오더가 없습니다.")
            }


    fun createOrder(
            userId: Int,
            orderForm: OrderForm
    ): Mono<Order> = Mono
            .fromSupplier {
                userService.searchById(userId) // userId값에 해당하는 User객체를 가져옴 null이면 null을 가져옴
                        ?: throw RestException(HttpStatus.NOT_FOUND, "user를 찾지 못했습니다. userId : $userId")
            }.map {

                // orderDetail에 저장할 객체를 만듦
                val orderDetails = orderForm.orderTuples.map { orderTuple ->
                    //foot 컴포넌트 tuple 만드는 객체 생성
                    OrderDetail( // orderDetail 생성자 완성
                            order = null,
                            product = orderTuple.product,
                            quantity = orderTuple.quantity,
                            total = orderTuple.product.price.times(orderTuple.quantity)
                    )
                }
                // 순서를 바꾸니까 됨 위에 orderDetail의 total을 먼저 계산해서 넣고 밑에서 detail의 합계를 다시 order에 합계하니까 들어감
                val orderTotalNum = orderForm.orderTuples.sumBy { orderTuple -> orderTuple.quantity } // 간편해..
                val orderTotalPrice = orderForm.orderTuples.sumBy { orderTuple -> orderTuple.product.price.times(orderTuple.quantity) }

                val order = Order( // DB에 저장할 order 객체를 완서엉
                        user = it,
                        orderTotalPrice = orderTotalPrice,
                        orderTotalQuantity = orderTotalNum,
                        orderDetails = orderDetails
                )
                orderRepository.save(order) // 먼저 order를 저장해서 ordeId를 생성시켜야 orderDetail에 있는 orderId가 충족되어서 저장시킬수있음

                orderDetails.forEach {
                    // 반복문 실행해서 orderDetail안에 있는 order를 위에 완성시킨 order로 대입
                    it.order = order
                }
                //
                order.orderDetails = orderDetailRepository.saveAll(orderDetails) //saveAll은 데이터베이스에 보류중인 모든변경사항을 flush시켜줌
                orderRepository.save(order)
            }


    fun topUser(): Mono<TopUser> = Mono
            .fromSupplier {
                orderRepository.findAll()
                        .groupBy { it.user.userId }
                        .maxBy { it.value.sumBy { order -> order.orderTotalPrice } }

            }.map {
                TopUser(
                        topUserAccount = it!!.value[0].user.userAccount,
                        total = it.value.sumBy { order -> order.orderTotalPrice }
                )
            }


    fun topMenu(): Mono<TopMenu> = Mono
            .fromSupplier {
                orderDetailRepository.findAll()
                        .groupBy { it.product.productId } // 공통적으로 필요한 변수
                        .maxBy { it.value.sumBy { orderDetail -> orderDetail.total } }
            }.map {
                TopMenu(
                        topMenu = it!!.value[0].product.menu,
                        total = it.value.sumBy { orderDetail -> orderDetail.total }
                )
            }

    fun topType(): Mono<TopType> = Mono
            .fromSupplier {
                orderDetailRepository.findAll()
                        .groupBy { it.product.menuType }
                        .maxBy { it.value.sumBy { orderDetail -> orderDetail.total } }
            }.map {
                TopType(
                        topType = MenuType.valueOf(it!!.key.name),
                        total = it.value.sumBy { orderDetail -> orderDetail.total }
                )
            }

    fun userCount(): Mono<Int> = Mono
            .fromSupplier {
                userRepository.findAll().size
            }

    fun orderCount(): Mono<Int> = Mono
            .fromSupplier {
                orderRepository.findAll().size
            }

    fun orderTotal(): Mono<Int> = Mono
            .fromSupplier {
                orderRepository.findAll().sumBy { it.orderTotalPrice }
            }

    fun getDashBoard(): Mono<DashBoard> = topUser() // Mono타입을 리턴함
            .zipWith(topMenu()) // .zipWith함수는 여러개의 mono객체들을 합체 시켜주는 함수 최초 리턴 시키는 객체가 결과를 가져온 대로 모두를 업고 간다고 생각하면 됨
            .map {
                DashBoard(
                         adminTopUser = it.t1,
                         adminTopMenu = it.t2
                )
            }// stream을 이어가는 객체는 DashBoard객체

            .zipWith(topType())
            .map {
                it.t1.copy(
                        adminTopType = it.t2)
            }
            .zipWith(userCount())
            .map {
                it.t1.copy(
                        adminUserCount = it.t2
                )
            }
            .zipWith(orderCount())
            .map {
                it.t1.copy(
                        adminOrderCount = it.t2
                )
            }
            .zipWith(orderTotal())
            .map {
                it.t1.copy(
                        adminTotal = it.t2
                )
            }

}

