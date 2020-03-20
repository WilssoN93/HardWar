package Hardwar.Scrapers;

import Hardwar.Domain;
import Hardwar.Scraper;
import Hardwar.Utils.Utils;
import com.Hardwar.Persistence.Entitys.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
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
                button.sendKeys(Keys.RETURN);
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
        CentralProcessingUnit cpu = new CentralProcessingUnit();
        Utils.waitTime((int) (Math.random() * 5000) + 1000);
        getWebPage(product.getUrl());
        cpu.setUrl(product.getUrl());
        cpu.setDomain(getDomainName());
        try {
            cpu.setCores(removeAllCharactersFromNumbers(findFieldFromSpecification("Antal kärnor")));
        } catch (Exception cores) {
            cores.printStackTrace();
            return null;
        }
        try {
            cpu.setThreads(removeAllCharactersFromNumbers(findFieldFromSpecification("Antal trådar")));
        } catch (Exception threads) {
            threads.printStackTrace();
            return null;
        }
        try {
            cpu.setSocket(findSocketFromSpecification());
        } catch (Exception socket) {
            socket.printStackTrace();
            return null;
        }
        try {
            cpu.setArticleNumber(findFieldFromSpecification("Tillverkarens ArtNr"));
        } catch (Exception artNumber) {
            artNumber.printStackTrace();
            return null;
        }
        try {
            cpu.setName(getText("//section[@class='box box-body product-description']//h1", "name"));
        } catch (Exception name) {
            name.printStackTrace();
            return null;
        }
        try {
            if (getTextWithAttribute("//div[@class='slide'][2]/picture[@class='image-slide']/div/img", "src", "image url") != null) {
                cpu.setImgUrl(getTextWithAttribute("//div[@class='slide'][2]/picture[@class='image-slide']/div/img", "src", "image url"));
            } else {
                cpu.setImgUrl(getTextWithAttribute("//div[@class='carousel-gallery']/picture[@class='image-slide']/div[@class='center-image']/img", "src", "image url"));
            }
        } catch (Exception img) {
            img.printStackTrace();
        }
        try {
            cpu.setPrice(removeAllCharactersFromNumbers(getText("//div[@class='box-body']/span[@class='price']", "price")));
        } catch (Exception e) {
            try {
                cpu.setPrice(removeAllCharactersFromNumbers(getText("//span[@class='campaign-price']", "reduced price")));
            } catch (Exception price) {
                price.printStackTrace();
                return null;
            }
        }
        try {
            cpu = getCpuClockSpeeds(findFieldFromSpecification("Hastighet"), cpu);
        } catch (Exception clockspeed) {
            clockspeed.printStackTrace();
            return null;
        }
        System.out.println(cpu);
        return cpu;
    }

    @Override
    public MotherBoard parseMotherBoard(Product product) {
        MotherBoard motherBoard = new MotherBoard();
        try {
            Utils.waitTime((int) (Math.random() * 5000) + 1000);
            getWebPage(product.getUrl());
            motherBoard.setSupportedRam(findFieldFromSpecification("Internminnestyp"));
            motherBoard.setFormFactor(findFieldFromSpecification("Formfaktor"));
            motherBoard.setSocket(findSocketFromSpecification());
            try {
                motherBoard.setSpeeds(removeAllCharactersFromNumbers(findFieldFromSpecification("Minneshastighet (Standard)")));
            } catch (Exception e) {
                System.out.println("error parsing speeds");
            }
            motherBoard.setUrl(product.getUrl());
            motherBoard.setDomainName(getDomainName());
            motherBoard.setArticleNumber(findFieldFromSpecification("Tillverkarens ArtNr"));
            motherBoard.setImgUrl(getTextWithAttribute("//div[@class='slide'][2]/picture[@class='image-slide']/div/img", "src", "image url"));
            try {
                motherBoard.setPrice(removeAllCharactersFromNumbers(getText("//div[@class='box-body']/span[@class='price']", "price")));
            } catch (Exception e) {
                motherBoard.setPrice(removeAllCharactersFromNumbers(getText("//span[@class='campaign-price']", "reduced price")));
            }
            motherBoard.setName(getText("//section[@class='box box-body product-description']/header/h1", "name"));
            motherBoard.setMdot2(removeAllCharactersFromNumbers(findFieldFromSpecification("Antal M.2")));
            motherBoard.setMdot2Key(findFieldFromSpecification("M.2 key plats 1"));
            motherBoard.setMdot2Size(findFieldFromSpecification("M.2 storlek plats 1"));
            System.out.println(motherBoard);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return motherBoard;
    }

    @Override
    public RandomAccessMemory parseRAM(Product product) {
        RandomAccessMemory ram = new RandomAccessMemory();
        Utils.waitTime((int) (Math.random() * 5000) + 1000);
        getWebPage(product.getUrl());
        ram.setUrl(product.getUrl());
        ram.setDomainName(getDomainName());
        try {
            ram.setName(getText("//section[@class='box box-body product-description']/header/h1", "name"));
        } catch (Exception name) {
            name.printStackTrace();
            return null;
        }
        try {
            if (getTextWithAttribute("//div[@class='slide'][2]/picture[@class='image-slide']/div[@class='center-image']/img", "src", "image url") != null) {
                ram.setImgUrl(getTextWithAttribute("//div[@class='slide'][2]/picture[@class='image-slide']/div[@class='center-image']/img", "src", "image url"));
            } else if (getTextWithAttribute("//div[@class='slide'][6]/div[@class='preview-border']/picture[@class='image-slide']/div[@class='center-image']/img", "src", "second image url") != null) {
                ram.setImgUrl(getTextWithAttribute("//div[@class='slide'][6]/div[@class='preview-border']/picture[@class='image-slide']/div[@class='center-image']/img", "src", "second image url"));
            } else {
                ram.setImgUrl(getTextWithAttribute("//div[@class='carousel-gallery']/picture[@class='image-slide']/div[@class='center-image']/img", "src", "third image url"));
            }
        } catch (Exception image) {
            image.printStackTrace();
        }
        try {
            ram.setDdr(findFieldFromSpecification("Internminnestyp"));
        } catch (Exception ddr) {
            ddr.printStackTrace();
            return null;
        }
        try {
            ram.setSpeeds(removeAllCharactersFromNumbers(findFieldFromSpecification("Minnesfrekvens (MHz)")));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        try {
            ram.setCapacity(removeAllCharactersFromNumbers(findFieldFromSpecification("Kapacitet")));
        } catch (Exception capacity) {
            capacity.printStackTrace();
            return null;
        }
        try {
            ram.setArticleNumber(findFieldFromSpecification("Tillverkarens ArtNr"));
        } catch (Exception artNumber) {
            artNumber.printStackTrace();
        }
        try {
            ram.setPrice(removeAllCharactersFromNumbers(getText("//div[@class='box-body']/span[@class='price']", "price")));
        } catch (Exception price) {
            try {
                ram.setPrice(removeAllCharactersFromNumbers(getText("//span[@class='campaign-price']", "reduced price")));
            } catch (Exception price2) {
                price2.printStackTrace();
                return null;
            }
        }
        return ram;
    }

    @Override
    public Chassi parseChassi(Product product) {
        Chassi chassi = new Chassi();
        Utils.waitTime((int) (Math.random() * 5000) + 1000);
        getWebPage(product.getUrl());
        chassi.setUrl(product.getUrl());
        chassi.setDomainName(getDomainName());
        try {
            chassi.setName(getText("//section[@class='box box-body product-description']/header/h1", "name"));
        } catch (Exception name) {
            name.printStackTrace();
            return null;
        }
        try {
            chassi.setArticleNumber(findFieldFromSpecification("Tillverkarens ArtNr"));
        } catch (Exception artNumber) {
            artNumber.printStackTrace();
        }
        try {
            chassi.setImgUrl(getTextWithAttribute("//div[@class='slide'][2]/picture[@class='image-slide']/div[@class='center-image']/img", "src", "image url"));
        } catch (Exception image) {
            image.printStackTrace();
        }
        try {
            chassi.setPrice(removeAllCharactersFromNumbers(getText("//div[@class='box-body']/span[@class='price']", "price")));
        } catch (Exception e) {
            try {
                chassi.setPrice(removeAllCharactersFromNumbers(getText("//span[@class='campaign-price']", "reduced price")));
            } catch (Exception price2) {
                price2.printStackTrace();
                return null;
            }
        }
        try {
            chassi.setMaxHeightCpuCooler(findFieldFromSpecification("Max höjd CPU-kylare"));
        } catch (Exception maxHeight) {
            maxHeight.printStackTrace();
        }
        try {
            chassi.setMaxGpuWidth(findFieldFromSpecification("Max längd grafikkort (standardutförande)"));
        } catch (Exception maxGpuW) {
            maxGpuW.printStackTrace();
        }
        try {
            chassi.setFormFactor(findFieldFromSpecification("Formfaktor"));
        } catch (Exception formFactor) {
            formFactor.printStackTrace();
        }
        return chassi;
    }

    @Override
    public Storage parseStorage(Product product) {
        Storage storage = new Storage();
        Utils.waitTime((int) (Math.random() * 5000) + 1000);
        getWebPage(product.getUrl());
        storage.setUrl(product.getUrl());
        storage.setDomainName(getDomainName());
        try {
            storage.setName(getText("//section[@class='box box-body product-description']/header/h1", "name"));
        } catch (Exception name) {
            name.printStackTrace();
        }
        try {
            if (getText("//div[@class='box-body']/span[@class='price']", "price") != null) {
                storage.setPrice(removeAllCharactersFromNumbers(getText("//div[@class='box-body']/span[@class='price']", "price")));
            } else {
                storage.setPrice(removeAllCharactersFromNumbers(getText("//div[@class='box-body']/span[@class='price campaign']/span[@class='campaign-price']", "reduced price")));
            }
        } catch (Exception price) {
            price.getStackTrace();
            return null;
        }

        try {
            storage.setArticleNumber(findFieldFromSpecification("Tillverkarens ArtNr"));
        } catch (Exception artNumber) {
            artNumber.printStackTrace();
        }
        try {
            if (getText("//li[2]/a/span", " type of storage").equals("Hårddisk Mekanisk")) {
                storage.setType("HDD");
            } else {
                storage.setType("SSD");
            }
        } catch (Exception typeOfStorage) {
            typeOfStorage.printStackTrace();
        }
        try {
            storage.setFormFaktor(findFieldFromSpecification("Formfaktor"));
        } catch (Exception formFactor) {
            formFactor.printStackTrace();
        }
        try {
            storage.setSize(setSSDSize());
        } catch (Exception setSize) {
            setSize.printStackTrace();
        }
        try {
            if (getTextWithAttribute("//div[@class='slide'][2]/picture[@class='image-slide']/div[@class='center-image']/img", "src", "image url") != null) {
                storage.setImgUrl(getTextWithAttribute("//div[@class='slide'][2]/picture[@class='image-slide']/div[@class='center-image']/img", "src", "image url"));
            } else {
                storage.setImgUrl(getTextWithAttribute("//div[@class='carousel-gallery']/picture[@class='image-slide']/div[@class='center-image']/img", "src", "second image url"));
            }
        } catch (Exception image) {
            image.printStackTrace();
        }
        try {
            storage.setReadSpeed(removeAllCharactersFromNumbers(findFieldFromSpecification("Läshastighet")));
        } catch (Exception readSpeed) {
            readSpeed.printStackTrace();
        }
        try {
            storage.setWriteSpeed(removeAllCharactersFromNumbers(findFieldFromSpecification("Skrivhastighet")));
        } catch (Exception writeSpeed) {
            writeSpeed.printStackTrace();
        }
        try {
            if (storage.getFormFaktor().equals("M.2")) {
                storage.setMdot2key(findFieldFromSpecification("M.2 key"));
                storage.setMdot2size(findFieldFromSpecification("M.2 storlek"));
            }
        } catch (Exception formFactor) {
            formFactor.printStackTrace();
        }
        return storage;
    }

    @Override
    public PowerSupplyUnit parsePSU(Product product) {
        PowerSupplyUnit psu = new PowerSupplyUnit();

        Utils.waitTime((int) (Math.random() * 5000) + 1000);
        getWebPage(product.getUrl());
        psu.setUrl(product.getUrl());
        psu.setDomainName(getDomainName());
        try {
            psu.setName(getText("//section[@class='box box-body product-description']/header/h1", "name"));
        } catch (Exception name) {
            name.printStackTrace();
        }
        try {
            if (getText("//div[@class='box-body']/span[@class='price']", "price") != null) {
                psu.setPrice(removeAllCharactersFromNumbers(getText("//div[@class='box-body']/span[@class='price']", "price")));
            } else {
                psu.setPrice(removeAllCharactersFromNumbers(getText("//div[@class='box-body']/span[@class='price campaign']/span[@class='campaign-price']", "reduced price")));
            }
        } catch (Exception price) {
            price.printStackTrace();
            return null;
        }
        try {
            psu.setArticleNumber(findFieldFromSpecification("Tillverkarens ArtNr"));
        } catch (Exception artNumber) {
            artNumber.printStackTrace();
        }
        try {
            psu.setFormFactor(findFieldFromSpecification("Formfaktor"));
        } catch (Exception formFactor) {
            formFactor.printStackTrace();
        }

        try {
            psu.setCertPoints(getCertPoints(findFieldFromSpecification("80+ Certifiering")));
        }catch (NullPointerException e){
            System.out.println("didnt find certification!");
        }
        try {
            if (findFieldFromSpecification("Modulärt").equals("Ja")) {
                psu.setModular(true);
            } else {
                psu.setModular(false);
            }
        } catch (Exception modular) {
            modular.printStackTrace();
        }

        try {
            psu.setGpuConnection(findFieldFromSpecification("PCI Express"));
        } catch (Exception connection) {
            connection.printStackTrace();
        }
        try {
            psu.setCapacity(findCapacityFromSpecification());
        } catch (Exception capacity) {
            capacity.printStackTrace();
        }
        try {
            if (getTextWithAttribute("//div[@class='slides']/div[1]//img", "src", "img url") != null) {
                psu.setImgUrl(getTextWithAttribute("//div[@class='slides']/div[2]//img", "src", "img url"));
            } else {
                psu.setImgUrl(getTextWithAttribute("//div[@class='slides']/div[1]//img", "src", "img url"));
            }
        } catch (Exception imageUrl) {
            imageUrl.printStackTrace();
        }

        System.out.println(psu.toString());
        return psu;
    }

    private String findSSDSizeFromSpecification() {
        List<String> header = getListOfText("//section/dl/div/dt", "header");
        List<String> values = getListOfText("//section/dl/div/dd", "values");
        for (int i = 0; i < header.size(); i++) {
            if (header.get(i).equals("Storlek")) {
                return values.get(i);
            }
        }
        return null;
    }

    private String findSocketFromSpecification() {
        List<String> header = getListOfText("//section/dl/div/dt", "header");
        List<String> values = getListOfText("//section/dl/div/dd", "values");
        for (int i = 0; i < header.size(); i++) {
            if (header.get(i).equals("Processorsocket")) {
                String maker = values.get(i).split("\\s")[0];
                if (maker.equals("AMD") || maker.equals("Intel")) {

                    return values.get(i).replace(maker, "").trim();
                }
            }
        }
        return null;
    }

    private int findCapacityFromSpecification() {
        List<String> header = getListOfText("//section/dl/div/dt", "header");
        List<String> values = getListOfText("//section/dl/div/dd", "values");
        for (int i = 0; i < header.size(); i++) {
            if (header.get(i).equals("Effekt")) {
                String capacity = values.get(i);
                if (capacity.contains(",")) {
                    capacity = capacity.split("\\s")[0];
                }
                return removeAllCharactersFromNumbers(capacity.trim());
            }
        }
        return 0;
    }

    private int getCertPoints(String cert){

            if (cert.toLowerCase().contains("bronze")){
                return 1;
            }else if(cert.toLowerCase().contains("silver")){
                return 2;
            }else if(cert.toLowerCase().contains("gold")){
                return 3;
            }else if(cert.toLowerCase().contains("platinum")){
                return 4;
            }else if (cert.toLowerCase().contains("titanium")){
                return 5;
            }else return 0;
    }

    private int setSSDSize() {
        String ssdSize = findSSDSizeFromSpecification();
        if (ssdSize.contains(",")) {
            ssdSize = ssdSize.split(",")[0];
            if (ssdSize.contains("TB")) {
                try {
                    int sizeInGigaByte = removeAllCharactersFromNumbers(ssdSize) * 1024;
                    return sizeInGigaByte;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            } else {
                return removeAllCharactersFromNumbers(ssdSize);
            }
        } else if (ssdSize.contains("TB")) {
            try {
                int sizeInGigaByte = removeAllCharactersFromNumbers(ssdSize) * 1024;
                return sizeInGigaByte;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            return removeAllCharactersFromNumbers(ssdSize);
        }
        return removeAllCharactersFromNumbers(ssdSize);
    }

    @Override
    public Product parseType(Product product) {
        Utils.waitTime((int) (Math.random() * 5000) + 1000);
        getWebPage(product.getUrl());
        product.setTypeOfHardWare(getText("//li[2]/a/span", "type of hardware"));
        return product;
    }

    @Override
    public GraphicsCard parseGraphicsCard(Product product) {
        Utils.waitTime((int) (Math.random() * 5000) + 1000);
        GraphicsCard graphicsCard = new GraphicsCard();
        getWebPage(product.getUrl());
        String boostClock = null;
        try {
            boostClock = findFieldFromSpecification("Turbofrekvens");
        } catch (Exception definedBoostClock) {
            definedBoostClock.printStackTrace();
        }
        String coreclock = null;
        try {
            coreclock = findFieldFromSpecification("GPU-frekvens");
        } catch (Exception coreClock) {
            coreClock.printStackTrace();
        }
        try {
            graphicsCard.setArticleNumber(findFieldFromSpecification("Tillverkarens ArtNr"));
        } catch (Exception artNumber) {
            artNumber.printStackTrace();
        }
        try {
            graphicsCard.setUrl(product.getUrl());
        } catch (Exception url) {
            url.printStackTrace();
        }
        try {
            graphicsCard.setName(getText("//section[@class='box box-body product-description']/header/h1", "name"));
        } catch (Exception name) {
            name.printStackTrace();
        }
        graphicsCard.setDomainName(getDomainName());
        try {
            graphicsCard.setCudaCores(removeAllCharactersFromNumbers(findFieldFromSpecification("Streamprocessorer / pipes")));
        } catch (Exception cudaCores) {
            cudaCores.printStackTrace();
        }
        try {
            if(getTextWithAttribute("//div[@class='slides']/div[@class='slide'][2]/picture[@class='rotate-slide']/div[@class='center-rotate']/picture[@class='rotate-360']/img[1]","src","image url")!=null){
                graphicsCard.setImgUrl(getTextWithAttribute("//div[@class='slides']/div[@class='slide'][2]/picture[@class='rotate-slide']/div[@class='center-rotate']/picture[@class='rotate-360']/img[1]", "src", "img url"));
            }else if(getTextWithAttribute("//div[@class='slides']/div[@class='slide'][2]/picture[@class='image-slide']/div[@class='center-image']/img", "src", "img url") != null) {
                graphicsCard.setImgUrl(getTextWithAttribute("//div[@class='slides']/div[@class='slide'][2]/picture[@class='image-slide']/div[@class='center-image']/img", "src", "img url"));
            }else {
                graphicsCard.setImgUrl(getTextWithAttribute("//div[@class='carousel-gallery']/picture[@class='image-slide']/div[@class='center-image']/img", "src", "second image url"));
            }
        } catch (Exception img) {
            img.printStackTrace();
        }
        try {
            if (coreclock != null) {
                if (coreclock.contains("OC") || coreclock.contains("Boost")) {
                    String[] splittedCoreClock = findFieldFromSpecification("GPU-frekvens").split(",");
                    graphicsCard.setCoreClock(removeAllCharactersFromNumbers(splittedCoreClock[0]));
                } else {
                    graphicsCard.setCoreClock(removeAllCharactersFromNumbers(coreclock));
                }
            }
        } catch (Exception settingCoreClock) {
            settingCoreClock.printStackTrace();
        }
        try {
            if (boostClock != null) {
                if (boostClock.contains("OC")) {
                    String[] splittedBoostClock = findFieldFromSpecification("Turbofrekvens").split(",");
                    graphicsCard.setBoostClock(removeAllCharactersFromNumbers(splittedBoostClock[0]));
                } else {
                    graphicsCard.setBoostClock(removeAllCharactersFromNumbers(boostClock));
                }
            }
        } catch (Exception settingBoostClock) {
            settingBoostClock.printStackTrace();
        }
        try {
            graphicsCard.setConnection(findFieldFromSpecification("Strömanslutning"));
        } catch (Exception connection) {
            connection.printStackTrace();
        }

        try {
            if (graphicsCard.getBoostClock() == 0) {
                graphicsCard = getBoost(graphicsCard);
            }
        } catch (Exception boostClockAgain) {
            boostClockAgain.printStackTrace();
        }
        try {
            graphicsCard.setPrice(removeAllCharactersFromNumbers(getText("//div[@class='box-body']/span[@class='price']", "price")));
        } catch (Exception e) {
            try {
                graphicsCard.setPrice(removeAllCharactersFromNumbers(getText("//section[@class='box product-purchase']//span[@class='campaign-price']", "discounted price")));
            } catch (NullPointerException e1) {
                graphicsCard = null;
            }
        }

        try {
            graphicsCard.setCapacity(removeAllCharactersFromNumbers(findFieldFromSpecification("Rek. watt (dator)")));
        } catch (Exception capacity) {
            capacity.printStackTrace();
        }

        System.out.println(graphicsCard);
        return graphicsCard;
    }

    private GraphicsCard getBoost(GraphicsCard gpu) {
        String coreClock = findFieldFromSpecification("GPU-frekvens");
        if (coreClock != null) {
            String[] clockSpeeds = coreClock.split("[,]");
            if (clockSpeeds.length >= 2) {
                gpu.setCoreClock(removeAllCharactersFromNumbers(clockSpeeds[0]));
                if (!clockSpeeds[1].replaceAll("\\D", "").equals("")) {
                    gpu.setBoostClock(removeAllCharactersFromNumbers(clockSpeeds[1]));
                }
            }
        }
        return gpu;
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

    private String findFieldFromSpecification(String field) {
        List<String> header = getListOfText("//section/dl/div/dt", "header");
        List<String> values = getListOfText("//section/dl/div/dd", "values");
        for (int i = 0; i < header.size(); i++) {
            if (header.get(i).toLowerCase().contains(field.toLowerCase())) {
                String value = values.get(i);
                if (value.contains("+")) {
                    value = value.replaceAll("[+]", " plus ");
                }
                return value;
            }
        }
        return null;
    }


    public CentralProcessingUnit getCpuClockSpeeds(String speeds, CentralProcessingUnit cpu) {
        if (speeds != null) {
            String[] speed = speeds.split("\\s");
            List<Double> matchedSpeed = new ArrayList<>();
            for (String clockspeeds : speed) {
                if (clockspeeds.matches("[0-9][,.][0-9]")) {
                    matchedSpeed.add(Double.parseDouble(clockspeeds.replace(",", ".")));
                } else if (clockspeeds.matches("[0-9]")) {
                    matchedSpeed.add(Double.parseDouble(clockspeeds));
                } else if (Arrays.asList(speed).contains("Med turbo") && clockspeeds.matches("[0-9].[0-9]*")) {
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

    public String clearStringFromLetters(String string) {
        if (string != null) {
            return string.replaceAll("\\D", "");
        }
        return string;
    }

    public ComputerComponent updatePrice(ComputerComponent component){

        getWebPage(component.getUrl());

        try {
           WebElement alertBox = driver.findElementByXPath("//section[@class='box product-purchase']/div[@class='box-body']/div");
           if (alertBox.getText().equals("Denna produkt har utgått!")){
               return null;
           }
        }catch (NoSuchElementException e){
        }

        try {
            component.setPrice(removeAllCharactersFromNumbers(getText("//div[@class='box-body']/span[@class='price']", "price")));
        } catch (Exception e) {
            try {
                component.setPrice(removeAllCharactersFromNumbers(getText("//section[@class='box product-purchase']//span[@class='campaign-price']", "discounted price")));
            } catch (NullPointerException e1) {
                component = null;
            }
        }
        return component;
    }

}
