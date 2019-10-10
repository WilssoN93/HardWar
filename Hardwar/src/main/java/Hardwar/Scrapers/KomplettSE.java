package Hardwar.Scrapers;

import Hardwar.Domain;
import Hardwar.Scraper;
import Hardwar.Utils.Utils;
import com.Hardwar.Persistence.Entitys.*;

import java.util.ArrayList;
import java.util.List;

@Domain("komplett.se")
public class KomplettSE extends Scraper {
    @Override
    public List<Product> scan() {
        List<Product> products = new ArrayList<>();

        driver.get("https://www.komplett.se/category/28003/datorutrustning/datorkomponenter");
        driver.switchTo().activeElement().click();
        Utils.scrollToLastProduct(driver);

        driver.findElementsByXPath("//a[@class='product-link image-container']")
                .stream()
                .map(webElement -> {
                    String url = webElement.getAttribute("href");
                    Product product = new Product();
                    product.setUrl(url);
                    product.setDomainName(getDomainName());
                    return product;
                })
                .forEach(products::add);
        System.out.println(products.size() + " products saved!");
        return products;
    }

    @Override
    public GraphicsCard parseGraphicsCard(Product product) {
        GraphicsCard graphicsCard = new GraphicsCard();
        Utils.waitTime((int)(Math.random()*5000)+1000);
        getWebPage(product.getUrl());
        graphicsCard.setDomainName(getDomainName());
        graphicsCard.setUrl(product.getUrl());
        graphicsCard.setName(getText("//h1[@class='product-main-info-webtext1']/span", "name"));
        graphicsCard.setArticleNumber(getText("//div[@class='product-main-info-partnumber-store']/span/span[@itemprop='mpn']", "Article Number"));
        graphicsCard.setPrice(getText("//span[@class='product-price-now']", "price"));
        graphicsCard.setBoostClock(findFieldFromSpecification("Snabbklocka"));
        graphicsCard.setCudaCores(findFieldFromSpecification("cuda-kärnor"));
        graphicsCard.setConnection(findFieldFromSpecification("Extra Krav"));
        graphicsCard.setImgUrl(getWebElement("//div/button[1]/img","img url").getAttribute("src"));
        System.out.println(graphicsCard);
        return graphicsCard;
    }

    @Override
    public CentralProcessingUnit parseCPU(Product product) {
        return null;
    }

    @Override
    public MotherBoard parseMotherBoard(Product product) {
        return null;
    }

    @Override
    public RandomAcessMemory parseRAM(Product product) {
        return null;
    }

    @Override
    public Product parseType(Product product) {
        Utils.waitTime((int)(Math.random()*5000)+1000);
        getWebPage(product.getUrl());
        System.out.println(driver.getCurrentUrl());
        driver.switchTo().activeElement().click();
        Utils.waitTime(1000);
        List<String> categories = getListOfText("//div[@class='breadcrumb-item-wrapper']/a[@class='breadcrumb-item-link']", "categories");
        for (String category : categories) {
            if (category.equals("Datorkomponenter")) {
                String typeOfHardWare = categories.get(categories.indexOf(category) + 1);
                product.setTypeOfHardWare(typeOfHardWare);
                System.out.println(typeOfHardWare);
            }
        }
        return product;
    }

    @Override
    public String getDomainName() {
        return "komplett.se";
    }

    private String findFieldFromSpecification(String field) {
        List<String> headerList = getListOfText("//tbody/tr/th","specification headers");
        List<String> valueList = getListOfText("//tbody/tr/td","specification values");
        for (int i = 0; i < headerList.size() ; i++) {
            if(headerList.get(i).toLowerCase().equals(field.toLowerCase())){
                return valueList.get(i);
            }
        }
        return null;
    }

}
