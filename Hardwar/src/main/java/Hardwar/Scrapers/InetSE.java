package Hardwar.Scrapers;

import Hardwar.Domain;
import Hardwar.Scraper;
import Hardwar.Utils.Utils;
import com.Hardwar.Persistence.Entitys.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Domain("inet.se")
public class InetSE extends Scraper {
    @Override
    public List<Product> scan() {
        List<Product> products = new ArrayList<>();
        driver.get("https://www.inet.se/kategori/31/datorkomponenter");
        List<String> categories = driver.findElementsByXPath("//ol[@class='lvl2 collapse in']//a")
                .stream()
                .map(we -> we.getAttribute("href"))
                .collect(Collectors.toList());

        for (String category : categories) {
            driver.get(category);
            Utils.scrollToLastProduct(driver);
            WebElement button = getWebElement("//button[@class='btn-blue btn-show-more btn btn-default']");
            if (button != null) {
                Utils.waitTime(2000);
                button.click();
            }
            Utils.scrollToLastProduct(driver);
            driver.findElementsByXPath("//div[@class='description']//a[1]")
                    .stream()
                    .map(we -> {
                        Product product = new Product();
                        String url = we.getAttribute("href");
                        product.setUrl(url);
                        product.setDomainName(getDomainName());
                        return product;
                    }).forEach(products::add);
            System.out.println(products.size() + " products saved!");
        }
        return products;

    }

    @Override
    public CentralProcessingUnit parseCPU(Product product) {
        Utils.waitTime((int) (Math.random() * 5000) + 1000);
        getWebPage(product.getUrl());
        CentralProcessingUnit cpu = new CentralProcessingUnit();
        cpu.setUrl(product.getUrl());
        cpu.setDomain(getDomainName());
        cpu.setCores(findFieldFromSpecification("Antal kärnor").replaceAll("\\D",""));
        cpu.setThreads(findFieldFromSpecification("Antal trådar"));
        cpu.setSocket(findFieldFromSpecification("Processorsocket"));
        cpu.setArticleNumber(findFieldFromSpecification("Tillverkarens ArtNr"));
        cpu.setName(getText("//section[@class='box box-body product-description']//h1", "name"));
        cpu.setImgUrl(getTextWithAttribute("//div[@class='slide'][2]/picture[@class='image-slide']/div/img", "src", "image url"));
        cpu.setPrice(getText("//div[@class='box-body']/span[@class='price']", "price").replaceAll("\\D",""));
        cpu = getCpuClockSpeeds(findFieldFromSpecification("Hastighet"), cpu);
        System.out.println(cpu);
        return cpu;
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
        Utils.waitTime((int) (Math.random() * 5000) + 1000);
        System.out.println((int) (Math.random() * 5000) + 1000);
        getWebPage(product.getUrl());
        product.setTypeOfHardWare(getText("//li[2]/a/span", "type of hardware"));
        return product;
    }

    @Override
    public GraphicsCard parseGraphicsCard(Product product) {
        Utils.waitTime((int) (Math.random() * 5000) + 1000);
        GraphicsCard graphicsCard = new GraphicsCard();
        getWebPage(product.getUrl());
        graphicsCard.setArticleNumber(findFieldFromSpecification("Tillverkarens ArtNr"));
        graphicsCard.setUrl(product.getUrl());
        graphicsCard.setName(getText("//section[@class='box box-body product-description']/header/h1", "name"));
        graphicsCard.setPrice(getText("//div[@class='box-body']/span[@class='price']", "price").replaceAll("\\D",""));
        if (graphicsCard.getPrice() == null) {
            graphicsCard.setPrice(getText("//section[@class='box product-purchase']//span[@class='campaign-price']", "discounted price").replaceAll("\\D",""));
            graphicsCard.setOriginalPrice(getText("//section[@class='box product-purchase']//span[@class='list-price']", "original price").replaceAll("\\D",""));
        }

        graphicsCard.setDomainName(getDomainName());
        graphicsCard.setCudaCores(findFieldFromSpecification("Streamprocessorer / pipes").replaceAll("\\D",""));
        graphicsCard.setImgUrl(getText("//div[@class='slide'][1]/picture[@class='image-slide']/div[@class='center-image']/img", "img url"));
        graphicsCard.setCoreClock(findFieldFromSpecification("GPU-frekvens").replaceAll("\\D",""));
        graphicsCard.setBoostClock(findFieldFromSpecification("Turbofrekvens").replaceAll("\\D",""));
        graphicsCard.setConnection(findFieldFromSpecification("Strömanslutning"));
        if (graphicsCard.getBoostClock() == null) {
            if (graphicsCard.getCoreClock() != null) {
                graphicsCard.setBoostClock(getBoostClock(graphicsCard.getCoreClock()));
                graphicsCard.setCoreClock(removeBoostClockFromCoreClock(graphicsCard.getCoreClock()));
            }
        }

        System.out.println(graphicsCard);
        return graphicsCard;
    }

    @Override
    public String getDomainName() {
        return "inet.se";
    }

    private WebElement getWebElement(String xPath) {
        WebElement element;
        try {
            element = driver.findElementByXPath(xPath);
        } catch (NoSuchElementException e) {
            System.out.println("Didnt find element : " + driver.getCurrentUrl());
            return null;
        }
        return element;
    }

    private String getTextWithAttribute(String xPath, String attribute, String field) {
        String element;
        try {
            element = driver.findElementByXPath(xPath).getAttribute(attribute);
        } catch (NoSuchElementException e) {
            System.out.println("Didnt find " + field + ": " + driver.getCurrentUrl());
            return null;
        }
        return element;
    }

    private String getArtNumberFromSpecifications(List<Specification> specificationList) {
        for (Specification spec : specificationList) {
            if (spec.getKey().equals("Tillverkarens ArtNr")) {
                return spec.getValue();
            }
        }
        return null;
    }

    private String findFieldFromSpecification(String field) {
        List<String> header = getListOfText("//section/dl/div/dt", "header");
        List<String> values = getListOfText("//section/dl/div/dd", "values");
        for (int i = 0; i < header.size(); i++) {
            if (header.get(i).toLowerCase().contains(field.toLowerCase())) {
                return values.get(i);
            }
        }
        return null;
    }

    private String getBoostClock(String clockspeed) {
        String[] slittedString = clockspeed.split("\\s");
        for (String string : slittedString) {
            string = string.replaceAll("[\\W]", "");
            if (string.matches("[0-9]{4}[A-Za-z]{3}")) {
                return string;
            }
        }
        return null;
    }

    private String removeBoostClockFromCoreClock(String clockSpeed) {
        String[] coreClock = clockSpeed.split("\\s");
        String coreClockSpeed = null;
        try {
            coreClockSpeed = coreClock[0] + " " + coreClock[1] + " " + coreClock[2];
        } catch (IndexOutOfBoundsException e) {
            coreClockSpeed = coreClock[0] + " " + coreClock[1];
        }

        return coreClockSpeed;
    }

    public CentralProcessingUnit getCpuClockSpeeds(String speeds, CentralProcessingUnit cpu) {
        if (speeds != null) {
            String[] speed = speeds.split("\\s");
            List<Double> matchedSpeed = new ArrayList<>();
            for (String clockspeeds : speed) {
                if (clockspeeds.matches("[0-9][,.][0-9]")) {
                    matchedSpeed.add(Double.parseDouble(clockspeeds.replace(",",".")));
                }else if(clockspeeds.matches("[0-9]")){
                    matchedSpeed.add(Double.parseDouble(clockspeeds));
                }
            }
            System.out.println(matchedSpeed.size());
            if (matchedSpeed.size() > 1) {
                if (matchedSpeed.get(0) > matchedSpeed.get(1)) {
                    cpu.setCoreClock(matchedSpeed.get(1).toString());
                    cpu.setBoostClock(matchedSpeed.get(0).toString());
                } else if (matchedSpeed.get(1) > matchedSpeed.get(0)) {
                    cpu.setCoreClock(matchedSpeed.get(0).toString());
                    cpu.setBoostClock(matchedSpeed.get(1).toString());
                }
            } else if (matchedSpeed.size() == 1) {
                cpu.setCoreClock(matchedSpeed.get(0).toString());
                cpu.setBoostClock(null);
            }
        }
        return cpu;
    }

}