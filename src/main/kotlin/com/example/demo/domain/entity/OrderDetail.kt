package com.example.demo.domain.entity

import com.example.demo.domain.Enum.TypeMenu
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "order_detail")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class OrderDetail(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val orderDetailId : Int = 0,

        @JsonIgnore
        @ManyToOne
        @JoinColumn(name="order_id")
        var order : Order?,

        @OneToOne
        @JoinColumn(name = "product_id")
        val product : Product,

        val quantity: Int,

        @Column(insertable = false, updatable = false)
        val createdAt : LocalDateTime? = null

)