package com.example.demo.service

import com.example.demo.domain.entity.Member
import com.example.demo.exception.RestException
import com.example.demo.repository.MemberRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Service
import org.springframework.web.util.HttpSessionMutexListener
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono

@Service
class MemberService(
        val memberRepository: MemberRepository
) {

    fun createMember(member: Member): Mono<Member> =
            member.toMono() // 바로 .toMono로 리액터객체로 만듦 member를 데이터처리할거니깐
                    .doOnNext(this::checkAccount) // 파리미터 값 member가 성공적일 때 다음 행동을 함
                    .doOnNext(this::checkEmail) // this란 해당 클래스를 의미함 this클래스의 checkEmail 함수를 사용하는것을 행동(.doOnNext)한다.
//                    .map(memberRepository::save)  유형 유추실패 유추할 정보가 충분하지않다는 에러 발생
                    .map { memberRepository.save(it) }


    fun checkAccount(member: Member) { // Id값만 체크하는 funtion
        if (memberRepository.findByMemberAccountIsLike(member.memberAccount) != null)
            throw RestException(HttpStatus.INTERNAL_SERVER_ERROR, "입력하신 ${member.memberAccount} 은 이미 가입한 계정입니다.")
    }

    fun checkEmail(member: Member) { // Email값만 체크하면 funtion
        if (memberRepository.findByMemberEmailIsLike(member.memberEmail) != null)
            throw RestException(HttpStatus.INTERNAL_SERVER_ERROR, "입력하신 ${member.memberEmail} 은 이미 가입한 이메일입니다.")
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
    ): Mono<String> {
        val account = memberRepository.findByMemberEmailAndMemberNameEquals(memberEmail, memberName)
        if (account == null) {
            throw RestException(HttpStatus.NOT_FOUND, "$memberEmail , $memberName 으로 찾은 ID가 없습니다.")
        } else {
            return account.memberAccount.toMono()
        }
    }

    fun selectMemberPasswd(
            memberAccount: String,
            memberEmail: String
    ): Mono<String> {
        return Mono.fromSupplier {
            memberRepository.findByMemberAccountIsLikeAndAndMemberEmailLike(memberAccount, memberEmail)
                    ?.memberPasswd
                    ?: throw RestException(HttpStatus.NOT_FOUND, "$memberAccount, $memberEmail 으로 찾은 PASSWORD가 없습니다.")
        }
    }



}// 서비스 끝