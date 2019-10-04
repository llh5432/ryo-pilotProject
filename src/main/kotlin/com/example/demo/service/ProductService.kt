package com.example.demo.service

import com.example.demo.domain.entity.Product
import com.example.demo.domain.entity.TypeMenu
import com.example.demo.exception.RestException
import com.example.demo.repository.ProductRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux
import reactor.core.publisher.toMono


@Service // 서비스어노테이션
class ProductService( // 보통 entity 네임 뒤에 패키지이름 @Autowire
     val productRepository: ProductRepository // @autowired 가 자동으로 생성
) {

    fun getMenuEqual(selectMenuType: TypeMenu): Mono<List<Product>> = Mono
            .fromSupplier {
                 productRepository.findAll()
                    .filter { it.menuType == selectMenuType}
            }

    fun getProductById(productId: Int): Mono<Product> = Mono
            .fromSupplier {
                    productRepository.findById(productId)
                            .orElseThrow { RestException(HttpStatus.NOT_FOUND, "찾는 메뉴가 없습니다.") }
                            // 찾는 값이 있다면 리턴하고 찾는 값이 없으면 {}이 안의 내용물을 던지라는 것인듯
            }

    fun getAllProduct(): Flux<Product> = Mono
            .fromSupplier {
                productRepository.findAll()
            }.flatMapMany { Flux.fromIterable(it) } // flatMapMany는 Mono를 Flux로 바꿔주는 역할을 하는듯 하고 fromIterable 은 파라미터인 it을 flux로 생성해줌 반복해서



    fun createProduct(product: Product): Mono<Product> = Mono
            .fromSupplier {
                var products = productRepository.findAll()
                    products.filter { it.menu == product.menu }
            }.flatMap {//map 은 안됨
                if (it.isEmpty()){
                    productRepository.save(product).toMono()
                }else {
                    throw RestException(HttpStatus.BAD_REQUEST,"이미 있음")
                }
            }

    fun updateProduct(
            productId: Int,
            product: Product) {
                var target: Product = productRepository.findById(productId).get() //변수 target 생성 id값으로 product타입 빈객체를 대입
                Mono.just(target)
                    target.menuType= product.menuType
                    target.price = product.price
                    target.updatedAt = product.updatedAt
                productRepository.save(target) // target에 저장된 객체를 save
            }

    fun deleteProduct(product: Mono<Product>): Mono<Product> = product
                    .map {
                        productRepository.delete(it!!)
                        it
                    }

    // ============== JPA Repository쿼리 ====================
    fun getMenu(selectMenu: String): Mono<List<Product>> = Mono   // menu이름이 containing 된 데이터를 List타입으로 select 함
                    .fromSupplier {
                        productRepository.findByMenuContaining(selectMenu)

                    }



    fun getPriceLessThen(selectPrice: Int): Mono<List<Product>> = Mono   // price가 파라미터 이하인 데이터를 List타입으로 select함
                .fromSupplier {
                    productRepository.findByPriceLessThanEqual(selectPrice)
                }

    fun getMenuContainAndLessThenPrice(
            selectMenu: String,
            selectPrice: Int
    ): Mono<List<Product>> = Mono
        .fromSupplier {
            productRepository.findByMenuContainingAndPriceLessThanEqual(selectMenu, selectPrice)
        }

    fun getMenuAndPriceBetween(
            selectMenu: String,
            selectMinPrice: Int,
            selectMaxPrice: Int
    ): Mono <List<Product>> = Mono
            .fromSupplier {
                productRepository.findByMenuContainingAndPriceBetween(selectMenu, selectMinPrice, selectMaxPrice)
            }

    // ============== JPA Repository 끝=====================
    // stream 함수 응용
    fun streamMenu(selectMenu: String): Flux<Product> = Mono
            .fromSupplier {
                var products = productRepository.findAll()
                    products.filter { it.menu.contains(selectMenu, true) }//default true, 생략가능
            }.flatMapMany {//  flatMapMany라는 연산자를 사용하면 하나의 값으로부터 여러 개의 값을 취급하는 Flux를 리턴할 수 있는 환경을 만들어줌
                if (it.isNotEmpty()) { // it(products)가 비어있지않다면
                    Flux.fromIterable(it) // fromIterable은 iterable type의 인자를 넘기면 (it : products<List>) flux로 변환해서 리턴함
                } else{ // 비어있다면
                    throw RestException(HttpStatus.NOT_FOUND,"그런 메뉴는 우리식당에 없어") //RestException을 던져라
                }
                
            }


    // 원하는 가격으로 메뉴 검색
    fun streamPrice(selectPrice: Int): Mono<List<Product>> = Mono
            .fromSupplier {
                productRepository.findAll()
                    .filter { it.price == selectPrice }
            }

    // 원하는 메뉴이름과 원하는 가격으로 메뉴 검색
    fun streamMenuAndPrice(
            selectMenu: String,
            selectPrice: Int
    ): Mono<List<Product>> = Mono
            .fromSupplier {
                 productRepository.findAll()
                    .filter { it.menu.contains(selectMenu, true) and (it.price == selectPrice) }
            }

    fun streamPriceGreaterThen(selectMinPrice: Int): Mono<List<Product>> = Mono
            .fromSupplier {
                var products = productRepository.findAll()
                products.filter { it.price >= selectMinPrice }
            }

    fun streamPriceLessThen(selectMaxPrice: Int): Mono<List<Product>> = Mono
            .fromSupplier {
                productRepository.findAll()
                    .filter { it.price <= selectMaxPrice }
            }

    fun streamPriceBetween(
            selectPrice: Int,
            selectPrice2: Int): Mono<List<Product>> = Mono
                .fromSupplier {
                    productRepository.findAll()
                        .filter { (it.price >= selectPrice) and (it.price <= selectPrice2) }
                }

    fun streamMenuContainAndLessThenPrice(
            selectMenu: String,
            selectMaxPrice: Int): Mono<List<Product>> = Mono
                .fromSupplier {
                    productRepository.findAll()
                            .filter { it.menu.contains(selectMenu) and (it.price <= selectMaxPrice) }
                }

    // 글자가 포함된 메뉴가 ~~원 이상, ~~ 이하인 메뉴 검색
    fun streamMenuContainAndPriceBetween(
            selectMenu: String,
            selectMinPrice: Int,
            selectMaxPrice: Int
    ): Mono<List<Product>> = Mono
            .fromSupplier {
                    productRepository.findAll()
                        .filter { it.menu.contains(selectMenu) and (it.price >= selectMinPrice) and (it.price <= selectMaxPrice)}

            }

}// service끝
