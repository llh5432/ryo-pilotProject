package com.example.demo.controller

import com.example.demo.domain.Enum.MenuType
import com.example.demo.domain.entity.Product
import com.example.demo.service.ProductService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/products") // 전체 /api맵핑으로 한번 감쌈
class ProductController ( // 코드의형태는 항상 똑같게, 관행에 따라 테이블명+Controller
        val productService: ProductService // @autowired productService = ProductService;  이거라고 생각하면 됨
) {

    // Mapping은 되도록 구분되어 보여질 수 있게
    @GetMapping
    fun readAll(): Flux<Product> = productService.readProductAll()


    @GetMapping("/menuType")
    fun readMenuTypeOrderByPriceDesc(
            @RequestParam menuType: MenuType
    ): Flux<Product> = productService.readMenuEqualPriceAsc(menuType)


    @GetMapping("/read/{productId}")
    fun readProductById(
            @PathVariable productId: Int //@PathVariable : URL 경로에 매개변수를 넣어주는거
    ): Mono<Product> = productService.readProductById(productId)   // {return } 대신 = 하나로 표현이 가능한듯


    @PostMapping("/create")
    fun create(
            @RequestBody product: Product //postman에서 입력했떤 파라미터를 vo에 주입
    ): Mono<Product> = productService.createProduct(product) // create한 데이터값을 보여주기위한 리턴값생성


    @PutMapping("/update/{productId}")
    fun updateById(
            @PathVariable productId: Int,
            @RequestBody product: Product
    ): Mono<Product> {
        productService.updateProduct(productId, product)
        return productService.readProductById(productId)
    }
    //서비스를 두번 호출할 때엔 '=' 을 안쓰는듯 하다.

    @DeleteMapping("/delete/{productId}")
    fun deleteById(
            @PathVariable productId: Int
    ): Mono<Product> { // Product 타입 리턴함
        val founded = productService.readProductById(productId) // 변수에 founded에 id값의 product 빈객체를 넣음
        return productService.deleteProduct(founded) //!!은 이 값이 반드시있다고 해주는)
    }

    @GetMapping("/menuContain")
    fun readByMenuContain(
            @RequestParam menu: String
    ): Flux<Product> = productService.readMenu(menu)

    @GetMapping("/lessThenPrice")
    fun readByLessThenPrice(
    ): Flux<Product> = productService.readByLessThenPrice()
}