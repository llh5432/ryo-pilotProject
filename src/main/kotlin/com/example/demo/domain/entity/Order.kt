package com.example.demo.domain.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "`order`")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class Order (

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val orderId: Int,

        @JsonIgnore
        @ManyToOne
        @JoinColumn(name="order_member_id")
        val member : Member,

        val orderAccount: String,
        val orderTotalPrice: Int,
        val orderTotalNum: Int,

        @Column(insertable = false, updatable = false)
        val orderCreatedAt: LocalDateTime?

)