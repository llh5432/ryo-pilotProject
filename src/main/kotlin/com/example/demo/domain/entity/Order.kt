package com.example.demo.domain.entity

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.sql.Timestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class Order (

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val orderId: Int,
        val orderAccount: String,
        val orderInfo: String,
        val orderTotalPrice: Int,
        val orderTotalNum: Int,

        @Column(insertable = false, updatable = false)
        val orderCreatedAt: LocalDateTime?

)