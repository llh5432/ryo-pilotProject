package com.example.demo.repository

import com.example.demo.domain.entity.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: JpaRepository<Order, Int> {

}