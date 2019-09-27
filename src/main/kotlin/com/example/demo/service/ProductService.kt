package com.example.demo.service

import com.example.demo.domain.entity.Product
import com.example.demo.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono


@Service // 서비스어노테이션
class ProductService( // 보통 entity 네임 뒤에 패키지이름 @Autowire
        val productRepository: ProductRepository // @autowired 가 자동으로 생성
) { // productId로 Product객체를 select함

    fun getProductById(productId: Int): Mono<Product?> { //함수생성 product를 셀렉함(받을 파라미터 예: String id == String타입의 id값을 매개변수로 받음)리턴타입 : product타입 ? null이 될수도있다. nullalbe
        return Mono.fromSupplier {
            productRepository.findById(productId).orElseGet { null } //생성된 productRepository.적절한함수사용(받은 파라미터값).orElseGet { null } <=이건 try,catch 문이랑 비슷함
        }
    }

    // Product테이블의 모든 데이터 select함
    fun getAllProduct(): Mono<List<Product>> {
        return Mono.fromSupplier {
            productRepository.findAll()
        }
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
    fun deleteProduct(product: Mono<Product?>): Mono<Product?> {
        return product.map {
            productRepository.delete(it!!)
            it
        }


//        return if (result > 0) { //delete문이 실행이 됐으면 1이상 product를 리턴 안됐으면 0 null을 리턴
//            product
//        } else {
//            null
//        }
    }

    // ============== JPA쿼리 부분 시작=====================
    // menu이름이 containing 된 데이터를 List타입으로 select 함
    fun getMenu(selectMenu: String): List<Product> {
        return productRepository.findByMenuContaining(selectMenu)
    }

    // price가 파라미터 이하인 데이터를 List타입으로 select함
    fun getPriceLessThen(selectPrice: Int): List<Product> {
        return productRepository.findByPriceLessThanEqual(selectPrice)
    }

    // selectMenu가 contain되 있고 price이하인 데이터를 List타입으로 select함
    fun getMenuContainAndLessThenPrice(selectMenu: String, selectPrice: Int): List<Product> {
        return productRepository.findByMenuContainingAndPriceLessThanEqual(selectMenu, selectPrice)
    }

    // menu이름이 containing 되있고 ~~원에서 ~~원 사이의 데이터를 List타입으로 select함
    fun getMenuAndPriceBetween(selectMenu: String, selectMinPrice: Int, selectMaxPrice: Int): List<Product> {
        return productRepository.findByMenuContainingAndPriceBetween(selectMenu, selectMinPrice, selectMaxPrice)
    }

    // ============== JPA쿼리 부분 끝=====================
    // ============== 람다식 부분 시작=======================
    // lamda 교재 130쪽 예제응용
    fun lamdaMenu(selectMenu: String): List<Product> { //controller에서 가져온 reqeustParam 값
        var products = productRepository.findAll().toList() // 변수생성 후 필터시켜볼 테이블데이터를 list타입으로 받아서 담음
        return products.filter { it.menu.contains(selectMenu, true) }
        // 람다식으로 for문 처리 여기서 it은 자바의 this인듯 it이라는 테이블의 컬럼menu의 로우값이 selectMenu와 true(일치,생략가능)한다면 담는다
    }

    // 원하는 가격으로 메뉴 검색
    fun lamdaPrice(selectPrice: Int): List<Product> {
        var products = productRepository.findAll().toList()
        return products.filter { it.price.equals(selectPrice) }
    }

    // 원하는 메뉴이름과 원하는 가격으로 메뉴 검색
    fun lamdaMenuAndPrice(selectMenu: String, selectPrice: Int): List<Product> {
        var products = productRepository.findAll().toList()
        return products.filter { it.menu.contains(selectMenu) and it.price.equals(selectPrice) }
    }

    // 뭔가 깨달음 지금 쳐봐야함 ~~원 이상 메뉴 검색
    fun lamdaPriceGreaterThen(selectMinPrice: Int): List<Product> {
        var products = productRepository.findAll().toList()
        return products.filter { it.price >= selectMinPrice }
    }

    // ~~원 이하 메뉴 검색
    fun lamdaPriceLessThen(selectMaxPrice: Int): List<Product> {
        var products = productRepository.findAll().toList()
        return products.filter { it.price <= selectMaxPrice }
    }

    // ~~원과 ~~원 사이 메뉴 검색
    fun lamdaPriceBetween(selectPrice: Int, selectPrice2: Int): List<Product> {
        var products = productRepository.findAll().toList()
        return products.filter { it.price >= selectPrice }.filter { it.price <= selectPrice2 }
    }

    // 글자가 포함된 메뉴가 ~~원 이하인 메뉴 검색
    fun lamdaMenuContainAndLessThenPrice(selectMenu: String, selectMaxPrice: Int): List<Product> {
        var products = productRepository.findAll().toList()
//        return products.filter { it.menu.contains(selectMenu) }.filter { it.price <= selectMaxPrice }
        return products.asSequence().filter { it.menu.contains(selectMenu) }.filter { it.price <= selectMaxPrice }.toList()
        // 시퀀스 사용.. filter함수 하나당 filter된 리스트가 만들어짐 시퀀스를 사용하면 중간처리 결과를 저장하지않고 연산을 연쇄적으로 적용해서 효율적으로 계산을 사용 할수있음
    }

    // 글자가 포함된 메뉴가 ~~원 이상, ~~ 이하인 메뉴 검색
    fun lamdaMenuContainAndPriceBetween(selectMenu: String, selectMinPrice: Int, selectMaxPrice: Int): List<Product> {
        var products = productRepository.findAll().toList()
        return products.filter { it.menu.contains(selectMenu) }.filter { it.price >= selectMinPrice }.filter { it.price <= selectMaxPrice }
    }
    // ============== 람다식 부분 끝=======================

}// service끝
