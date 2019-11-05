package Hardwar.Scrapers;

import Hardwar.Domain;
import Hardwar.Scraper;
import Hardwar.Utils.Utils;
import com.Hardwar.Persistence.Entitys.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

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
        CentralProcessingUnit cpu = new CentralProcessingUnit();
        try {
            Utils.waitTime((int) (Math.random() * 5000) + 1000);
            getWebPage(product.getUrl());
            cpu.setUrl(product.getUrl());
            cpu.setDomain(getDomainName());
            cpu.setCores(removeAllCharactersFromNumbers(findFieldFromSpecification("Antal kärnor")));
            cpu.setThreads(removeAllCharactersFromNumbers(findFieldFromSpecification("Antal trådar")));
            cpu.setSocket(findSocketFromSpecification());
            cpu.setArticleNumber(findFieldFromSpecification("Tillverkarens ArtNr"));
            cpu.setName(getText("//section[@class='box box-body product-description']//h1", "name"));
            cpu.setImgUrl(getTextWithAttribute("//div[@class='slide'][2]/picture[@class='image-slide']/div/img", "src", "image url"));
            try {
                cpu.setPrice(removeAllCharactersFromNumbers(getText("//div[@class='box-body']/span[@class='price']", "price")));
            } catch (Exception e) {
                cpu.setPrice(removeAllCharactersFromNumbers(getText("//span[@class='campaign-price']", "reduced price")));
            }
            cpu = getCpuClockSpeeds(findFieldFromSpecification("Hastighet"), cpu);
            System.out.println(cpu);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        try {
            Utils.waitTime((int) (Math.random() * 5000) + 1000);
            getWebPage(product.getUrl());
            ram.setUrl(product.getUrl());
            ram.setName(getText("//section[@class='box box-body product-description']/header/h1", "name"));
            ram.setDomainName(getDomainName());
            ram.setDdr(findFieldFromSpecification("Internminnestyp"));
            ram.setSpeeds(removeAllCharactersFromNumbers(findFieldFromSpecification("Minnesfrekvens (MHz)")));
            ram.setCapacity(removeAllCharactersFromNumbers(findFieldFromSpecification("Kapacitet")));
            ram.setArticleNumber(findFieldFromSpecification("Tillverkarens ArtNr"));
            try {
                ram.setPrice(removeAllCharactersFromNumbers(getText("//div[@class='box-body']/span[@class='price']", "price")));
            } catch (Exception e) {
                ram.setPrice(removeAllCharactersFromNumbers(getText("//span[@class='campaign-price']", "reduced price")));
            }
            System.out.println(ram);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ram;
    }

    @Override
    public Chassi parseChassi(Product product) {
        Chassi chassi = new Chassi();
        try {
            Utils.waitTime((int) (Math.random() * 5000) + 1000);
            getWebPage(product.getUrl());
            chassi.setUrl(product.getUrl());
            chassi.setDomainName(getDomainName());
            chassi.setName(getText("//section[@class='box box-body product-description']/header/h1", "name"));
            chassi.setArticleNumber(findFieldFromSpecification("Tillverkarens ArtNr"));
            chassi.setImgUrl(getTextWithAttribute("//div[1]/picture/div/div[2]/picture/div/img", "src", "image url"));
            try {
                chassi.setPrice(removeAllCharactersFromNumbers(getText("//div[@class='box-body']/span[@class='price']", "price")));
            } catch (Exception e) {
                chassi.setPrice(removeAllCharactersFromNumbers(getText("//span[@class='campaign-price']", "reduced price")));
            }
            chassi.setMaxHeightCpuCooler(findFieldFromSpecification("Max höjd CPU-kylare"));
            chassi.setMaxGpuWidth(findFieldFromSpecification("Max längd grafikkort (standardutförande)"));
            chassi.setFormFactor(findFieldFromSpecification("Formfaktor"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return chassi;
    }

    @Override
    public Storage parseStorage(Product product) {
        Storage storage = new Storage();
        try {
            Utils.waitTime((int) (Math.random() * 5000) + 1000);
            getWebPage(product.getUrl());
            storage.setUrl(product.getUrl());
            storage.setDomainName(getDomainName());
            storage.setName(getText("//section[@class='box box-body product-description']/header/h1", "name"));
            try {
                storage.setPrice(removeAllCharactersFromNumbers(getText("//div[@class='box-body']/span[@class='price']", "price")));
            } catch (Exception e) {
                storage.setPrice(removeAllCharactersFromNumbers(getText("//span[@class='campaign-price']", "reduced price")));
            }
            storage.setArticleNumber(findFieldFromSpecification("Tillverkarens ArtNr"));
            if (getText("//li[2]/a/span", " type of storage").equals("Hårddisk Mekanisk")) {
                storage.setType("HDD");
            } else {
                storage.setType("SSD");
            }
            storage.setFormFaktor(findFieldFromSpecification("Formfaktor"));
            storage.setSize(setSSDSize());
            storage.setImgUrl(getTextWithAttribute("//div[@class='slides']/div[1]//img", "src", "img url"));
            storage.setReadSpeed(removeAllCharactersFromNumbers(findFieldFromSpecification("Läshastighet")));
            storage.setWriteSpeed(removeAllCharactersFromNumbers(findFieldFromSpecification("Skrivhastighet")));
            if (storage.getFormFaktor().equals("M.2")) {
                storage.setMdot2key(findFieldFromSpecification("M.2 key"));
                storage.setMdot2size(findFieldFromSpecification("M.2 storlek"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return storage;
    }

    @Override
    public PowerSupplyUnit parsePSU(Product product) {
        PowerSupplyUnit psu = new PowerSupplyUnit();
        try {
            Utils.waitTime((int) (Math.random() * 5000) + 1000);
            getWebPage(product.getUrl());
            psu.setUrl(product.getUrl());
            psu.setName(getText("//section[@class='box box-body product-description']/header/h1", "name"));
            psu.setDomainName(getDomainName());
            try {
                psu.setPrice(removeAllCharactersFromNumbers(getText("//div[@class='box-body']/span[@class='price']", "price")));
            } catch (Exception e) {
                psu.setPrice(removeAllCharactersFromNumbers(getText("//div[@class='box-body']/span[@class='price campaign']/span[@class='campaign-price']", "reduced price")));
            }
            psu.setArticleNumber(findFieldFromSpecification("Tillverkarens ArtNr"));
            psu.setFormFactor(findFieldFromSpecification("Formfaktor"));
            if (findFieldFromSpecification("Modulärt").equals("Ja")) {
                psu.setModular(true);
            } else {
                psu.setModular(false);
            }

            psu.setGPUconnection(findFieldFromSpecification("PCI Express"));
            psu.setCapacity(findCapacityFromSpecification());
            psu.setImgUrl(getTextWithAttribute("//div[@class='slides']/div[1]//img", "src", "img url"));

        } catch (Exception e) {
            e.printStackTrace();
        }
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
                if (maker.equals("AMD")||maker.equals("Intel")){

                    return values.get(i).replace(maker,"").trim();
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
                if (capacity.contains(",")){
                    capacity = capacity.split("\\s")[0];
                }
                return removeAllCharactersFromNumbers(capacity.trim());
            }
        }
        return 0;
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
        }else if (ssdSize.contains("TB")){
            try {
                int sizeInGigaByte = removeAllCharactersFromNumbers(ssdSize) * 1024;
                return sizeInGigaByte;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }else {
            return removeAllCharactersFromNumbers(ssdSize);
        }
        return removeAllCharactersFromNumbers(ssdSize);
    }

        @Override
        public Product parseType (Product product){
            Utils.waitTime((int) (Math.random() * 5000) + 1000);
            System.out.println((int) (Math.random() * 5000) + 1000);
            getWebPage(product.getUrl());
            product.setTypeOfHardWare(getText("//li[2]/a/span", "type of hardware"));
            return product;
        }

        @Override
        public GraphicsCard parseGraphicsCard (Product product){
            Utils.waitTime((int) (Math.random() * 5000) + 1000);
            GraphicsCard graphicsCard = new GraphicsCard();
            getWebPage(product.getUrl());
            System.out.println(product.getUrl());
            String boostClock = findFieldFromSpecification("Turbofrekvens");
            String coreclock = findFieldFromSpecification("GPU-frekvens");
            graphicsCard.setArticleNumber(findFieldFromSpecification("Tillverkarens ArtNr"));
            graphicsCard.setUrl(product.getUrl());
            graphicsCard.setName(getText("//section[@class='box box-body product-description']/header/h1", "name"));
            try {
                graphicsCard.setPrice(removeAllCharactersFromNumbers(getText("//div[@class='box-body']/span[@class='price']", "price")));
            }catch(Exception e){
                graphicsCard.setPrice(removeAllCharactersFromNumbers(getText("//section[@class='box product-purchase']//span[@class='campaign-price']", "discounted price")));;
            }

            graphicsCard.setDomainName(getDomainName());
            graphicsCard.setCudaCores(removeAllCharactersFromNumbers(findFieldFromSpecification("Streamprocessorer / pipes")));
            graphicsCard.setImgUrl(getTextWithAttribute("//div[@class='slides']/div[1]//img", "src", "img url"));
            if (coreclock != null) {
                if (coreclock.contains("OC")) {
                    String[] splittedCoreClock = findFieldFromSpecification("GPU-frekvens").split(",");
                    graphicsCard.setCoreClock(removeAllCharactersFromNumbers(splittedCoreClock[0]));
                } else {
                    graphicsCard.setCoreClock(removeAllCharactersFromNumbers(coreclock));
                }
            }
            if (boostClock != null) {
                if (boostClock.contains("OC")) {
                    String[] splittedBoostClock = findFieldFromSpecification("Turbofrekvens").split(",");
                    graphicsCard.setBoostClock(removeAllCharactersFromNumbers(splittedBoostClock[0]));
                } else {
                    graphicsCard.setBoostClock(removeAllCharactersFromNumbers(boostClock));
                }
            }
            graphicsCard.setConnection(findFieldFromSpecification("Strömanslutning"));

            if (graphicsCard.getBoostClock() == 0) {
                graphicsCard = getBoost(graphicsCard);
            }

            System.out.println(graphicsCard);
            return graphicsCard;
        }

        private GraphicsCard getBoost (GraphicsCard gpu){
            String coreClock = findFieldFromSpecification("GPU-frekvens");
            if (coreClock != null) {
                String[] clockSpeeds = coreClock.split("[,]");
                if (clockSpeeds.length >= 2) {
                    gpu.setCoreClock(removeAllCharactersFromNumbers(clockSpeeds[0]));
                    gpu.setBoostClock(removeAllCharactersFromNumbers(clockSpeeds[1]));
                }
            }
            return gpu;
        }

        @Override
        public String getDomainName () {
            return "inet.se";
        }

        private WebElement getWebElement (String xPath){
            WebElement element;
            try {
                element = driver.findElementByXPath(xPath);
            } catch (NoSuchElementException e) {
                System.out.println("Didnt find element : " + driver.getCurrentUrl());
                return null;
            }
            return element;
        }

        private String findFieldFromSpecification (String field){
            List<String> header = getListOfText("//section/dl/div/dt", "header");
            List<String> values = getListOfText("//section/dl/div/dd", "values");
            for (int i = 0; i < header.size(); i++) {
                if (header.get(i).toLowerCase().contains(field.toLowerCase())) {
                    return values.get(i);
                }
            }
            return null;
        }


        public CentralProcessingUnit getCpuClockSpeeds (String speeds, CentralProcessingUnit cpu){
            if (speeds != null) {
                String[] speed = speeds.split("\\s");
                List<Double> matchedSpeed = new ArrayList<>();
                for (String clockspeeds : speed) {
                    if (clockspeeds.matches("[0-9][,.][0-9]")) {
                        matchedSpeed.add(Double.parseDouble(clockspeeds.replace(",", ".")));
                    } else if (clockspeeds.matches("[0-9]")) {
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

        public String clearStringFromLetters (String string){
            if (string != null) {
                return string.replaceAll("\\D", "");
            }
            return string;
        }

    }
