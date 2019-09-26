package com.example.demo

import com.example.demo.domain.entity.Person
import com.example.demo.domain.entity.Product
import com.example.demo.domain.entity.animal
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.stream.Stream

@SpringBootApplication
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)

    // 람다 식 기초 예제
    val sum = { x: Int, y: Int -> x + y }
    println(sum(1, 3))

    // 방법 1)
    var people = listOf(
            Person("bob", 22, 1004), Person("alice", 24, 1502), Person("dave", 32, 1502))
    var olderPerson = people.maxBy { it.age }
    println("나이젤많아 :" + olderPerson)

    // 방법 2)
    println("나이제일많아:" + people.maxBy({ p: Person -> p.age }))
    // maxBy 인자로 람다 식을 그대로 넘김 그래서 괄호안에 중괄호(람다 식)이 있음

    // 방법 3) 인자가 제일 마지막에 있다면 괄호밖으로 빼도 됨
    println("나이제일루많아:" + people.maxBy() { p: Person -> p.age })

    // 방법 4) 가장 가독성이 좋다고 합니다.
    println("나이제일루다많아:" + people.maxBy { p: Person -> p.age })

    // 이름 붙인 인자 사용 람다넘기기
    val people2 = listOf(Person("이몽룡", 29, 123213), Person("성춘향", 31, 123213))
    println(people2.joinToString(" ") { p: Person -> p.name })

    // 람다 파라미터타입 제거하기 컴파일러가 알아서 추론가능 p = Person 근디 가독성이 떨어지는거아닌가?
    println("파라미터타입제거 : " + people.maxBy { p -> p.age })
    // 파라미터타입 제거하고 컴파일러가 읽지못하는 경우에는 타입을 명시

    // 디폴트 파라미터 이름 it 남발하는경우에는 가독성이 떨어짐
    println("it 사용하기 : " + people.maxBy { it.age })

    val sum2 = { x: Int, y: Int ->
        println("Computing the sum of $x and $y...")
        x + y
    }
    println(sum2(1, 2))

    // 함수의 파라미터를 람다 안에서 사용하기
    fun printMessagesWithPrefix(massage: Collection<String>, prefix: String) {
        massage.forEach {
            println("$prefix $it")
        }
    }

    val errors = listOf("403 Forbidden", "404 Not Found")
    printMessagesWithPrefix(errors, "Error:")

    fun printMessageTest(test1: Collection<String>, test2: String) {
        test1.forEach {
            println("$test2 $test1")
        }
    }

    var sports = listOf("농구", "야구", "축구", "배구", "골프")
    printMessageTest(sports, "스포츠 : ")
    // 람다 안에서 람다 밖의 변수 값을 바꿀 수 있다. == 당연한거아닌가..아닌가..? 코틀린에서는 가능한데 자바에선 불가능한듯보임
    //
    fun printProblemCounts(response: Collection<String>) {
        var clientErrors = 0 // 현재 변수값 0
        var serverErrors = 0 // 현재 변수값 0
        response.forEach {
            if (it.startsWith("4")) { // it(매개변수의 값)의 첫자리가 4로시작한다면
                clientErrors++
            } else if (it.startsWith("5")) {
                serverErrors++
            } // ifelse 끝
        }
        println("$clientErrors client errors, $serverErrors server errors")

    }// 함수 끝
    val response = listOf("200 OK", "418 I'm a teapot", "500 Internal Server Error")
    printProblemCounts(response)


    // stream , 람다 식 사용예제
    var list = listOf("홍길동","신용권","감자바") // 리스트 생성
    fun streamTest(list: List<String>){ // 리스트 인자 받는 함수 생성
        var str: Stream<String> = list.stream() // str 변수안에 스트링타입의 list.stream을 담음
         str.forEach { println(it.find { true }) } // 람다식 사용 find 필터 사용 참조하는 인자들 중 제일 첫글자 필터링
}
    streamTest(list) // 함수호출
    // stream, 람다 식 사용예제2
    var str2 = list.stream()
    str2.forEach { name-> println(name) } // name은 list.value값들의 변수생성 단순화 코드가 훨씬간결해짐
    
    println(list.filter { it == "홍길동" }) //filter는 컬렉션을 이터레이션을하면서 주어진 람다의 값이 true하는 것만 필터링함
    // filter는 원소를 반환해도 변환시킬순없음 그래서 쓰는게 map

    var list2 = listOf(1,2,3,4,5) 
    println(list2.map { it*it }) // filter는 트루되는 값만 반환 이런거 안됨


    var people3 = listOf(Person("나야나",13,400), Person("응그래",22,1302),Person("맞아용",32,5020),Person("그래마자용",32,5555))
    val maxAge = people3.maxBy { it.age}!!.age
    println(people3.filter { it.age == maxAge })

}