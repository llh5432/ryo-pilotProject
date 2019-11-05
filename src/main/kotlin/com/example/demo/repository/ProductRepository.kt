package com.example.demo.repository

import com.example.demo.domain.entity.Product
import com.example.demo.domain.Enum.MenuType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Int>{// interface에 미리 JpaReopository메서드를 정의해둔것   <entityClass, pk>

    fun findByMenuContaining(select : String): List<Product> // 포함된 글자 찾는 쿼리
    fun findByMenuEquals(menu: String): List<Product>
    fun findAllByOrderByCreatedAtDesc(): List<Product>
    fun findByMenuTypeEqualsOrderByPriceAsc(selectMenuType: MenuType): List<Product> // 메뉴타입으로 찾음
    fun findAllByOrderByPriceAsc(): List<Product>


//    @Modifying //이 어노테이션은 insert, update, delete 메서드가 실행됐을 때 몇건의 데이터처리가 실행되었는지를 int 값으로 return해줌
}