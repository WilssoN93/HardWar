package com.Hardwar.Persistence.Service;

import com.Hardwar.Persistence.Entitys.Product;
import com.Hardwar.Persistence.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository){
        this.repository = repository;
    }



    public List<Product> findAll(){
        return repository.findAll();
    }
    public Product findProductByUrl(String url){
       return repository.findByUrl(url);
    }
    public List<Product> findAllByDomainName(String domainName){
        return repository.findAllByDomainName(domainName);
    }
    public Product addProduct(Product product){
        return repository.save(product);
    }
    public List<Product> addAllProducts(List<Product> productList){
        for (Product product :productList) {
            if(!findAll().stream().map(Product::getUrl).collect(Collectors.toList()).contains(product.getUrl())) {
                repository.save(product);
            }
        }
        return productList;
    }
    public List<Product> updateProductsByUrl(List<Product> listToBeUpdated){
        for (Product product:listToBeUpdated) {
            Product existingProduct = findProductByUrl(product.getUrl());
            if(existingProduct!=null){
                product.setId(existingProduct.getId());
                repository.save(product);
            }
        }
        return listToBeUpdated;
    }
}
