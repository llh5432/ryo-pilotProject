package com.example.demo.controller

import com.example.demo.domain.Enum.MenuType
import com.example.demo.domain.dao.*
import com.example.demo.domain.entity.Order
import com.example.demo.domain.entity.OrderDetail
import com.example.demo.service.OrderService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/orders") // api/version/테이블명의 복수형태
class OrderController(
        val orderService: OrderService
) {

    @GetMapping
    fun readAll(): Flux<Order> = orderService.readFindAll()

    @GetMapping("/order/{userId}")
    fun readOrderByUserId(@PathVariable userId: Int): Flux<Order> = orderService.readOrderById(userId)

    @PostMapping("/{userId}")
    fun create( // 함수명은 간결하고 명확한 의미가 담긴것으로 명사형이 좋고 어쩔 수 없을 때 동사를 섞기도함
            @PathVariable userId : Int, // 테스트를 위해서 일단 계정pk를 손수 적음 실제론 front에서 cookie로 처리하는 듯
            @RequestBody @Valid orderForm : OrderForm // 입력받을 객체폼을 하나 만듦 Foot컴포넌트 부분 (Product, quantity(수량)이 들어갈 폼)
    ): Mono<Order> = orderService.createOrder(userId, orderForm)

    @GetMapping("/topUser")
    fun test1(): Mono<TopUser> = orderService.topUser()

    @GetMapping("/topMenu")
    fun test2(): Mono<TopMenu> = orderService.topMenu()

    @GetMapping("/topType")
    fun test3(): Mono<TopType> = orderService.topType()

    @GetMapping("/admin/dashBoard")
    fun getDashBoard(): Mono<DashBoard> = orderService.getDashBoard()

}