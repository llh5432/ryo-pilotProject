package com.example.demo.repository

import com.example.demo.domain.entity.Product
import com.example.demo.domain.Enum.TypeMenu
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Int>{// interface에 미리 JpaReopository메서드를 정의해둔것   <entityClass, pk>

    fun findByMenuContaining(select : String): List<Product> // 포함된 글자 찾는 쿼리
    fun findByMenuTypeEquals(selectMenu: String): Int

    fun findByMenuTypeEquals(selectMenuType: TypeMenu): List<Product> // 메뉴타입으로 찾음
    fun findByMenuTypeEqualsAndMenuContaining(selectMenuType: TypeMenu, selectMenu: String): List<Product>// 메뉴타입 + 메뉴포함 찾기
    fun findByMenuTypeEqualsAndPriceGreaterThanEqual(selectMenuType: TypeMenu, selectMinPrice: Int): List<Product> // 메뉴타입 + ~원 이상
    fun findByMenuTypeEqualsAndPriceLessThanEqual(selectMenuType: TypeMenu, selectMaxPrice: Int): List<Product> // 메뉴타입 + ~원 이하

    fun findByPriceGreaterThanEqual(selectPrice: Int): List<Product> // 파라미터 ~값 이상을 찾는 쿼리
    fun findByPriceLessThanEqual(selectPrice: Int): List<Product> // 파라미터 ~값 이하를 찾는 쿼리
    fun findByPriceBetween(selectMinPrice: Int, selectMaxPrice: Int): List<Product> //파라미터 최소 ~값 이상 최대 ~값 이하 사이를 찾는 쿼리

    fun findByMenuTypeEqualsAndMenuContainingAndPriceBetween(selectMenuType: TypeMenu, selectMenu: String, selectMinPrice: Int, selectMaxPrice: Int): List<Product>

    fun findByProductId(productId : List<Int>): List<Product>

//    @Modifying //이 어노테이션은 insert, update, delete 메서드가 실행됐을 때 몇건의 데이터처리가 실행되었는지를 int 값으로 return해줌
}