package com.example.demo.controller

import com.bea.xml.stream.filters.NameFilter
import com.example.demo.domain.entity.Product
import com.example.demo.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import java.util.concurrent.ConcurrentHashMap

@RestController // 빈이됨
@RequestMapping("/api")
class ProductController ( // 코드의형태는 항상 똑같게
        val productService: ProductService // @autowired productService = ProductService  이거라고 생각하면 됨
){  //class 시작
    // Mapping은 되도록 구분되어 보여질 수 있게
    // selectAll
    @GetMapping("/select/all")
    fun getAllProduct(): Mono<List<Product>> {
        return productService.getAllProduct()
    }
    // select
    @GetMapping("/select/{productId}")
    fun getProductById(
            @PathVariable productId: Int //@PathVariable : URL 경로에 변수를 넣어주는거 url email=? 이런거랑비슷한듯
    ) : Mono<Product?>{ // 리턴타입이 ?  == nullable
        return productService.getProductById(productId) // 위에 생성한 productService.만든함수(매개변수)
    }
    // insert
    @PostMapping("/insert")
    fun createProduct(
            @RequestBody product: Product //postman에서 입력했떤 파라미터를 vo에 주입
    ): Product?{ // create한 데이터값을 보여주기위한 리턴값
        return productService.createProduct(product)
    }
    // update
    @PutMapping("/update/{productId}")
    @ResponseStatus(HttpStatus.OK) //Http상태코드 예외처리 어노테이션..? 왜사용? , 요청이 성공했음을 나타내는 응답상태코드 HTTP 200 OK 그런데 왜 업데이트에만 붙이는지?
    fun updateProductById(
        @PathVariable("productId")productId: Int, @RequestBody product: Product
    ): Mono<Product?>
    {
         productService.updateProduct(productId,product)
        return productService.getProductById(productId)
    }
    //delete
    @DeleteMapping("/delete/{productId}")
    fun deleteProductById(
        @PathVariable productId : Int
    ): Mono<Product?> { // Product 타입 리턴함
        val founded = productService.getProductById(productId) // 변수에 founded에 id값의 product 빈객체를 넣음
        return productService.deleteProduct(founded) //!!은 이 값이 반드시있다고 해주는)
    }

    @GetMapping("/menu")
    fun getMenu(
            @RequestParam selectMenu: String
    ):List<Product>{
        return productService.getMenu(selectMenu)
    }

    @GetMapping("/price")
    fun getPriceLessThen(
            @RequestParam selectPrice: Int
    ): List<Product>{
        return productService.getPriceLessThen(selectPrice)
    }

    @GetMapping("/menuAndPrice")
    fun getMenuAndPrice(
            @RequestParam selectMenu: String, selectMinPrice: Int, selectMaxPrice: Int
    ) : List<Product> {
        return productService.getMenuAndPriceBetween(selectMenu, selectMinPrice, selectMaxPrice)
    }

    @GetMapping("/lamdaMenu")
    fun getMenuTest(
            @RequestParam selectMenu: String
    ): List<Product>?{
        return productService.lamdaMenu(selectMenu)
    }

    @GetMapping("/lamdaPrice")
    fun getPriceTest(
            @RequestParam selectPrice: Int
    ): List<Product>?{
        return productService.lamdaPrice(selectPrice)
    }

    @GetMapping("/lamdaMenuAndPrice")
    fun getMenuAndPrice(
            @RequestParam selectPrice: Int, selectMenu: String
    ): List<Product>{
        return productService.lamdaMenuAndPrice(selectMenu, selectPrice)
    }

    @GetMapping("/lamdaGreaterThenPrice")
    fun getLamdaGreaterThenPrice(
            @RequestParam selectMinPrice: Int
    ): List<Product>{
        return productService.lamdaPriceGreaterThen(selectMinPrice)
    }

    @GetMapping("/lamdaLessThenPrice")
    fun getLamdaLessThenPrice(
            @RequestParam selectMaxPrice: Int
    ): List<Product>{
        return productService.lamdaPriceLessThen(selectMaxPrice)
    }

    @GetMapping("/lamdaPriceBetween")
    fun getLamdaPriceBetween(
            @RequestParam selectMinPrice: Int, selectMaxPrice: Int
    ): List<Product>{
        return productService.lamdaPriceBetween(selectMinPrice, selectMaxPrice)
    }

    @GetMapping("/lamdaMenuContainAndLessThenPrice")
    fun getLamdaMenuContainAndLessThenPrice(
@RequestParam selectMenu: String, selectMaxPrice: Int
) : List<Product>{
    return productService.lamdaMenuContainAndLessThenPrice(selectMenu, selectMaxPrice)
}

@GetMapping("/lamdaMenuContainAndPriceBetween")
fun getLamdaMenuContainAndPriceBetween(
        @RequestParam selectMenu: String, selectMinPrice: Int, selectMaxPrice: Int
) : List<Product>{
    return productService.lamdaMenuContainAndPriceBetween(selectMenu, selectMinPrice, selectMaxPrice)
}
} // class 끝