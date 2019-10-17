package com.example.demo.service

import com.example.demo.domain.entity.Product
import com.example.demo.domain.Enum.MenuType
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

    fun readAll(): Mono<List<Product>> = Mono
            .fromSupplier {
                productRepository.
            }.map {
                if (it.isNotEmpty()) {
                    it
                } else {
                    throw RestException(HttpStatus.NOT_FOUND, "아직 리스트가 없습니다.")
                }
            }

    fun readMenuEqual(menuType: MenuType): Flux<Product> = Mono
            .fromSupplier {
                 productRepository.findByMenuTypeEquals(menuType)
            }
            .flatMapMany {
                if (it.isNotEmpty()) {
                    Flux.fromIterable(it)
                }else {
                    throw RestException(HttpStatus.NOT_FOUND,"아직 생성된 리스트가 없습니다.")
                }
            }

    fun readProductById(productId: Int): Mono<Product> = Mono
            .fromSupplier {
                    productRepository.findById(productId)
                            .orElseThrow { RestException(HttpStatus.NOT_FOUND, "찾는 메뉴가 없습니다.") }
                            // 찾는 값이 있다면 리턴하고 찾는 값이 없으면 {}이 안의 내용물을 던지라는 것인듯
            }


    fun createProduct(product: Product): Mono<Product> = Mono
            .fromSupplier {
                productRepository.findByMenuEquals(product.menu) //DB 전체를 찾지말고 그냥 바로 INSERT하는 메뉴이름만 뽑아서 찾아냄
            }.map {
                if (it == 0) {
                    productRepository.save(product)
                } else {
                    throw RestException(HttpStatus.ALREADY_REPORTED, "이미 생성된 메뉴입니다. menu : ${product.menu}")
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


    fun readMenu(selectMenu: String): Flux<Product> = Mono // menu이름이 containing 된 데이터를 List타입으로 select 함
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
            selectMenuType: MenuType,
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
            selectMenuType: MenuType,
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
            selectMenuType: MenuType,
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
            selectMenuType: MenuType,
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
