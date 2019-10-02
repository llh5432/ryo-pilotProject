package com.example.demo.controller

import com.example.demo.domain.entity.Product
import com.example.demo.domain.entity.TypeMenu
import com.example.demo.service.ProductService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api") // 전체 /api맵핑으로 한번 감쌈
class ProductController ( // 코드의형태는 항상 똑같게, 관행에 따라 테이블명+Controller
        val productService: ProductService // @autowired productService = ProductService;  이거라고 생각하면 됨
){  //class 시작
    // Mapping은 되도록 구분되어 보여질 수 있게
    @GetMapping("/select/all")
    fun getAllProduct(): Flux<Product> {
        return productService.getAllProduct() // {return 사용}
    }


    @GetMapping("/menuType/{selectMenuType}")
    fun getMenuTypeEqual(
            @PathVariable selectMenuType : TypeMenu
    ): Mono<List<Product>> = productService.getMenuEqual(selectMenuType)


    @GetMapping("/select/{productId}")
    fun getProductById(
        @PathVariable productId: Int //@PathVariable : URL 경로에 매개변수를 넣어주는거
    ): Mono<Product> = productService.getProductById(productId)   // {return } 대신 = 하나로 표현이 가능한듯

    @PostMapping("/insert")
    fun createProduct(
        @RequestBody product: Product //postman에서 입력했떤 파라미터를 vo에 주입
    ): Mono<Product> = productService.createProduct(product) // create한 데이터값을 보여주기위한 리턴값생성

    @PutMapping("/update/{productId}")
    fun updateProductById(
        @PathVariable productId: Int,
        @RequestBody product: Product
    ): Mono<Product> {
        productService.updateProduct(productId,product)
     return productService.getProductById(productId)
    }
     //서비스를 두번 호출할 때엔 '=' 을 안쓰는듯 하다.

    @DeleteMapping("/delete/{productId}")
    fun deleteProductById(
        @PathVariable productId : Int
    ): Mono<Product> { // Product 타입 리턴함
        val founded = productService.getProductById(productId) // 변수에 founded에 id값의 product 빈객체를 넣음
        return productService.deleteProduct(founded) //!!은 이 값이 반드시있다고 해주는)
    }

    @GetMapping("/menu")
    fun getMenu(
        @RequestParam selectMenu: String
    ): Mono<List<Product>> = productService.getMenu(selectMenu)

    @GetMapping("/price")
    fun getPriceLessThen(
        @RequestParam selectPrice: Int
    ): Mono<List<Product>> = productService.getPriceLessThen(selectPrice)


    @GetMapping("/menuAndPrice")
    fun getMenuAndPrice(
        @RequestParam selectMenu: String,
                      selectMinPrice: Int,
                      selectMaxPrice: Int
    ): Mono<List<Product>> = productService.getMenuAndPriceBetween(selectMenu,
                                                                    selectMinPrice,
                                                                    selectMaxPrice)


    @GetMapping("/streamMenu")
    fun getMenuTest(
            @RequestParam selectMenu: String
    ): Mono<List<Product>> = productService.streamMenu(selectMenu)


    @GetMapping("/streamPrice")
    fun getPriceTest(
            @RequestParam selectPrice: Int
    ): Mono<List<Product>> = productService.streamPrice(selectPrice)


    @GetMapping("/streamMenuAndPrice")
    fun getMenuAndPrice(
            @RequestParam selectPrice: Int, selectMenu: String
    ): Mono<List<Product>> = productService.streamMenuAndPrice(selectMenu, selectPrice)


    @GetMapping("/streamGreaterThenPrice")
    fun getstreamGreaterThenPrice(
            @RequestParam selectMinPrice: Int
    ): Mono<List<Product>> = productService.streamPriceGreaterThen(selectMinPrice)


    @GetMapping("/streamLessThenPrice")
    fun getstreamLessThenPrice(
            @RequestParam selectMaxPrice: Int
    ): Mono<List<Product>> = productService.streamPriceLessThen(selectMaxPrice)


    @GetMapping("/streamPriceBetween")
    fun getstreamPriceBetween(
            @RequestParam selectMinPrice: Int,
                          selectMaxPrice: Int
    ): Mono<List<Product>> = productService.streamPriceBetween(selectMinPrice, selectMaxPrice)


    @GetMapping("/streamMenuContainAndLessThenPrice")
    fun getstreamMenuContainAndLessThenPrice(
            @RequestParam selectMenu: String,
                          selectMaxPrice: Int
    ): Mono<List<Product>> = productService.streamMenuContainAndLessThenPrice(selectMenu, selectMaxPrice)


    @GetMapping("/streamMenuContainAndPriceBetween")
    fun getstreamMenuContainAndPriceBetween(
            @RequestParam selectMenu: String,
                          selectMinPrice: Int,
                          selectMaxPrice: Int
    ): Mono<List<Product>> = productService.streamMenuContainAndPriceBetween(selectMenu,
                                                                               selectMinPrice,
                                                                               selectMaxPrice)

} // class 끝