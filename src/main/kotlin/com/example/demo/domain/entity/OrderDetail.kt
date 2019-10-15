package com.example.demo.domain.entity

import com.example.demo.domain.Enum.TypeMenu
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "order_detail")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class OrderDetail(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val orderDetailId : Int,

        @ManyToOne
        @JoinColumn(name="order_id")
        val order : Order,

        @ManyToOne
        @JoinColumn(name = "product_id")
        val product : Product,


        @Column(insertable = false, updatable = false)
        val orderDetailCreatedAt : LocalDateTime

)