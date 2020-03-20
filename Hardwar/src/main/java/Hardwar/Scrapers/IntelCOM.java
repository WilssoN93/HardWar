package Hardwar.Scrapers;

import Hardwar.Domain;
import Hardwar.Scraper;
import com.Hardwar.Persistence.Entitys.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

@Domain("intel.com")
public class IntelCOM extends Scraper {
    private final String URL = "https://ark.intel.com/content/www/us/en/ark.html#@Processors";

    @Override
    public List<Product> scan() {
        List<Product> products = new ArrayList<>();
        int counter = 1;
        for (int i = 0; i <= 30; i++) {
            getWebPage(URL);
            List<String> topCategories = getHrefs("//div[@class='products processors'][" + counter + "]//span[@class='name']/a", "TopCategories");
            counter++;
            System.out.println("==================================");
            System.out.println(topCategories);
            System.out.println("==================================");
            for (String category : topCategories) {
                getWebPage(category);
                System.out.println(category);
                getHrefs("//table[@id='product-table']/tbody/tr/td/a", "product-table")
                        .stream()
                        .map(url -> {
                            Product product = new Product();
                            product.setUrl(url);
                            System.out.println(url);
                            product.setDomainName(getDomainName());
                            return product;
                        }).forEach(products::add);
            }
            System.out.println(products.size() + " saved in productsList");
        }
        return products;
    }

    @Override
    public GraphicsCard parseGraphicsCard(Product product) {

        // getWebPage(product.getUrl());
        //product.setDomainName(getDomainName());
        //product.setName(getText("//div[@class='product-family-title-text']/h1[@class='h1']", "name"));
        //product.setTypeOfHardWare(getText("//div/a[@class='hidden-crumb-xs']", "Type of hardware"));
        //product.setArticleNumber(getProcessorNumber());
        //product.setSpecification(createSpecificationMap());
        //System.out.println(product);
        return null;
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
    public RandomAccessMemory parseRAM(Product product) {
        return null;
    }

    @Override
    public Chassi parseChassi(Product product) {
        return null;
    }

    @Override
    public Storage parseStorage(Product product) {
        return null;
    }

    @Override
    public PowerSupplyUnit parsePSU(Product product) {
        return null;
    }

    @Override
    public Product parseType(Product product) {
        return null;
    }

    @Override
    public ComputerComponent updatePrice(ComputerComponent component) {
        return null;
    }


    @Override
    public String getDomainName() {
        return "intel.com";
    }

    private String getProcessorNumber() {
        List<WebElement> dataKeys = getListOfElement("//section[@id='tab-blade-1-0-0']//ul[@class='specs-list']/li/span[@class='value']", "data keys");

        for (WebElement element : dataKeys) {
            if (element.getAttribute("data-key") != null) {
                if (element.getAttribute("data-key").contains("ProcessorNumber")) {
                    return cleanWeirdCharacters(element.getText());
                }
            }
        }
        return null;
    }
}
