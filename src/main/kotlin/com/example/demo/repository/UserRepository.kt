package com.example.demo.repository

import com.example.demo.domain.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sun.security.util.Password

@Repository
interface UserRepository : JpaRepository<User, Int>{

    fun findByUserAccountIsLike(userAccount: String): User? // 아이디중복방지
    fun findByUserEmailIsLike(userEmail: String): User? // 이메일중복방지
    fun findByUserEmailAndUserNameEquals(userEmail: String, userName: String): User? // 아이디찾기
    fun findByUserAccountIsLikeAndAndUserEmailLike(userAccount: String, userEmail: String): User? // 패스워드 찾기

    fun findByUserAccountEqualsAndUserPasswordEquals(userAccount: String, userPassword: String): User?

}