package com.vongthaya.backenddemo.controller;

import com.vongthaya.backenddemo.exception.BaseException;
import com.vongthaya.backenddemo.exception.UserException;
import com.vongthaya.backenddemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable("id") String id) throws BaseException  {

        String response = productService.getProductById(id);

        System.out.println(">>> response: " + response);

        return ResponseEntity.ok(response);
    }

}
