package com.example.demo.service

import com.example.demo.domain.entity.Product
import com.example.demo.repository.ProductRepository
import org.springframework.stereotype.Service

@Service // 서비스어노테이션
class ProductService( // 보통 entity 네임 뒤에 패키지이름
        val productRepository: ProductRepository // @autowired 가 자동으로 생성
) {
    fun getProductById(productId: Int): Product? { //함수생성 product를 셀렉함(받을 파라미터 예: String id == String타입의 id값을 매개변수로 받음)리턴타입 : product타입 ? null이 될수도있다. nullalbe
        return productRepository.findById(productId).orElseGet { null } //생성된 productRepository.적절한함수사용(받은 파라미터값).orElseGet { null } <=이건 try,catch 문이랑 비슷함 
    }

    fun getAllProduct(): List<Product>{ // insert 함수..
       return productRepository.findAll()
    }

    fun putProduct(product: Product): Product?{
        return productRepository.save(product)
    }
}