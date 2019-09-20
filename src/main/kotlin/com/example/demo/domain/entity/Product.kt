package com.example.demo.domain.entity

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime
import javax.persistence.*

@Entity // 엔티티라는 자바객체를 디비의 테이블과 연결시키는 어노테이션
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
// 카멜, 케밥, 스네이크 케이스
data class Product(// class앞에 data를 붙이면 vo객체 역할을 알림
        @Id // primary key 지정
        @GeneratedValue(strategy = GenerationType.IDENTITY) // primary key의 타입이 숫자일경우 autoIncrese 생성됨
        val productId : Int, // 변수생성방법 자바스크립트랑 비슷함 [변수 변수이름 : 타입]
        val name : String,
        val price : String,
        val createdAt : LocalDateTime,
        val updatedAt : LocalDateTime
)