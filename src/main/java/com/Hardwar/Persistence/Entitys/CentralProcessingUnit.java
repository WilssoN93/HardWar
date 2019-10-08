package com.Hardwar.Persistence.Entitys;


import javax.persistence.*;

@Entity
@Table(name = "processor")
public class CentralProcessingUnit extends ComputerComponent {


    Long id;
    String name;
    String price;
    String url;
    String domain;
    String imgUrl;
    String articleNumber;
    String originalPrice;
    String socket;
    String coreClock;
    String boostClock;
    String threads;
    String cores;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    @Column(name = "domain")
    public String getDomain() {
        return domain;
    }

    @Column(name = "`name`")
    public String getName() {
        return name;
    }

    @Column(name = "price")
    public String getPrice() {
        return price;
    }

    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    @Column(name = "imgurl")
    public String getImgUrl() {
        return imgUrl;
    }

    @Column(name = "articlenumber")
    public String getArticleNumber() {
        return articleNumber;
    }

    @Column(name = "originalprice")
    public String getOriginalPrice() {
        return originalPrice;
    }

    @Column(name = "socket")
    public String getSocket() {
        return socket;
    }

    @Column(name = "coreclock")
    public String getCoreClock() {
        return coreClock;
    }

    @Column(name = "boostclock")
    public String getBoostClock() {
        return boostClock;
    }

    @Column(name = "threads")
    public String getThreads() {
        return threads;
    }

    @Column(name = "cores")
    public String getCores() {
        return cores;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public void setSocket(String socket) {
        this.socket = socket;
    }

    public void setCoreClock(String coreClock) {
        this.coreClock = coreClock;
    }

    public void setBoostClock(String boostClock) {
        this.boostClock = boostClock;
    }

    public void setThreads(String threads) {
        this.threads = threads;
    }


    public void setCores(String cores) {
        this.cores = cores;
    }

    @Override
    public String toString() {
        return "CentralProcessingUnit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", url='" + url + '\'' +
                ", domain='" + domain + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", articleNumber='" + articleNumber + '\'' +
                ", originalPrice='" + originalPrice + '\'' +
                ", socket='" + socket + '\'' +
                ", coreClock='" + coreClock + '\'' +
                ", boostClock='" + boostClock + '\'' +
                ", threads='" + threads + '\'' +
                ", cores='" + cores + '\'' +
                '}';
    }
}
