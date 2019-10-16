package com.example.demo.domain.entity

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime
import javax.persistence.*

@Entity // GET,SET 메서드라고 봄
@Table(name = "`order`")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class Order (

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val orderId: Int = 0,

//        @JsonIgnore
        @ManyToOne
        @JoinColumn(name="order_user_id")
        var user : User,

        var orderTotalPrice: Int,
        var orderTotalNum: Int,

        @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
        var orderDetails: List<OrderDetail>,

        @Column(insertable = false, updatable = false)
        val orderCreatedAt: LocalDateTime? = null

)