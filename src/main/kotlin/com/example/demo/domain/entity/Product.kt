package com.example.demo.domain.entity

import com.example.demo.domain.Enum.MenuType
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Entity // 엔티티 자바객체를 디비의 테이블과 연결시키는 어노테이션
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class) // 카멜(leeInH), 케밥(lee-inh), 스네이크 케이스(lee_inh)
data class Product(// class앞에 data를 붙이면 vo객체 역할을 알림

        @Id // pk 역할을 한다는 것을 지정
        @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO는 자동선택 IDENTITY는 DB의 indentity컬럼을 이용?그러니까 데이터베이스의 설정에 따른다 라는 말인듯
        val productId : Int = 0, // 변수생성방법 자바스크립트랑 비슷함 [변수 변수이름 : 타입]

        @Enumerated(EnumType.STRING)// enum 어노테이션 정의 타입은 string으로 정함
        var menuType : MenuType,

        @get:NotNull
        var menu : String,

        @get:Min(value= 1L, message = "1이상의 가격을 정해주세요.")
        var price : Int,

        @Column(insertable = false, updatable = false) //이 컬럼을 insert나 update 시킬 때 포함하지않아도 된다 란뜻인듯 데이터베이스쪽에서 알아서한다
        val createdAt : LocalDateTime? = null,
        @Column(insertable = false, updatable = false)
        var updatedAt : LocalDateTime? = null
)
