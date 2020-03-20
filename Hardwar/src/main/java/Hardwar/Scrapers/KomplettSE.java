package Hardwar.Scrapers;

import Hardwar.Domain;
import Hardwar.Scraper;
import Hardwar.Utils.Utils;
import com.Hardwar.Persistence.Entitys.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

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
        System.out.println(product.getUrl());
        Utils.waitTime((int) (Math.random() * 5000) + 1000);
        getWebPage(product.getUrl());
        graphicsCard.setDomainName(getDomainName());
        graphicsCard.setUrl(product.getUrl());
        try {
            graphicsCard.setName(getText("//h1[@class='product-main-info-webtext1']/span", "name"));
        } catch (Exception name) {
            name.printStackTrace();
            return null;
        }
        try {
            graphicsCard.setArticleNumber(getText("//div[@class='product-main-info-partnumber-store']/span/span[@itemprop='mpn']", "Article Number"));
        } catch (Exception artNumber) {
            artNumber.printStackTrace();
            return null;
        }
        try {
            graphicsCard.setCoreClock(findCoreClockFromSpecification());
        } catch (Exception coreClock) {
            coreClock.printStackTrace();
            return null;
        }
        try {
            graphicsCard.setPrice(removeAllCharactersFromNumbers(getText("//span[@class='product-price-now']", "price")));
        } catch (Exception price) {
            price.printStackTrace();
            return null;
        }
        try {
            graphicsCard.setBoostClock(findBoostClockFromSpecification());
        } catch (Exception e) {
            System.out.println("Couldnt find boost clock!");
        }
        try {
            graphicsCard.setCudaCores(removeAllCharactersFromNumbers(findCudaCoresFromSpecification("cuda-kärnor")));
        } catch (Exception cudaCores) {
            cudaCores.printStackTrace();
            return null;
        }
        try {
            graphicsCard.setConnection(findFieldFromSpecification("Extra Krav"));
        } catch (Exception connection) {
            connection.printStackTrace();
            return null;
        }
        try {
            graphicsCard.setImgUrl(getWebElement("//div/button[1]/img", "img url").getAttribute("src"));
        } catch (Exception img) {
            img.printStackTrace();
        }
        try {
            graphicsCard.setCapacity(removeAllCharactersFromNumbers(findFieldFromSpecification("Erforderligt nätaggregat")));
        } catch (Exception capacity) {
            capacity.printStackTrace();
            return null;
        }
        return graphicsCard;
    }

    @Override
    public CentralProcessingUnit parseCPU(Product product) {
        CentralProcessingUnit cpu = new CentralProcessingUnit();
        try {
            System.out.println(product.getUrl());
            Utils.waitTime((int) (Math.random() * 5000) + 1000);
            getWebPage(product.getUrl());
            cpu.setUrl(product.getUrl());
            try {
                cpu.setName(getText("//h1[@class='product-main-info-webtext1']/span", "name"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                cpu.setArticleNumber(getText("//div[@class='product-main-info-partnumber-store']/span/span[@itemprop='mpn']", "Article Number"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                cpu.setPrice(removeAllCharactersFromNumbers(getText("//span[@class='product-price-now']", "price")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            cpu.setDomain(getDomainName());
            try {
                cpu.setCores(getCpuCores(findFieldFromSpecification("Antal kärnor")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                cpu.setThreads(removeAllCharactersFromNumbers(findFieldFromSpecification("Antal trådar")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                cpu.setCoreClock(getCpuSpeeds(findFieldFromSpecification("Klockfrekvens")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                cpu.setBoostClock(getCpuSpeeds(findFieldFromSpecification("Max. turbohastighet")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                cpu.setSocket(getCpuSocket(findFieldFromSpecification("Kompatibel processorsockel")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                cpu.setImgUrl(getTextWithAttribute("//*[@id=\"MainContent\"]//button/img", "src", "image url"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(cpu.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }

        return cpu;
    }

    @Override
    public MotherBoard parseMotherBoard(Product product) {
        MotherBoard motherBoard = new MotherBoard();
        try {
            System.out.println(product.getUrl());
            Utils.waitTime((int) (Math.random() * 5000) + 1000);
            getWebPage(product.getUrl());
            motherBoard.setUrl(product.getUrl());
            try {
                motherBoard.setName(getText("//h1[@class='product-main-info-webtext1']/span", "name"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                motherBoard.setArticleNumber(getText("//div[@class='product-main-info-partnumber-store']/span/span[@itemprop='mpn']", "Article Number"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                motherBoard.setPrice(removeAllCharactersFromNumbers(getText("//div[@class='product-price ']/span[@class='product-price-now']", "price")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            motherBoard.setDomainName(getDomainName());
            try {
                motherBoard.setSupportedRam(findFieldFromSpecification("Teknik"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                motherBoard.setSocket(getCpuSocket(findFieldFromSpecification("Processor-socket")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                motherBoard.setSpeeds(getMaxSpeed(findFieldFromSpecification("Bussklocka")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                motherBoard.setFormFactor(getFormFactor(findFieldFromSpecification("Typ av produkt")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                motherBoard.setImgUrl(getTextWithAttribute("//*[@id=\"MainContent\"]//button/img", "src", "image url"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                motherBoard.setMdot2(getMdot2(findFieldFromSpecification("Lagringsgränssnitt")));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(motherBoard.toString());

        return motherBoard;
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
        Utils.waitTime((int) (Math.random() * 5000) + 1000);
        getWebPage(product.getUrl());
        System.out.println(driver.getCurrentUrl());
        driver.switchTo().activeElement().click();
        Utils.waitTime(1000);
        List<String> categories = getListOfText("//div[@class='breadcrumb-item-wrapper']/a[@class='breadcrumb-item-link']", "categories");
        for (String category : categories) {
            if (category.equals("Datorkomponenter")||category.equals("Kringutrustning")||category.equals("Lagring")||category.equals("Datorer")) {
                String typeOfHardWare = categories.get(categories.indexOf(category) + 1);
                product.setTypeOfHardWare(typeOfHardWare);
                System.out.println(typeOfHardWare);
            }
        }
        return product;
    }

    @Override
    public ComputerComponent updatePrice(ComputerComponent component) {

        getWebPage(component.getUrl());

        try {
           String alertBox = driver.findElementByXPath("//div[@class='alert-content']/div").getText();
            if (alertBox.contains("Den här produkten finns inte längre i vårt sortiment")){
                return null;
            }
        }catch(Exception e){

        }

        try {
            component.setPrice(removeAllCharactersFromNumbers(getText("//div[@class='product-price ']/span[@class='product-price-now']", "price")));
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    private String getFormFactor(String formFactorText) {
        String[] formfactor = formFactorText.split("-");
        if (formfactor.length == 2) {
            if (formfactor[1].contains("utökad")) {
                return formfactor[1].replaceAll("utökad", "").trim();
            } else if (formfactor[1].contains("micro ATX")) {
                return "mATX";
            } else if (formfactor[1].contains("mini ITX")) {
                return "Mini-ITX";
            } else {
                return formfactor[1].trim();
            }
        }
        return null;
    }

    private int getMaxSpeed(String allSpeeds) {
        String[] speeds = allSpeeds.split(",");
        int highestSpeed = 0;
        for (String speed : speeds) {
            if (removeAllCharactersFromNumbers(speed) > highestSpeed) {
                highestSpeed = removeAllCharactersFromNumbers(speed);
            }
        }
        return highestSpeed;
    }

    @Override
    public String getDomainName() {
        return "komplett.se";
    }

    private String findFieldFromSpecification(String field) {
        List<String> headerList = getListOfText("//tbody/tr/th", "specification headers");
        List<String> valueList = getListOfText("//tbody/tr/td", "specification values");
        for (int i = 0; i < headerList.size(); i++) {
            if (headerList.get(i).toLowerCase().equals(field.toLowerCase())) {
                return valueList.get(i);
            }
        }
        return null;
    }

    private String findCudaCoresFromSpecification(String field) {
        List<String> headerList = getListOfText("//tbody/tr/th", "specification headers");
        List<String> valueList = getListOfText("//tbody/tr/td", "specification values");
        for (int i = 0; i < headerList.size(); i++) {
            if (headerList.get(i).equals("Strömprocessorer")) {
                return valueList.get(i);
            } else if (headerList.get(i).equals("CUDA-kärnor")) {
                return valueList.get(i);
            }
        }
        return "0";
    }

    private int findCoreClockFromSpecification() {
        List<String> headerList = getListOfText("//tbody/tr/th", "specification headers");
        List<String> valueList = getListOfText("//tbody/tr/td", "specification values");
        if (headerList.contains("Kärnklocka")) {
            for (int i = 0; i < headerList.size(); i++) {
                if (headerList.get(i).equals("Kärnklocka")) {
                    return removeAllCharactersFromNumbers(valueList.get(i));
                }
            }
        } else {
            String info = getText("//h2[@class='product-main-info-webtext2']/span", "info");
            String[] diffrentInfo = info.split(",");
            for (String strings : diffrentInfo) {
                if (strings.contains("/")) {
                    diffrentInfo = strings.split("/");
                    if (diffrentInfo.length == 2) {
                        return removeAllCharactersFromNumbers(diffrentInfo[0].trim());
                    }
                }
            }
        }
        return 0;
    }

    private int findBoostClockFromSpecification() {
        List<String> headerList = getListOfText("//tbody/tr/th", "specification headers");
        List<String> valueList = getListOfText("//tbody/tr/td", "specification values");
        if (headerList.contains("Snabbklocka")) {
            for (int i = 0; i < headerList.size(); i++) {
                if (headerList.get(i).equals("Snabbklocka")) {
                    return removeAllCharactersFromNumbers(valueList.get(i));
                }
            }
        } else {
            String info = getText("//h2[@class='product-main-info-webtext2']/span", "info");
            String[] diffrentInfo = info.split(",");
            for (String strings : diffrentInfo) {
                if (strings.contains("/")) {
                    diffrentInfo = strings.split("/");
                    if (diffrentInfo.length == 2) {
                        return removeAllCharactersFromNumbers(diffrentInfo[1].trim());
                    }
                }
            }
        }
        return 0;
    }

    private String getCpuSpeeds(String speed) {
        return speed.replaceAll("[A-Za-z]*", "").trim();
    }

    private String getCpuSocket(String socket) {
        if (socket.contains("LGA")) {
            socket = socket.replaceAll("LGA", "");
        }
        return socket.replaceAll("Socket", "").trim();
    }

    private int getMdot2(String lagring) {

        if (lagring.contains("x M.2")) {
            System.out.println(lagring.indexOf("x M.2"));
            String Mdot2 = lagring.substring(lagring.indexOf("x M.2") - 2, lagring.indexOf("x M.2"));
            System.out.println(Mdot2);
            return Integer.parseInt(Mdot2.trim());
        }
        return 0;
    }

    private int getCpuCores(String cores) {
        int nrCores = 0;

        if (cores.equals("Dubbelkärnig")) {
            nrCores = 2;
        } else if (cores.equals("Fyrkärnig")) {
            nrCores = 4;
        } else if (cores.equals("Sexkärnig")) {
            nrCores = 6;
        } else {
            nrCores = removeAllCharactersFromNumbers(cores);
        }
        return nrCores;
    }
}
