package Hardwar.Utils;

import Hardwar.Domain;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Utils {

    public static void waitTime(int sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void printTimeLength(long startTime) {
        long duration = System.nanoTime() - startTime;
        System.out.printf("%s%02d:%02d:%02d%n", "Running time: ",
                TimeUnit.NANOSECONDS.toHours(duration),
                TimeUnit.NANOSECONDS.toMinutes(duration) % 60,
                TimeUnit.NANOSECONDS.toSeconds(duration) % 60);
    }

    public static String extractDomainName(String url) {
        Matcher matcher = Pattern.compile("^(?:https?:\\/\\/)?(?:[^@\\n]+@)?(?:www\\.)?([^:\\/\\n?]+)").matcher(url);
        if (matcher.find()) {
            if (!matcher.group().contains("www.")) {
                return matcher.group().split("://")[1];
            } else {
                return matcher.group().split("www.")[1];
            }
        } else {
            System.out.println("Couldn't extract the domain name from " + url);
            return null;
        }
    }

    public static WebExport instantiateWebExport(String domainName) {
        Reflections reflections = new Reflections("Hardwar");
        Optional<Class<? extends WebExport>> optional = reflections.getSubTypesOf(WebExport.class).stream()
                .filter(clazz -> clazz.isAnnotationPresent(Domain.class))
                .filter(clazz -> domainName.equals(clazz.getAnnotation(Domain.class).value()))
                .findAny();
        if (optional.isPresent()) {
            Class<? extends WebExport> clazz = optional.get();
            try {
                return clazz.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static WebExport lookupByDomain(String domainName) {

        Reflections reflections = new Reflections("Hardwar");
        Set<Class<? extends WebExport>> allClasses = reflections.getSubTypesOf(WebExport.class);
        for (Class<?> clazz : allClasses) {
            try {
                Method method = clazz.getMethod("getDomainName");
                Object instance = Class.forName(clazz.getName()).newInstance();
                String instanceDomainName = (String) method.invoke(instance);

                if (instanceDomainName.equals(domainName)) {
                    System.out.println("Found domain name: " + instanceDomainName);
                    return (WebExport) instance;
                }
            } catch (SecurityException | NoSuchMethodException | IllegalAccessException | InvocationTargetException |
                    InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void scrollToLastProduct(ChromeDriver driver){
        JavascriptExecutor jse = driver;
        Long height;
        Long newHeight;
        do {
            height = (Long) jse.executeScript("return document.body.scrollHeight");
            jse.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Utils.waitTime(2000);
            newHeight = (Long) jse.executeScript("return document.body.scrollHeight");
        } while (!height.equals(newHeight));

    }
    public static void scrollToBottom(ChromeDriver driver){

        JavascriptExecutor jse = driver;
        jse.executeScript("window.scrollTo(0, document.body.scrollHeight);");

    }

    public static void scrollByPixel(ChromeDriver driver, String pixel){

        JavascriptExecutor jse = driver;
        jse.executeScript("window.scrollTo(0, "+ pixel + ");");

    }

}