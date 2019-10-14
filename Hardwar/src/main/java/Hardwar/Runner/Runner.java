package Hardwar.Runner;

import Hardwar.Clients.Client;
import Hardwar.Utils.Utils;
import Hardwar.Utils.WebExport;
import com.Hardwar.Persistence.Entitys.*;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    private String method;
    private String domainName;
    private String apiHost;
    private Client client;
    private String typeOfHardWare;
    private final int parseAmount = 5;
    private final String GRAPHICSCARD_ENDPOINT = "/graphicscard/";
    private final String PRODUCTS_ENDPOINT = "product/";
    private final String CPU_ENDPOINT = "cpu/";
    private final String MOTHERBOARD_ENDPOINT = "motherboard/";
    private final String PSU_ENDPOINT = "psu/";
    private final String RAM_ENDPOINT = "ram/";
    private final String CHASSI_ENDPOINT = "chassi/";
    private final String STORAGE_ENDPOINT = "storage/";


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
                client.saveProducts(webExport.scan(),PRODUCTS_ENDPOINT);
            }
        } else if ("parse".equals(method)) {
            System.out.println("Parse method is called on domain: " + domainName);
            List<Product> products = client.getAllByDomainNameAndType(domainName, typeOfHardWare,PRODUCTS_ENDPOINT);
            if (webExport != null) {
                switch (typeOfHardWare) {
                    case "grafikkort":
                        List<GraphicsCard> parsedGraphicsCards = new ArrayList<>();
                        for (Product graphicscard : products) {
                            if (parsedGraphicsCards.size() >= parseAmount) {
                                client.updateComponents(parsedGraphicsCards,GRAPHICSCARD_ENDPOINT);
                                parsedGraphicsCards.removeAll(parsedGraphicsCards);
                            }
                            parsedGraphicsCards.add(webExport.parseGraphicsCard(graphicscard));
                            System.out.println(parsedGraphicsCards.size() + "/5 parsed!");
                        }
                        client.updateComponents(parsedGraphicsCards,GRAPHICSCARD_ENDPOINT);
                        break;
                    case "processor":
                        List<CentralProcessingUnit> parsedCPUs = new ArrayList<>();
                        for (Product processor : products) {
                            if (parsedCPUs.size() >= parseAmount) {
                                client.updateComponents(parsedCPUs,CPU_ENDPOINT);
                                parsedCPUs.removeAll(parsedCPUs);
                            }
                            parsedCPUs.add(webExport.parseCPU(processor));
                            System.out.println(parsedCPUs.size() + "/"+ parseAmount +" parsed!");
                        }
                        client.updateComponents(parsedCPUs,CPU_ENDPOINT);
                        break;
                    case "chassi":
                        List<Chassi> parsedChassis = new ArrayList<>();
                        for (Product chassi : products) {
                            if (parsedChassis.size() >= parseAmount) {
                                client.updateComponents(parsedChassis,CHASSI_ENDPOINT);
                                parsedChassis.removeAll(parsedChassis);
                            }
                            parsedChassis.add(webExport.parseChassi(chassi));
                            System.out.println(parsedChassis.size() + "/"+ parseAmount +" parsed!");
                        }
                        client.updateComponents(parsedChassis,CHASSI_ENDPOINT);
                        break;
                    case "storage":
                        List<Storage> parsedStorage = new ArrayList<>();
                        for (Product storage : products) {
                            if (parsedStorage.size() >= parseAmount) {
                                client.updateComponents(parsedStorage,STORAGE_ENDPOINT);
                                parsedStorage.removeAll(parsedStorage);
                            }
                            parsedStorage.add(webExport.parseStorage(storage));
                            System.out.println(parsedStorage.size() + "/"+ parseAmount +" parsed!");
                        }
                        client.updateComponents(parsedStorage,STORAGE_ENDPOINT);
                        break;
                    case "ram":
                        List<RandomAccessMemory> parsedRAM = new ArrayList<>();
                        for (Product RAM : products) {
                            if (parsedRAM.size() >= parseAmount) {
                                client.updateComponents(parsedRAM,RAM_ENDPOINT);
                                parsedRAM.removeAll(parsedRAM);
                            }
                            parsedRAM.add(webExport.parseRAM(RAM));
                            System.out.println(parsedRAM.size() + "/"+ parseAmount +" parsed!");
                        }
                        client.updateComponents(parsedRAM,RAM_ENDPOINT);
                        break;
                    case "psu":
                        List<PowerSupplyUnit> parsedPSU = new ArrayList<>();
                        for (Product PSU : products) {
                            if (parsedPSU.size() >= parseAmount) {
                                client.updateComponents(parsedPSU,PSU_ENDPOINT);
                                parsedPSU.removeAll(parsedPSU);
                            }
                            parsedPSU.add(webExport.parsePSU(PSU));
                            System.out.println(parsedPSU.size() + "/"+ parseAmount +" parsed!");
                        }
                        client.updateComponents(parsedPSU,PSU_ENDPOINT);
                        break;
                    case "moderkort":
                        List<MotherBoard> parsedMotherBoard = new ArrayList<>();
                        for (Product motherBoard : products) {
                            if (parsedMotherBoard.size() >= parseAmount) {
                                client.updateComponents(parsedMotherBoard,MOTHERBOARD_ENDPOINT);
                                parsedMotherBoard.removeAll(parsedMotherBoard);
                            }
                            parsedMotherBoard.add(webExport.parseMotherBoard(motherBoard));
                            System.out.println(parsedMotherBoard.size() + "/"+ parseAmount +" parsed!");
                        }
                        client.updateComponents(parsedMotherBoard,MOTHERBOARD_ENDPOINT);
                        break;

                }

            }
        } else if ("type".equals(method)) {
            List<Product> allProductByDomain = client.getAllNullAndDomain(domainName,PRODUCTS_ENDPOINT);
            List<Product> parsedProducts = new ArrayList<>();
            for (Product product : allProductByDomain) {
                if (parsedProducts.size() >= 5) {
                    client.updateProductTypes(parsedProducts,PRODUCTS_ENDPOINT);
                    parsedProducts.removeAll(parsedProducts);
                }
                parsedProducts.add(webExport.parseType(product));
                System.out.println(parsedProducts.size() + "/5 parsed");
            }
            client.updateProductTypes(parsedProducts,PRODUCTS_ENDPOINT);
        }
    }
}
