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

    @GetMapping(path = "/{domainName}/{hardWareType}")
    public @ResponseBody
    List<Product> getAllbyDomainNameAndType(@PathVariable("domainName") String domainName,@PathVariable("hardWareType") String hardWareType){
        return service.findAllByDomainNameAndType(domainName,hardWareType);
    }
    @GetMapping(path = "/{domainName}")
    public @ResponseBody List<Product> getAllByDomainName(@PathVariable("domainName")String domainName){
        return service.findAllByDomain(domainName);
    }

    @GetMapping(path = "{domainName}/nullvalues")
    public @ResponseBody List<Product> getNullTypes(@PathVariable("domainName")String domainName){
        return service.findAllByHardwareIsNullandDomain(domainName);
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
