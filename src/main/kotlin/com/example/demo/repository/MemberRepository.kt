package com.example.demo.repository

import com.example.demo.domain.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository: JpaRepository<Member, Int>{

    fun findByMemberAccountIsLike(memberAccount: String): Member? // 아이디중복방지
    fun findByMemberEmailIsLike(memberEmail: String): Member? // 이메일중복방지
    fun findByMemberEmailAndMemberNameEquals(memberEmail: String, memberName: String): Member? // 아이디찾기
    fun findByMemberAccountIsLikeAndAndMemberEmailLike(memberAccount: String, memberEmail: String): Member? // 패스워드 찾기

    fun findByMemberAccountEqualsOrMemberPasswdEquals(memberAccount: String, memberPassword: String): Int



}