package com.example.demo.controller

import com.example.demo.domain.entity.Product
import com.example.demo.domain.Enum.TypeMenu
import com.example.demo.service.ProductService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
@RequestMapping("/api") // 전체 /api맵핑으로 한번 감쌈
class ProductController ( // 코드의형태는 항상 똑같게, 관행에 따라 테이블명+Controller
        val productService: ProductService // @autowired productService = ProductService;  이거라고 생각하면 됨
){

    // Mapping은 되도록 구분되어 보여질 수 있게
    @GetMapping("/select/all")
    fun getAllProduct(): Mono<List<Product>> {
        return productService.getAllProduct()
    }

    @GetMapping("/TypeMenu")
    fun getMenuType(
            @RequestParam selectMenuType: TypeMenu
    ): Flux<Product> = productService.getMenuEqual(selectMenuType)


    @GetMapping("/menuType/{selectMenuType}")
    fun getMenuTypeEqual(
            @PathVariable selectMenuType : TypeMenu
    ): Flux<Product> = productService.getMenuEqual(selectMenuType)


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

    @GetMapping("/menuContain")
    fun getMenu(
        @RequestParam selectMenu: String
    ): Flux<Product> = productService.getMenu(selectMenu)


    @GetMapping("/GreaterThenPrice")
    fun getGreaterThenPrice(
            @RequestParam selectMinPrice: Int
    ): Flux<Product> = productService.priceGreaterThen(selectMinPrice)


    @GetMapping("/LessThenPrice")
    fun getLessThenPrice(
            @RequestParam selectMaxPrice: Int
    ): Flux<Product> = productService.priceLessThen(selectMaxPrice)


    @GetMapping("/PriceBtw")
    fun getPriceBetween(
            @RequestParam selectMinPrice: Int,
                          selectMaxPrice: Int
    ): Flux<Product> = productService.priceBetween(selectMinPrice, selectMaxPrice)


    @GetMapping("/TypeMenuAndMenuContain")
    fun getTypeMenuAndMenuContaion(
            @RequestParam selectMenuType: TypeMenu,
            selectMenu: String
    ): Flux<Product> = productService.typeMenuAndMenuContain(selectMenuType, selectMenu)

    @GetMapping("/TypeMenuAndPriceGreaterThen")
    fun getTypeMenuAndPriceGreaterThen(
            @RequestParam selectMenuType: TypeMenu,
            selectMinPrice: Int
    ): Flux<Product> = productService.typeMenuAndPriceGreaterThen(selectMenuType, selectMinPrice)

    @GetMapping("/TypeMenuAndPriceLessThen")
    fun getTypeMenuAndPriceLessThen(
            @RequestParam selectMenuType: TypeMenu,
            selectMaxPrice: Int
    ): Flux<Product> = productService.typeMenuAndPriceLessThen(selectMenuType, selectMaxPrice)


    @GetMapping("/TypeEqualAndMenuContainAndPriceBtw")
    fun getTypeEqualAndMenuContainAndPriceBtw(
            @RequestParam selectMenuType: TypeMenu,
            selectMenu: String,
            selectMinPrice: Int,
            selectMaxPrice: Int
    ): Flux<Product> = productService.menuTypeEqualAndMenuContainAndPriceBtw(selectMenuType,
                                                                             selectMenu,
                                                                             selectMinPrice,
                                                                             selectMaxPrice
    )
} // class 끝