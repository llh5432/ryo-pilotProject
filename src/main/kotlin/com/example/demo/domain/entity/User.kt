package com.example.demo.domain.entity

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class User (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val userId: Int = 0,

//        @JsonIgnore// json 형태로 쭉 읽다가 무시하고 지나가는 컬럼 없는취급함
//        @OneToMany(mappedBy = "user", fetch = FetchType.EAGER) // 주종에서 주인의 쪽 테이블이 One ()
//        var orders: List<Order?>,

        @get:NotNull
        val userAccount: String,
        @get:NotNull
        val userPassword: String,
        @get:NotNull
        val userEmail: String,
        @get:NotNull
        val userName: String,

        @Column(insertable = false, updatable = false)
        val userCreatedAt: LocalDateTime? = null



)