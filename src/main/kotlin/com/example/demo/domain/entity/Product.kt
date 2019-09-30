package com.example.demo.domain.entity

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime
import javax.persistence.*

@Entity // 엔티티라는 자바객체를 디비의 테이블과 연결시키는 어노테이션
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class) // 카멜(leeInH), 케밥, 스네이크 케이스(lee_inh)
data class Product(// class앞에 data를 붙이면 vo객체 역할을 알림
        @Id // primary key 지정
        @GeneratedValue(strategy = GenerationType.IDENTITY) // primary key의 타입이 숫자일경우 autoIncrese 생성됨
        val productId : Int, // 변수생성방법 자바스크립트랑 비슷함 [변수 변수이름 : 타입]

        @Enumerated(EnumType.STRING)
        var menuType : TypeFood,
        var menu : String,
        var price : Int,
        @Column(insertable = false, updatable = false)
        val createdAt : LocalDateTime?,
        @Column(insertable = false, updatable = false)
        var updatedAt : LocalDateTime?
)
enum class TypeFood{
         KR,
         JP,
         CN,
         PA,
         BU
}

data class Person( // lamda 식 테스트용 data class
        var name: String,
        var age: Int,
        var joinDated: Int)


data class animal( //  TEST
        var name : String
)