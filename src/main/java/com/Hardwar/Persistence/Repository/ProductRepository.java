package com.Hardwar.Persistence.Repository;

import com.Hardwar.Persistence.Entitys.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByDomainName(String domainName);
    Product findByUrl(String url);
    List<Product> findAllByTypeOfHardWareIsNullAndDomainName(String domainName);
}
