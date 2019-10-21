package com.example.demo.domain.dao

import com.example.demo.domain.entity.Product
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class OrderForm (
        @get:NotEmpty
        val orderTuples : List<OrderTuple> // View단에서 담은 최종 데이터 List를 orderTuples
)

data class OrderTuple( // 이 데이터객체는 foot컴포넌트 한 줄을 보여줄거임 (주문메뉴, 가격, 수량)

        @get:NotNull // validation not null
        val product: Product, // 메뉴이름, 가격이 포함되있으니 Product객체를 생성

        @get:Min(value = 0L, message = "수량을 확인하세요.")
        val quantity: Int, // 수량 OrderDetail 테이블과 Order테이블에 들어갈 컬럼

        @get:NotNull
        val total: Int // ''
)