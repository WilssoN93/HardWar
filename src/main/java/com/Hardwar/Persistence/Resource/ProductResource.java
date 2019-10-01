package com.Hardwar.Persistence.Resource;

import com.Hardwar.Persistence.Entitys.Product;
import com.Hardwar.Persistence.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/product")
public class ProductResource {

    @Autowired
    ProductService service;

    @GetMapping
    public @ResponseBody
    List<Product> getAll(){
        return service.findAll();
    }



}
