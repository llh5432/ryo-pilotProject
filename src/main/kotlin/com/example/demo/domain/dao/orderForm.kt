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
        val orderTuples : List<OrderTuple> // 메뉴 하나씩 추가할 때마다 늘어갈 list 쪽
)

data class OrderTuple( // 이 데이터객체는 foot컴포넌트 한 줄을 보여줄거임 (주문메뉴, 가격, 수량)

        @get:NotNull
        val product: Product, // 메뉴이름, 가격

        @get:Min(value = 0L, message = "수량을 확인하세요.")
        val quantity: Int, // 수량

        @get:NotNull
        val total: Int
)