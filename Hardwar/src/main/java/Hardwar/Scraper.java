package Hardwar;

import Hardwar.Utils.WebExport;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Scraper implements WebExport {

    protected ChromeDriver driver;

    public Scraper(){

        this.driver = new ChromeDriver(getDesiredCapabilitiesConfiguration(getChromeOptionsConfiguration()));

    }
    public Scraper(ChromeOptions options){

        this.driver = new ChromeDriver(options);

    }

    public String clearStringFromLetters(String string){
        if(string != null){
            return string.replaceAll("\\D","");
        }
        return string;
    }

    private ChromeOptions getChromeOptionsConfiguration(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        options.addArguments("--start-maximized");
        return options;

    }

    private DesiredCapabilities getDesiredCapabilitiesConfiguration(ChromeOptions options){
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(ChromeOptions.CAPABILITY,options);
        return cap;
    }


    public String getText(String xPath,String field){
        String text = null;
        try {
            text = cleanWeirdCharacters(driver.findElementByXPath(xPath).getText());
        }catch (NoSuchElementException e){
            System.out.println("Failed to get " + field + " " + driver.getCurrentUrl());
        }
        return text;
    }

    public String getTextWithAttribute(String xPath,String attribute,String field){
        String text = null;
        try {
            text = cleanWeirdCharacters(driver.findElementByXPath(xPath).getAttribute(attribute));
        }catch (NoSuchElementException e){
            System.out.println("Failed to get " + field + " " + driver.getCurrentUrl());
        }
        return text;
    }

    public List<String> getHrefs(String xPath,String field){
        List<String> hrefs = null;
        try {
            hrefs = driver.findElementsByXPath(xPath)
            .stream()
            .map(we->we.getAttribute("href"))
            .collect(Collectors.toList());
        }catch (NoSuchElementException e){
            System.out.println("Failed to get " + field + " " + driver.getCurrentUrl());
        }
        return hrefs;
    }

    public List<String> getListOfText(String xPath,String field){
        List<String> textList = null;
        try {
            textList = driver.findElementsByXPath(xPath)
                    .stream()
                    .map(we->cleanWeirdCharacters(we.getAttribute("innerText")))
                    .collect(Collectors.toList());
        }catch (NoSuchElementException e){
            System.out.println("Failed to get " + field + " " + driver.getCurrentUrl());
        }
        return textList;
    }

    public List<WebElement> getListOfElement(String xPath,String field){
        List<WebElement> elementList = null;
        try {
            elementList  = driver.findElementsByXPath(xPath);
        }catch (NoSuchElementException e){
            System.out.println("Failed to get " + field + " " + driver.getCurrentUrl());
        }
        return elementList;
    }

    public WebElement getWebElement(String xPath, String field){
        WebElement element = null;
        try {
            element = driver.findElementByXPath(xPath);
        }catch (NoSuchElementException e){
            System.out.println("Failed to get " + field + " " + driver.getCurrentUrl());
        }
        return element;
    }

    public void getWebPage(String url){
        try {
            driver.get(url);
        }catch (Exception e){
            System.out.println("Error connecting to: " + driver.getCurrentUrl());
        }
    }
    public String cleanString(String string){
        return string.replaceAll("\\W","");
    }
    public int removeAllCharactersFromNumbers(String numbers){
        return Integer.parseInt(numbers.replaceAll("\\D",""));
    }
    public String cleanWeirdCharacters(String string){
        return string.replaceAll("[®™]","");
    }


}
