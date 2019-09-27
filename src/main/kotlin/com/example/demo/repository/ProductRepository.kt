package com.example.demo.repository

import com.example.demo.domain.entity.Product
import org.hibernate.sql.Select
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository // JpaQuery
interface ProductRepository : JpaRepository<Product, Int>{ // interface 생성 JpaReopository 메소드를 받아서 씀 <type, primary key> Like

    fun findByMenuContaining(select : String): List<Product> // 포함된 글자 찾는 쿼리
    fun findByPriceLessThanEqual(selectPrice: Int): List<Product> // 파라미터 인트값 이하를 찾는 쿼리
    fun findByMenuContainingAndPriceLessThanEqual(selectMenu: String, selectPrice: Int) : List<Product> // 포함된 글자와 파라미터 인트 값 이하인 값을 찾는 쿼리
    fun findByMenuContainingAndPriceBetween(selectMenu: String, selectMinPrice: Int, selectMaxPrice: Int) : List<Product> // 포함된 글자와 원하는가격대 사이의 메뉴를 뽑아주는 쿼리

    // 실제로 delete문이 처리되는 곳..이아니라 이것은 그냥 함수정의 JPA쿼리 : 함수명으로 쿼리문을 작성할수있다.
    @Modifying //insert, update, delete 등의 메서드가 실행됐을 때 몇건의 데이터작업이 이루어졌는지 int 값으로 반환해줌
    fun deleteByProductId( // id값으로 delete쿼리 실행
            productId: Int // 매개변수
    ): Int // delete문 실행 건수
}