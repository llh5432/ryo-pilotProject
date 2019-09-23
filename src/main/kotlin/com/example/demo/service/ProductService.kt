package com.example.demo.service

import com.example.demo.domain.entity.Product
import com.example.demo.repository.ProductRepository
import com.sun.xml.internal.bind.v2.TODO
import org.omg.CORBA.Object
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service // 서비스어노테이션
class ProductService( // 보통 entity 네임 뒤에 패키지이름
        val productRepository: ProductRepository // @autowired 가 자동으로 생성
) {
    fun getProductById(productId: Int): Product? { //함수생성 product를 셀렉함(받을 파라미터 예: String id == String타입의 id값을 매개변수로 받음)리턴타입 : product타입 ? null이 될수도있다. nullalbe
        return productRepository.findById(productId).orElseGet { null } //생성된 productRepository.적절한함수사용(받은 파라미터값).orElseGet { null } <=이건 try,catch 문이랑 비슷함 
    }

    fun getAllProduct(): List<Product>{
       return productRepository.findAll()
    }

    fun createProduct(product: Product): Product{
      return productRepository.save(product)
    }

    fun updateProduct(productId: Int, product: Product){
        var target: Product= productRepository.findById(productId).get() //
        target.menu=product.menu
        target.price=product.price
        target.updatedAt=product.updatedAt
        //target이 model 역할? tartget이라는 함수를 이용해서 하나하나 이어붙임 파라미터를
        productRepository.save(target)
    }


    fun deleteProductById(productId: Int){
        productRepository.deleteById(productId)
    }

    @Transactional
    fun deleteProduct(product: Product): Product? {
        val productId = product.productId
        val result = productRepository.deleteByProductId(productId) // @Modifying이 반환해주는 결과값을 변수에 넣고
        return if (result > 0) { //return에 조건절 delete 값이있으면 product를 return 없으면 null
            product
        } else {
            null
        }
    }


}