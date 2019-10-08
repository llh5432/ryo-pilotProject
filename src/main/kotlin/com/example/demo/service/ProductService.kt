package com.example.demo.service

import com.example.demo.domain.entity.Product
import com.example.demo.domain.entity.TypeMenu
import com.example.demo.exception.RestException
import com.example.demo.repository.ProductRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toMono


@Service // 서비스어노테이션
class ProductService( // 보통 entity 네임 뒤에 패키지이름 @Autowire
     val productRepository: ProductRepository // @autowired 가 자동으로 생성
) {

    fun getMenuEqual(selectMenuType: TypeMenu): Flux<Product> = Mono
            .fromSupplier {
                 productRepository.findByMenuTypeEquals(selectMenuType)
            }
            .flatMapMany {
                if (it.isNotEmpty()) {
                    Flux.fromIterable(it)
                }else {
                    throw RestException(HttpStatus.NOT_FOUND,"아직 생성된 리스트가 없습니다.")
                }
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
                 productRepository.findByMenuContaining(product.menu) //DB 전체를 찾지말고 그냥 바로 INSERT하는 메뉴이름만 뽑아서 찾아냄
//                    .filter { it.menu == product.menu } 이렇게하면 필터를 할 필요가 없음
            }.flatMap {//map 은 안됨
                if (it.isEmpty()){ // 뽑아낸 it(insert할 menu이름)이 없으면
                    productRepository.save(product).toMono() // 저장
                }else { // 있다면
                    throw RestException(HttpStatus.BAD_REQUEST,"${product.menu} 는 이미 있습니다.") // exception 발생
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
    fun getMenu(selectMenu: String): Flux<Product> = Mono // menu이름이 containing 된 데이터를 List타입으로 select 함
                    .fromSupplier {
                        productRepository.findByMenuContaining(selectMenu)
                    }
            .flatMapMany {//flatMapMany라는 연산자를 사용하면 하나의 값으로부터 여러 개의 값을 취급하는 Flux를 리턴할 수 있는 환경을 만들어줌
                if (it.isNotEmpty()) {
                    Flux.fromIterable(it)// fromIterable은 iterable type의 인자를 넘기면 (it : products<List>) flux로 변환해서 리턴함
                } else {
                    throw RestException(HttpStatus.NOT_FOUND, "$selectMenu 를 포함한 메뉴가 없습니다.")
                }
            }


    fun priceGreaterThen(selectMinPrice: Int): Flux<Product> = Mono
            .fromSupplier {
                productRepository.findByPriceGreaterThanEqual(selectMinPrice)
            }
            .flatMapMany {
                if (it.isNotEmpty()) {
                    Flux.fromIterable(it)
                }else {
                    throw RestException(HttpStatus.NOT_FOUND, "$selectMinPrice 원 이상의 메뉴가 없습니다.")
                }
            }

    fun priceLessThen(selectMaxPrice: Int): Flux<Product> = Mono
            .fromSupplier {
                productRepository.findByPriceLessThanEqual(selectMaxPrice)
            }
            .flatMapMany {
                if (it.isNotEmpty()) {
                    Flux.fromIterable(it)
                }else {
                    throw RestException(HttpStatus.NOT_FOUND, "$selectMaxPrice 원 이하의 메뉴가 없습니다.")
                }
            }

    fun priceBetween(
            selectMinPrice: Int,
            selectMaxPrice: Int): Flux<Product> = Mono
                .fromSupplier {
                    productRepository.findByPriceBetween(selectMinPrice, selectMaxPrice)
                }
                .flatMapMany {
                    if (it.isNotEmpty()) {
                        Flux.fromIterable(it)
                    }else {
                        throw RestException(HttpStatus.NOT_FOUND, "$selectMinPrice 원 이상 $selectMaxPrice 원 이하의 메뉴가 없습니다.")
                    }
                }

    fun typeMenuAndMenuContain(
            selectMenuType: TypeMenu,
            selectMenu: String
    ): Flux<Product> = Mono
            .fromSupplier {
                productRepository.findByMenuTypeEqualsAndMenuContaining(selectMenuType, selectMenu)
            }
            .flatMapMany {
                if (it.isNotEmpty()) {
                    Flux.fromIterable(it)
                }else {
                    throw RestException(HttpStatus.NOT_FOUND, "$selectMenuType , $selectMenu 포함된 메뉴가 없습니다.")
                }
            }

    fun typeMenuAndPriceGreaterThen(
            selectMenuType: TypeMenu,
            selectMinPrice: Int
    ): Flux<Product> = Mono
            .fromSupplier {
                productRepository.findByMenuTypeEqualsAndPriceGreaterThanEqual(selectMenuType, selectMinPrice)
            }
            .flatMapMany {
                if (it.isNotEmpty()) {
                    Flux.fromIterable(it)
                }else {
                    throw RestException(HttpStatus.NOT_FOUND, "$selectMenuType, $selectMinPrice 원 이상인 메뉴가 없습니다.")
                }
            }

    fun typeMenuAndPriceLessThen(
            selectMenuType: TypeMenu,
            selectMaxPrice: Int
    ): Flux<Product> = Mono
            .fromSupplier {
                productRepository.findByMenuTypeEqualsAndPriceLessThanEqual(selectMenuType, selectMaxPrice)
            }
            .flatMapMany {
                if (it.isNotEmpty()) {
                    Flux.fromIterable(it)
                }else {
                    throw RestException(HttpStatus.NOT_FOUND, "$selectMenuType, $selectMaxPrice 원 이하인 메뉴가 없습니다.")
                }
            }

    fun menuTypeEqualAndMenuContainAndPriceBtw( // 메뉴타입 eq 메뉴 contain 가격 btw 안쓰임
            selectMenuType: TypeMenu,
            selectMenu: String,
            selectMinPrice: Int,
            selectMaxPrice: Int
    ): Flux<Product> = Mono
            .fromSupplier {
                productRepository.findByMenuTypeEqualsAndMenuContainingAndPriceBetween(selectMenuType, selectMenu, selectMinPrice, selectMaxPrice)
            }.flatMapMany {
                if (it.isNotEmpty()) {
                    Flux.fromIterable(it)
                }else {
                    throw RestException(HttpStatus.NOT_FOUND, "타입이 $selectMenuType , 메뉴에 $selectMenu 가 포함, $selectMinPrice ~ $selectMaxPrice 원 메뉴가 없습니다.")
                }
            }

}// service끝
