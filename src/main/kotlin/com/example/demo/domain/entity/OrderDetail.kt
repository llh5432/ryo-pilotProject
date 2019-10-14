package com.example.demo.domain.entity

import com.example.demo.domain.Enum.TypeMenu
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class OrderDetail(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val orderDetailId : Int,

//        @OneToMany
//        @JoinColumn(name="order_id")
//        val order : Order,
//
//        @OneToMany
//        @JoinColumn(name = "product_id")
//        val product : Product,
//
//        @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
//        @JoinColumn(name = "member_id")
//        var member : Member,

        var orderDetailAccount : String,

        var orderDetailType : TypeMenu,

        var orderDetailMenuName : String,

        var orderDetailMenuPrice : Int,

        @Column(insertable = false, updatable = false)
        val orderDetailCreatedAt : LocalDateTime

)