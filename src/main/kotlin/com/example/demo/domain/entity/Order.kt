package com.example.demo.domain.entity

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.sun.jmx.snmp.Timestamp
import java.time.LocalDateTime
import javax.persistence.*

//@Entity
//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
//data class Order (
////        @Id
////        @GeneratedValue(strategy = GenerationType.IDENTITY)
////        val orderId: Int,
////
////        @ManyToOne
////        @JoinColumn(name="productId")
////        val orderMenu: String,
////        val orderTotal: String,
////        @Column(insertable = false, updatable = false)
////        val orderCreateAt: LocalDateTime?,
////        @Column(insertable = false, updatable = false)
////        val orderCompletedAt: LocalDateTime?
//)