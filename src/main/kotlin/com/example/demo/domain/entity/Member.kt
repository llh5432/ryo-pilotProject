package com.example.demo.domain.entity

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.sql.Timestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class Member (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val memberId: Int,

        val memberAccount: String,
        val memberPasswd: String,
        val memberEmail: String,
        val memberName: String,
        @Column(insertable = false, updatable = false)
        val memberCreatedAt: LocalDateTime?

)