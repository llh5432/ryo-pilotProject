package com.example.demo.controller

import com.example.demo.domain.entity.Product
import com.example.demo.service.ProductService
import org.springframework.web.bind.annotation.*

@RestController // 빈이됨
class ProductController ( // 코드의형태는 항상 똑같게
        val productService: ProductService // @autowired productService = ProductService  이거라고 생각하면 됨 
){ //class 내용시작
    @RequestMapping(value="/api/{productId}", method = arrayOf(RequestMethod.GET)) //requestMapping은 함수위에 지정
    @GetMapping("/api/{productId}")
    fun getProductById(
            @PathVariable productId: Int //@PathVariable : URL 경로에 변수를 넣어주는거 url email=? 이런거랑비슷한듯
    ) : Product?{ // 리턴타입이 ?  == nullable
        return productService.getProductById(productId) // 위에 생성한 productService.만든함수(매개변수)
    }

    @RequestMapping(value="/api/all", method = arrayOf(RequestMethod.GET))
    @GetMapping("/api/all")
    @PostMapping
    @PutMapping
    @DeleteMapping
    fun getAllProduct(): List<Product>{
        return productService.getAllProduct()
    }




} // class 끝