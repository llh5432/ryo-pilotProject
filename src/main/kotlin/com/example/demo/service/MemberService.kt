package com.example.demo.service

import com.example.demo.domain.entity.Member
import com.example.demo.exception.NotFoundMemberException
import com.example.demo.exception.RestException
import com.example.demo.repository.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@Service
class MemberService(
        val memberRepository: MemberRepository
) {

    fun createMember(member: Member): Mono<Member> = //checkMemberAccount(member)
//            .onErrorResume(NotFoundMemberException::class.java) {
//                checkMemberEmail(member)
//                        .onErrorResume(NotFoundMemberException::class.java) {
//                            memberRepository.save(member)
//                            member.toMono()
//                        }
//                member.toMono()
//            }

            member.toMono()
                    .doOnNext(this::checkAccount)
                    .doOnNext(this::checkEmail)
                    .map(memberRepository::save)


    fun checkAccount(member: Member) {
        if (memberRepository.findByMemberAccountIsLike(member.memberAccount) != null)
            throw RestException(HttpStatus.INTERNAL_SERVER_ERROR, "입력하신 ${member.memberAccount} 은 이미 가입한 계정입니다.")
    }

    fun checkEmail(member: Member) {
        if (memberRepository.findByMemberEmailIsLike(member.memberEmail) != null)
            throw RestException(HttpStatus.INTERNAL_SERVER_ERROR, "입력하신 ${member.memberEmail} 은 이미 가입한 이메일입니다.")
    }

//    Mono.fromSupplier {
//        val resultAccount = memberRepository.findByMemberAccountIsLike(member.memberAccount)
//
//        val resultEmail = memberRepository.findByMemberEmailIsLike(member.memberEmail)
//        if (resultAccount == null) {
//            if (resultEmail == null) {
//                memberRepository.save(member)
//            } else {
//                throw RestException(HttpStatus.INTERNAL_SERVER_ERROR, "입력하신 ${member.memberEmail} 은 이미 가입한 이메일입니다.")
//            }
//        } else {
//            throw RestException(HttpStatus.INTERNAL_SERVER_ERROR, "입력하신 ${member.memberAccount} 은 이미 가입한 계정입니다.")
//        }
//    }

    private fun checkMemberAccount(member: Member): Mono<Member> = Mono
            .fromSupplier {
                memberRepository.findByMemberAccountIsLike(member.memberAccount)
            }
            .onErrorResume {
                throw NotFoundMemberException(HttpStatus.NOT_FOUND)
            }

    private fun checkMemberEmail(member: Member): Mono<Member> = Mono
            .fromSupplier {
                memberRepository.findByMemberEmailIsLike(member.memberEmail)
            }
            .onErrorResume {
                throw NotFoundMemberException(HttpStatus.NOT_FOUND)
            }

    fun selectMemberAll(): Flux<Member> = Mono
            .fromSupplier {
                memberRepository.findAll()
            }
            .flatMapMany {
                if (it.isNotEmpty()) {
                    Flux.fromIterable(it)
                } else {
                    throw RestException(HttpStatus.NOT_FOUND, "멤버가 없습니다.")
                }
            }

    fun selectMemberAccount(
            memberEmail: String,
            memberName: String
    ): Mono<String> = Mono
            .fromSupplier {
                memberRepository.findByMemberEmailAndMemberNameEquals(memberEmail, memberName)
            }
            .flatMap {
                if (it != null) {
                    it.memberAccount.toMono()
                } else {
                    throw RestException(HttpStatus.NOT_FOUND, "$memberEmail , $memberName 으로 찾은 ID가 없습니다.")
                }
            }

    fun selectMemberPasswd(
            memberAccount: String,
            memberEmail: String
    ): Mono<String> = Mono
            .fromSupplier {
                memberRepository.findByMemberAccountIsLikeAndAndMemberEmailLike(memberAccount, memberEmail)
            }
            .flatMap {
                if (it != null) {
                    it.memberPasswd.toString().toMono()
                } else {
                    throw RestException(HttpStatus.NOT_FOUND, "$memberAccount, $memberEmail 으로 찾은 PASSWORD가 없습니다.")
                }
            }

}// 서비스 끝