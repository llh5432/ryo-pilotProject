package com.example.demo.domain

import com.example.demo.domain.entity.Product
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import javax.validation.constraints.*

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class OrderForm(
        @get:NotEmpty
        val orderTuples: List<OrderTuple>
)

data class OrderTuple(
        @get:NotNull
        val product: Product,

        @get:Min(value = 0L, message = "0 보다 큰 값을 넣어주세요.")
        val quantity: Int
)