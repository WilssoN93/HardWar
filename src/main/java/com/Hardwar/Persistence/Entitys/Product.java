package com.Hardwar.Persistence.Entitys;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {

    private Long id;
    private String url;
    private String name;
    private String price;
    private String domainName;
    private String typeOfHardWare;
    private String articleNumber;
    private List<Specification> specification;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "articlenumber")
    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }


    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_specification")
    public List<Specification> getSpecification() {
        return specification;
    }

    public void setSpecification(List<Specification> specification) {
        this.specification = specification;
    }

    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "price")
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Column(name = "domainname")
    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    @Column(name = "typeofhardware")
    public String getTypeOfHardWare() {
        return typeOfHardWare;
    }

    public void setTypeOfHardWare(String typeOfHardWare) {
        this.typeOfHardWare = typeOfHardWare;
    }
}
