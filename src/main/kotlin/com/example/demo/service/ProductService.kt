package com.example.demo.service

import com.example.demo.domain.entity.Product
import com.example.demo.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.ConcurrentHashMap


@Service // 서비스어노테이션
class ProductService( // 보통 entity 네임 뒤에 패키지이름 @Autowire
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
        var target: Product= productRepository.findById(productId).get() //변수 target 생성 id값으로 product타입 빈객체를 대입
        target.menu=product.menu // update할 파라미터만 이어붙이는듯?
        target.price=product.price
        target.updatedAt=product.updatedAt
        productRepository.save(target) // target에 저장된 객체를 save
    }


    fun deleteProductById(productId: Int){
        productRepository.deleteById(productId)
    }

    @Transactional //Exception발생 시 rollback
    fun deleteProduct(product: Product): Product? {
        val productId = product.productId
        val result = productRepository.deleteByProductId(productId) // @Modifying이 반환해주는 결과값(실행된 건수 Int)을 변수에 넣고
        return if (result > 0) { //delete문이 실행이 됐으면 1이상 product를 리턴 안됐으면 0 null을 리턴
            product
        } else {
            null
        }
    }

    fun getMenu(select : String): List<Product>{
        return productRepository.findByMenuContaining(select)
    }

    fun getMenuAndPrice(selectMenu : String, selectPrice : Int): List<Product>{
        return productRepository.findByMenuStartingWithAndPriceGreaterThanEqual(selectMenu, selectPrice)
    }

    fun getPriceLessThen(selectPrice: Int): List<Product>{
        return productRepository.findByPriceLessThan(selectPrice)
    }
}
