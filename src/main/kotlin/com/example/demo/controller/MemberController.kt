package com.example.demo.controller

import com.example.demo.domain.entity.Member
import com.example.demo.service.MemberService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/member")
class MemberController (
        val memberService: MemberService
){

    @PostMapping("/create")
    fun createMember(
            @RequestBody member: Member
    ): Mono<Member> = memberService.createMember(member)

    @GetMapping("/select/all")
    fun selectMemberAll(
    ): Flux<Member> = memberService.selectMemberAll()

    @GetMapping("/search/id")
    fun searchAccount(
            @RequestParam memberEmail: String,
                           memberName: String
    ): Mono<String> = memberService.selectMemberAccount(memberEmail, memberName)

    @GetMapping("/search/passwd")
    fun searchPassword(
            @RequestParam memberAccount : String,
                          memberEmail: String
    ): Mono<String> = memberService.selectMemberPasswd(memberAccount, memberEmail)

}