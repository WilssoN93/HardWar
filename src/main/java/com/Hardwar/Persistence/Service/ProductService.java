package com.Hardwar.Persistence.Service;

import com.Hardwar.Persistence.Entitys.Product;
import com.Hardwar.Persistence.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }


    public List<Product> findAll() {
        return repository.findAll();
    }
    public List<Product> findAllByHardwareIsNullandDomain(String domainName) {
        return repository.findAllByTypeOfHardWareIsNullAndDomainName(domainName);
    }

    public List<Product> findAllByDomain(String domainName){
        return repository.findAllByDomainName(domainName);
    }
    public List<Product> findAllByDomainNameAndType(String domainName,String hardWareType) {
        List<Product> allProductsByDomainName = repository.findAllByDomainName(domainName);
        List<Product> productsByType = new ArrayList<>();
        for (Product product: allProductsByDomainName) {
            if(product.getTypeOfHardWare()!=null) {
                if (product.getTypeOfHardWare().toLowerCase().contains(hardWareType.toLowerCase())) {
                    productsByType.add(product);
                }
            }
        }
        return productsByType;
    }

    public Product addProduct(Product product) {
        return repository.save(product);
    }

    public List<Product> addAllProducts(List<Product> productList) {
        for (Product product : productList) {
            if (!findAll().stream().map(Product::getUrl).collect(Collectors.toList()).contains(product.getUrl())) {
                repository.save(product);
            }
        }
        return productList;
    }

    public List<Product> updateProductsByUrl(List<Product> listToBeUpdated) {
        for (Product product : listToBeUpdated) {
            repository.save(product);
        }
        return listToBeUpdated;
    }
}
