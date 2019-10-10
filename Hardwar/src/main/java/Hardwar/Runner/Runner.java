package Hardwar.Runner;

import Hardwar.Client;
import Hardwar.Utils.Utils;
import Hardwar.Utils.WebExport;
import com.Hardwar.Persistence.Entitys.CentralProcessingUnit;
import com.Hardwar.Persistence.Entitys.GraphicsCard;
import com.Hardwar.Persistence.Entitys.Product;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    private String method;
    private String domainName;
    private String apiHost;
    private Client client;
    private String typeOfHardWare;

    public Runner(String method, String domainName, String apiHost, Client client, String typeOfHardWare) {
        this.domainName = domainName;
        this.method = method;
        this.apiHost = apiHost;
        this.client = client;
        this.typeOfHardWare = typeOfHardWare;
    }

    public static void main(String[] args) {
        String chromeDriver = args[0];
        String method = args[1];
        String domainName = args[2];
        String apiHost = args[3];
        String typeOfHardWare = args[4];
        System.setProperty("webdriver.chrome.driver", chromeDriver);
        Client client = new Client(apiHost);
        Runner runner = new Runner(method, domainName, apiHost, client, typeOfHardWare);
        runner.run();
    }

    private void run() {
        WebExport webExport = Utils.instantiateWebExport(domainName);
        if ("scan".equals(method)) {
            System.out.println("Scanning method is called on domain: " + domainName);
            if (webExport != null) {
                client.saveProducts(webExport.scan());
            }
        } else if ("parse".equals(method)) {
            System.out.println("Parse method is called on domain: " + domainName);
            List<Product> products = client.getAllByDomainNameAndType(domainName, typeOfHardWare);
            if (webExport != null) {
                switch (typeOfHardWare) {
                    case "grafikkort":
                        List<GraphicsCard> parsedGraphicsCards = new ArrayList<>();
                        for (Product graphicscard : products) {
                            if (parsedGraphicsCards.size() >= 5) {
                                client.updateGraphicsCard(parsedGraphicsCards);
                                parsedGraphicsCards.removeAll(parsedGraphicsCards);
                            }
                            parsedGraphicsCards.add(webExport.parseGraphicsCard(graphicscard));
                            System.out.println(parsedGraphicsCards.size() + "/5 parsed!");
                        }
                        client.updateGraphicsCard(parsedGraphicsCards);
                        break;
                    case "processor":
                        List<CentralProcessingUnit> parsedCPUs = new ArrayList<>();
                        for (Product processor : products) {
                            if (parsedCPUs.size() >= 5) {
                                client.updateCPUs(parsedCPUs);
                                parsedCPUs.removeAll(parsedCPUs);
                            }
                            parsedCPUs.add(webExport.parseCPU(processor));
                            System.out.println(parsedCPUs.size() + "/5 parsed!");
                        }
                        client.updateCPUs(parsedCPUs);
                        break;
                }

            }
        } else if ("type".equals(method)) {
            List<Product> allProductByDomain = client.getAllNullAndDomain(domainName);
            List<Product> parsedProducts = new ArrayList<>();
            for (Product product : allProductByDomain) {
                if (parsedProducts.size() >= 5) {
                    client.updateProductTypes(parsedProducts);
                    parsedProducts.removeAll(parsedProducts);
                }
                parsedProducts.add(webExport.parseType(product));
                System.out.println(parsedProducts.size() + "/5 parsed");
            }
            client.updateProductTypes(parsedProducts);
        }
    }
}
