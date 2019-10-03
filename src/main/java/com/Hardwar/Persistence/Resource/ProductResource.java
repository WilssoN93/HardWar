package com.Hardwar.Persistence.Resource;

import com.Hardwar.Persistence.Entitys.Product;
import com.Hardwar.Persistence.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "/{domainName}")
    public @ResponseBody
    List<Product> getAllbyDomainName(@PathVariable("domainName") String domainName){
        return service.findAllByDomainName(domainName);
    }

    @PostMapping
    public List<Product> saveAll(@RequestBody List<Product> productList){
        return service.addAllProducts(productList);
    }

    @PutMapping
    public List<Product> updateProducts(@RequestBody List<Product> listToBeUpdated){
        return service.updateProductsByUrl(listToBeUpdated);
    }



}
