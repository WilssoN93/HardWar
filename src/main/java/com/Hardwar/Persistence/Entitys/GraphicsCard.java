package com.Hardwar.Persistence.Entitys;

import javax.persistence.*;

@Entity
@Table(name = "graphicscard")
public class GraphicsCard extends ComputerComponent {

    Long id;
    String url;
    String name;

     @Column(name = "url")
    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "GraphicsCard{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", originalPrice='" + originalPrice + '\'' +
                ", articleNumber='" + articleNumber + '\'' +
                ", coreClock='" + coreClock + '\'' +
                ", boostClock='" + boostClock + '\'' +
                ", cudaCores='" + cudaCores + '\'' +
                ", connection='" + connection + '\'' +
                ", domainName='" + domainName + '\'' +
                '}';
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String price;
    String imgUrl;
    String originalPrice;
    String articleNumber;
    String coreClock;
    String boostClock;
    String cudaCores;
    String connection;
    String domainName;

    @Column(name = "domainname")
    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "`name`")
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

    @Column(name = "imgurl")
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Column(name = "originalprice")
    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    @Column(name = "articlenumber")
    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    @Column(name = "coreclock")
    public String getCoreClock() {
        return coreClock;
    }

    public void setCoreClock(String coreClock) {
        this.coreClock = coreClock;
    }

    @Column(name = "boostclock")
    public String getBoostClock() {
        return boostClock;
    }

    public void setBoostClock(String boostClock) {
        this.boostClock = boostClock;
    }

    @Column(name = "cudacores")
    public String getCudaCores() {
        return cudaCores;
    }

    public void setCudaCores(String cudaCores) {
        this.cudaCores = cudaCores;
    }

    @Column(name = "connection")
    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

}
