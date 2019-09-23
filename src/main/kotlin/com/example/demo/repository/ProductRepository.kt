package com.example.demo.repository

import com.example.demo.domain.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository // query문 역할을함
interface ProductRepository : JpaRepository<Product, Int>{
// interface 생성 JpaReopository 메소드를 받아서 씀 <type, primary key>


    @Modifying //insert, update, delete 등의 메서드가 실행됐을 때 몇건의 데이터작업이 이루어졌는지 int 값으로 반환해줌
    fun deleteByProductId( // id값으로 delete쿼리 실행
            productId: Int // 매개변수
    ): Int
}