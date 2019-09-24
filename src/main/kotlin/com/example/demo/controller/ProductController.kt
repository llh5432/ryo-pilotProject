package com.example.demo.controller

import com.bea.xml.stream.filters.NameFilter
import com.example.demo.domain.entity.Product
import com.example.demo.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.concurrent.ConcurrentHashMap

@RestController // 빈이됨
@RequestMapping("/api")
class ProductController ( // 코드의형태는 항상 똑같게
        val productService: ProductService // @autowired productService = ProductService  이거라고 생각하면 됨
){  //class 시작
    // Mapping은 되도록 구분되어 보여질 수 있게
    // selectAll
    @GetMapping("/all")
    fun getAllProduct(): List<Product>{
        return productService.getAllProduct()
    }
    // select
    @GetMapping("/{productId}")
    fun getProductById(
            @PathVariable productId: Int //@PathVariable : URL 경로에 변수를 넣어주는거 url email=? 이런거랑비슷한듯
    ) : Product?{ // 리턴타입이 ?  == nullable
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
    @ResponseStatus(HttpStatus.OK) //Http상태코드 예외처리 어노테이션..? 왜사용?
    fun updateProductById(
        @PathVariable("productId")productId: Int, @RequestBody product: Product
    ): Product?
    {
         productService.updateProduct(productId,product)
        return productService.getProductById(productId)
    }
    //delete
    @DeleteMapping("/delete/{productId}")
    fun deleteProductById(
        @PathVariable productId : Int
    ): Product?{ // Product 타입 리턴함
        val founded = productService.getProductById(productId) // 변수에 founded에 id값의 product 빈객체를 넣음
        return productService.deleteProduct(founded!!) //!!은 이 값이 반드시있다고 해주는)
    }

    @GetMapping("/menu")
    fun getMenu(
            @RequestParam select: String
    ):List<Product>{
        return productService.getMenu(select)
    }

    @GetMapping("/menuAndPrice")
    fun getMenuAndPrice(
            @RequestParam selectMenu : String, selectPrice : Int
    ): List<Product>{
        return productService.getMenuAndPrice(selectMenu, selectPrice)
    }

    @GetMapping("/price")
    fun getPriceLessThen(
            @RequestParam selectPrice: Int
    ): List<Product>{
        return productService.getPriceLessThen(selectPrice)
    }


} // class 끝