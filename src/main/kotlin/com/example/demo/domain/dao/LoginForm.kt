package com.example.demo.domain.dao

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class LoginForm (
        val userAccount: String,
        val userPassword: String
)
