package com.example.demo.service

import com.example.demo.domain.entity.Product
import com.example.demo.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service // 서비스어노테이션
class ProductService( // 보통 entity 네임 뒤에 패키지이름 @Autowire
        val productRepository: ProductRepository // @autowired 가 자동으로 생성
) { // productId로 Product객체를 select함
    fun getProductById(productId: Int): Product? { //함수생성 product를 셀렉함(받을 파라미터 예: String id == String타입의 id값을 매개변수로 받음)리턴타입 : product타입 ? null이 될수도있다. nullalbe
        return productRepository.findById(productId).orElseGet { null } //생성된 productRepository.적절한함수사용(받은 파라미터값).orElseGet { null } <=이건 try,catch 문이랑 비슷함 
    }

    // Product테이블의 모든 데이터 select함
    fun getAllProduct(): List<Product> {
        return productRepository.findAll()
    }

    // 매개변수로 받은 product 객체를 테이블에 insert함
    fun createProduct(product: Product): Product {
        return productRepository.save(product)
    }

    // update할 productId값과 update할 파라미터를 매개변수로 받아서 update함
    fun updateProduct(productId: Int, product: Product) {
        var target: Product = productRepository.findById(productId).get() //변수 target 생성 id값으로 product타입 빈객체를 대입
        target.menu = product.menu // update할 파라미터만 이어붙이는듯?
        target.price = product.price
        target.updatedAt = product.updatedAt
        productRepository.save(target) // target에 저장된 객체를 save
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

    // menu이름이 containing 된 데이터를 List타입으로 select 함
    fun getMenu(select: String): List<Product> {
        return productRepository.findByMenuContaining(select)
    }

    // menu이름이 containing 되있고 price가 파라미터 이상인 데이터를 List타입으로 select함
    fun getMenuAndPrice(selectMenu: String, selectPrice: Int): List<Product> {
        return productRepository.findByMenuStartingWithAndPriceGreaterThanEqual(selectMenu, selectPrice)
    }

    // price가 파라미터 이하인 데이터를 List타입으로 select함
    fun getPriceLessThen(selectPrice: Int): List<Product> {
        return productRepository.findByPriceLessThanEqual(selectPrice)
    }

    // lamda 교재 130쪽 예제응용
    fun lamdaMenu(selectMenu: String): List<Product> { //controller에서 가져온 reqeustParam 값
        var products = productRepository.findAll().toList() // 변수생성 후 필터시켜볼 테이블데이터를 list타입으로 받아서 담음
        return products.filter { it.menu.contains(selectMenu, true) }
        // 람다식으로 for문 처리 여기서 it은 자바의 this인듯 it이라는 테이블의 컬럼menu의 로우값이 selectMenu와 true(일치)한다면 담는다
    }
    // 원하는 가격 메뉴찾기
    fun lamdaPrice(selectPrice: Int): List<Product> {
        var products = productRepository.findAll().toList()
        return products.filter { it.price.equals(selectPrice) }
    }
    // 원하는 메뉴이름과 가격인 로우찾기
    fun lamdaMenuAndPrice(selectMenu: String, selectPrice: Int): List<Product>{
        var products = productRepository.findAll().toList()
        return products.filter { it.menu.contains(selectMenu)and it.price.equals(selectPrice) }
    }
    // 뭔가 깨달음 지금 쳐봐야함 5000원 이상 음식검색
    fun lamdaPriceGreaterThen5000(selectPrice: Int): List<Product>{
        var products = productRepository.findAll().toList()
        return products.filter { it.price >= selectPrice }
    }
    // ~~원 이하 음식검색
    fun lamdaPriceLessThen5000(selectPrice: Int): List<Product>{
        var products = productRepository.findAll().toList()
        return products.filter { it.price <= selectPrice }
    }
    // ~~원과 ~~원 사이 음식검색
    fun lamdaPriceBetween(selectPrice: Int, selectPrice2: Int): List<Product>{
        var products = productRepository.findAll().toList()
        return products.filter { it.price >= selectPrice}.filter { it.price <= selectPrice2 }
    }
    
}// service끝
