package com.example.demo.repository

import com.example.demo.domain.entity.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: JpaRepository<Order, Int> {

    fun findAllByOrderByOrderCreatedAtDesc(): List<Order>
    fun findAllByUserUserId(userId: Int): List<Order>
//    fun findByOrderTotalPrice(): List<Int>
    // 유저id들을 그룹화해서 그 그룹들중 가장 totalprice가 높은 유저를 뽑음
//    @Query("select ")

}