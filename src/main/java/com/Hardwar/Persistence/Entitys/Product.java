package com.Hardwar.Persistence.Entitys;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "`product`")
public class Product {

    private Long id;
    private String url;
    private String domainName;
    private String typeOfHardWare;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", domainName='" + domainName + '\'' +
                ", typeOfHardWare='" + typeOfHardWare + '\'' +
                '}';
    }
}
