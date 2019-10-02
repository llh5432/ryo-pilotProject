package com.example.demo.service

import com.example.demo.domain.entity.Product
import com.example.demo.domain.entity.TypeFood
import com.example.demo.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.toFlux
import reactor.core.publisher.toMono


@Service // 서비스어노테이션
class ProductService( // 보통 entity 네임 뒤에 패키지이름 @Autowire
     val productRepository: ProductRepository // @autowired 가 자동으로 생성
) {

    fun getMenuEqual(selectMenuType: TypeFood): Mono<List<Product>> = Mono
            .fromSupplier {
                var products = productRepository.findAll().toList()
                products.filter { it.menuType.equals(selectMenuType)}
            }

    fun getProductById(productId: Int): Mono<Product?> = Mono
            .fromSupplier {
                productRepository.findById(productId).orElseGet { null } //생성된 productRepository.적절한함수사용(받은 파라미터값).orElseGet { null } <=이건 try,catch 문이랑 비슷함
            }

    fun getAllProduct(): Flux<Product> = productRepository.findAll().toFlux()

    fun createProduct(product: Product): Mono<Product> = Mono
                .fromSupplier {
                productRepository.save(product)
                }

    fun updateProduct(
            productId: Int,
            product: Product) {
                var target: Product = productRepository.findById(productId).get() //변수 target 생성 id값으로 product타입 빈객체를 대입
                Mono.just(target)
                    target.menuType= product.menuType
                    target.menu= product.menu // update할 파라미터만 이어붙이는듯?
                    target.price = product.price
                    target.updatedAt = product.updatedAt
                    productRepository.save(target) // target에 저장된 객체를 save
            }

    fun deleteProduct(product: Mono<Product?>): Mono<Product?> = product
                    .map {
                        productRepository.delete(it!!)
                        it
                    }

    // ============== JPA Repository쿼리 ====================
    fun getMenu(selectMenu: String): Mono<List<Product>> = Mono   // menu이름이 containing 된 데이터를 List타입으로 select 함
                    .fromSupplier {
                        productRepository.findByMenuContaining(selectMenu)
                    }

    fun getPriceLessThen(selectPrice: Int): Mono<List<Product?>> = Mono   // price가 파라미터 이하인 데이터를 List타입으로 select함
                .fromSupplier {
                    productRepository.findByPriceLessThanEqual(selectPrice)
                }

    fun getMenuContainAndLessThenPrice(
            selectMenu: String,
            selectPrice: Int
    ): Mono<List<Product?>> = Mono
        .fromSupplier {
            productRepository.findByMenuContainingAndPriceLessThanEqual(selectMenu, selectPrice)
        }

    fun getMenuAndPriceBetween(
            selectMenu: String,
            selectMinPrice: Int,
            selectMaxPrice: Int
    ): Mono <List<Product?>> = Mono
            .fromSupplier {
                productRepository.findByMenuContainingAndPriceBetween(selectMenu, selectMinPrice, selectMaxPrice)
            }

    // ============== JPA Repository 끝=====================
    // stream 함수 응용
    fun streamMenu(selectMenu: String): Mono<List<Product?>> = Mono
            .fromSupplier {
                var products = productRepository.findAll().toList() // 변수생성 후 필터시켜볼 테이블데이터를 list타입으로 받아서 담음
                products.filter { it.menu.contains(selectMenu, true) }//default true, 생략가능
            }


    // 원하는 가격으로 메뉴 검색
    fun streamPrice(selectPrice: Int): Mono<List<Product?>> = Mono
            .fromSupplier {
                var products = productRepository.findAll().toList()
                products.filter { it.price.equals(selectPrice) }
            }

    // 원하는 메뉴이름과 원하는 가격으로 메뉴 검색
    fun streamMenuAndPrice(
            selectMenu: String,
            selectPrice: Int
    ): Mono<List<Product?>> = Mono
            .fromSupplier {
                var products = productRepository.findAll().toList()
                products.filter { it.menu.contains(selectMenu) and it.price.equals(selectPrice) }
            }

    fun streamPriceGreaterThen(selectMinPrice: Int): Mono<List<Product?>> = Mono
            .fromSupplier {
                var products = productRepository.findAll().toList()
                products.filter { it.price >= selectMinPrice }
            }

    fun streamPriceLessThen(selectMaxPrice: Int): Mono<List<Product?>> = Mono
            .fromSupplier {
                var products = productRepository.findAll().toList()
                products.filter { it.price <= selectMaxPrice }
            }

    fun streamPriceBetween(
            selectPrice: Int,
            selectPrice2: Int): Mono<List<Product?>> = Mono
                .fromSupplier {
                    var products = productRepository.findAll().toList()
                    products.filter { it.price >= selectPrice }
                            .filter { it.price <= selectPrice2 }
                }

    fun streamMenuContainAndLessThenPrice(
            selectMenu: String,
            selectMaxPrice: Int): Mono<List<Product?>> = Mono
                .fromSupplier {
                        var products = productRepository.findAll().toList()
                        products.filter { it.menu.contains(selectMenu) }
                                .filter { it.price <= selectMaxPrice }.toList()
                }

    // 글자가 포함된 메뉴가 ~~원 이상, ~~ 이하인 메뉴 검색
    fun streamMenuContainAndPriceBetween(
            selectMenu: String,
            selectMinPrice: Int,
            selectMaxPrice: Int
    ): Mono<List<Product?>> = Mono
            .fromSupplier {
                var products = productRepository.findAll().toList()
                products.filter { it.menu.contains(selectMenu) }
                        .filter { it.price >= selectMinPrice }
                        .filter { it.price <= selectMaxPrice }
            }



}// service끝
