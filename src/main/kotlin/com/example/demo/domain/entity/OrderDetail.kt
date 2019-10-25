package com.example.demo.domain.entity

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
        @JoinColumn(name="order_id") // 양방향 맵핑 OrderDetail은 여러개일수있고 조인 된 Order은 하나
        var order : Order?, // 이것은 왜 nullAble 처리를 하는가? -> 순서가 order가 저장되고 order detail이 저장됨
        // 그러나 back end에서 순서는 order 빈객체를 만들고 일단 저장 -> order detail 객체를 만들고 -> order 저장하고 -> order detail 저장 완료 끝
        // 여기서 단순히 orderId 즉 order pk가 필요하니까 다른 데이터들은 채워지지않을수있으니 nullAble 해둠

        @OneToOne // detail테이블과 product테이블은 하나의 detail에 하나의 product 데이터가 들어감 그래서 oneToOne 관계
        @JoinColumn(name = "product_id")
        val product : Product,

        val quantity: Int,

        val total: Int = 0, // 초기화

        @Column(insertable = false, updatable = false)
        val createdAt : LocalDateTime? = null // 이것도 마찬가지 null

)