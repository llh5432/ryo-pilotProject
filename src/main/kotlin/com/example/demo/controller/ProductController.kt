package com.example.demo.controller

import com.bea.xml.stream.filters.NameFilter
import com.example.demo.domain.entity.Product
import com.example.demo.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController // 빈이됨
class ProductController ( // 코드의형태는 항상 똑같게
        val productService: ProductService // @autowired productService = ProductService  이거라고 생각하면 됨 
){ //class 내용시작
    // selectAll
    @GetMapping("/api/all")
    fun getAllProduct(): List<Product>{
        return productService.getAllProduct()
    }
    // select
    @GetMapping("/api/{productId}")
    fun getProductById(
            @PathVariable productId: Int //@PathVariable : URL 경로에 변수를 넣어주는거 url email=? 이런거랑비슷한듯
    ) : Product?{ // 리턴타입이 ?  == nullable
        return productService.getProductById(productId) // 위에 생성한 productService.만든함수(매개변수)
    }
    // insert
    @PostMapping("/api/insert")
    fun createProduct(
            @RequestBody product: Product //postman에서 입력했떤 파라미터를 vo에 주입
    ): Product?{ // create한 데이터값을 보여주기위한 리턴값
        return productService.createProduct(product)
    }

    @PutMapping("/api/update/{productId}")
    @ResponseStatus(HttpStatus.OK)
    fun updateProductById(
        @PathVariable("productId")productId: Int, @RequestBody product: Product
    ): Product?
    {
         productService.updateProduct(productId,product)
        return productService.getProductById(productId)
    }

    //delete
    @DeleteMapping("/api/delete/{productId}")
    fun deleteProductById(
            @PathVariable productId : Int
    ): Product?{
        val founded = productService.getProductById(productId)
     return productService.deleteProduct(founded!!) //!!은 이 값이 반드시 있다는 걸 나타내는 코틀린언어
    }







} // class 끝