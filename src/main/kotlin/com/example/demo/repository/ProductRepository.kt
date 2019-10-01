package com.example.demo.repository

import com.example.demo.domain.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Int>{
// interface에 미리 JpaReopository메서드를 정의해둔것   <entityClass, pk>
    fun findByMenuContaining(select : String) : List<Product> // 포함된 글자 찾는 쿼리
    fun findByPriceLessThanEqual(selectPrice: Int) : List<Product> // 파라미터 인트값 이하를 찾는 쿼리
    fun findByMenuContainingAndPriceLessThanEqual(selectMenu: String, selectPrice: Int) : List<Product> // 포함된 글자와 파라미터 인트 값 이하인 값을 찾는 쿼리
    fun findByMenuContainingAndPriceBetween(selectMenu: String, selectMinPrice: Int, selectMaxPrice: Int) : List<Product> // 포함된 글자와 원하는가격대 사이의 메뉴를 뽑아주는 쿼리

    @Modifying //이 어노테이션은 insert, update, delete 메서드가 실행됐을 때 몇건의 데이터처리가 실행되었는지를 int 값으로 return해줌
    fun deleteByProductId(productId: Int) : Int
}