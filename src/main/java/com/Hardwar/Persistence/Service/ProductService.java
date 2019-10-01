package com.Hardwar.Persistence.Service;

import com.Hardwar.Persistence.Entitys.Product;
import com.Hardwar.Persistence.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Product addProduct(Product product){
        return repository.save(product);
    }
}
