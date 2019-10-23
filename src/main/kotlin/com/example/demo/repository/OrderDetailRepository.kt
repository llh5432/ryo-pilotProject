package com.example.demo.repository

import com.example.demo.domain.entity.OrderDetail
import org.springframework.data.jpa.repository.JpaRepository

interface OrderDetailRepository : JpaRepository<OrderDetail, Int>{


}