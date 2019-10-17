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
        val orderId: Int = 0, // 요것도 default 값을 0으로 일단 줘놔야 에러가 안난다

//        @JsonIgnore
        @ManyToOne
        @JoinColumn(name="order_user_id")
        var user : User,

        var orderTotalPrice: Int,
        var orderTotalQuantity: Int,

        @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
        var orderDetails: List<OrderDetail>, // 양방향 맵핑

        @Column(insertable = false, updatable = false)
        val orderCreatedAt: LocalDateTime? = null // DB에서 처리되는 것이니 일단은 nullAble로 처리를 해줘야 에러가 안남

)