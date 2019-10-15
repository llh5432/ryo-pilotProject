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
        val orderId: Int,

//        @JsonIgnore
        @ManyToOne // 주종에서 종의 테이블이 Many
        @JoinColumn(name="order_user_id") // 맵핑할 외래키 입력 생략 시 기본전략을 사용 : 필드명 + "_" + 테이블명+id
        val user : User, //Collection 타입의 객체가 필드에 추가

        val orderTotalPrice: Int,
        val orderTotalNum: Int,

        @Column(insertable = false, updatable = false)
        val orderCreatedAt: LocalDateTime?

)