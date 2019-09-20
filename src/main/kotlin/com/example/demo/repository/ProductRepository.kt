package com.example.demo.repository

import com.example.demo.domain.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository // query문 역할을함
interface ProductRepository : JpaRepository<Product, Int>{
// interface 생성 JpaReopository 메소드를 받아서 씀 <type, primary key>
}