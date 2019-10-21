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
        @ManyToOne // 하나의 user_id는 여러개의 order를 가질 수 있음
        @JoinColumn(name="order_user_id") // join하는 컬럼 명
        var user : User,

        var orderTotalPrice: Int,
        var orderTotalQuantity: Int,

        @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
        // mappedBy는 주가되는 table 명
        // fetchType은 해당 테이블의 데이터를 미리 불러오는가(EAGER), 호출할 때 불러오는가에 대한 설정(LAZY)
        // CascadeType은 조인 된 테이블관계에 대한 설정 (주인 테이블의 데이터가 삭제되면 연관되있는 종의 데이터도 삭제 등등)
        var orderDetails: List<OrderDetail>, // 양방향 맵핑

        @Column(insertable = false, updatable = false)
        val orderCreatedAt: LocalDateTime? = null // DB에서 처리되는 것이니 일단은 nullAble로 처리를 해줘야 에러가 안남

)