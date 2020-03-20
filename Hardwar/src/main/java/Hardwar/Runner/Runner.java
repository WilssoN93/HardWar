package Hardwar.Runner;

import Hardwar.Clients.Client;
import Hardwar.Utils.Utils;
import Hardwar.Utils.WebExport;
import com.Hardwar.Persistence.Entitys.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Runner {
    private String method;
    private String domainName;
    private String apiHost;
    private Client client;
    private String typeOfHardWare;
    private final int parseAmount = 5;
    private final String GRAPHICSCARD_ENDPOINT = "/graphicscard";
    private final String PRODUCTS_ENDPOINT = "/product";
    private final String CPU_ENDPOINT = "/cpu";
    private final String MOTHERBOARD_ENDPOINT = "/motherboards";
    private final String PSU_ENDPOINT = "/psu";
    private final String RAM_ENDPOINT = "/ram";
    private final String CHASSI_ENDPOINT = "/chassi";
    private final String STORAGE_ENDPOINT = "/storage";


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
                client.saveProducts(webExport.scan(), PRODUCTS_ENDPOINT);
            }
        } else if ("parse".equals(method)) {
            System.out.println("Parse method is called on domain: " + domainName);
            List<Product> products = client.getAllByDomainNameAndType(domainName, typeOfHardWare, PRODUCTS_ENDPOINT);
            List<Product> parsedProducts = new ArrayList<>();
            if (webExport != null) {
                switch (typeOfHardWare) {
                    case "grafikkort":
                        List<GraphicsCard> parsedGraphicsCards = new ArrayList<>();
                        for (Product graphicscard : products) {
                            if (parsedGraphicsCards.size() >= parseAmount) {
                                client.updateComponents(parsedGraphicsCards, GRAPHICSCARD_ENDPOINT);
                                parsedGraphicsCards.removeAll(parsedGraphicsCards);
                            }
                            GraphicsCard gpu = webExport.parseGraphicsCard(graphicscard);
                            if (gpu != null) {
                                graphicscard.setParsed(true);
                                parsedProducts.add(graphicscard);
                                parsedGraphicsCards.add(gpu);
                                System.out.println(parsedGraphicsCards.size() + "/5 parsed!");
                            } else {
                                client.deleteProduct(graphicscard, PRODUCTS_ENDPOINT);
                            }
                        }
                        client.updateComponents(parsedGraphicsCards, GRAPHICSCARD_ENDPOINT);
                        break;
                    case "processor":
                        List<CentralProcessingUnit> parsedCPUs = new ArrayList<>();
                        for (Product processor : products) {
                            if (parsedCPUs.size() >= parseAmount) {
                                client.updateComponents(parsedCPUs, CPU_ENDPOINT);
                                parsedCPUs.removeAll(parsedCPUs);
                            }
                            CentralProcessingUnit cpu = webExport.parseCPU(processor);
                            if (cpu != null) {
                                processor.setParsed(true);
                                parsedProducts.add(processor);
                                parsedCPUs.add(cpu);
                                System.out.println(parsedCPUs.size() + "/" + parseAmount + " parsed!");
                            } else {
                                client.deleteProduct(processor, PRODUCTS_ENDPOINT);
                            }
                        }
                        client.updateComponents(parsedCPUs, CPU_ENDPOINT);
                        break;
                    case "chassi":
                        List<Chassi> parsedChassis = new ArrayList<>();
                        for (Product chassi : products) {
                            if (parsedChassis.size() >= parseAmount) {
                                client.updateComponents(parsedChassis, CHASSI_ENDPOINT);
                                parsedChassis.removeAll(parsedChassis);
                            }
                            Chassi parsedChassi = webExport.parseChassi(chassi);
                            if (parsedChassi != null) {
                                chassi.setParsed(true);
                                parsedProducts.add(chassi);
                                parsedChassis.add(parsedChassi);
                                System.out.println(parsedChassis.size() + "/" + parseAmount + " parsed!");
                            } else {
                                client.deleteProduct(chassi, PRODUCTS_ENDPOINT);
                            }

                        }
                        client.updateComponents(parsedChassis, CHASSI_ENDPOINT);
                        break;
                    case "disk":
                        List<Storage> parsedStorage = new ArrayList<>();
                        for (Product storage : products) {
                            if (parsedStorage.size() >= parseAmount) {
                                client.updateComponents(parsedStorage, STORAGE_ENDPOINT);
                                parsedStorage.removeAll(parsedStorage);
                            }
                            Storage parsedStorageDevice = webExport.parseStorage(storage);
                            if (parsedStorageDevice != null) {
                                storage.setParsed(true);
                                parsedProducts.add(storage);
                                parsedStorage.add(parsedStorageDevice);
                                System.out.println(parsedStorage.size() + "/" + parseAmount + " parsed!");
                            } else {
                                client.deleteProduct(storage, PRODUCTS_ENDPOINT);
                            }
                        }
                        client.updateComponents(parsedStorage, STORAGE_ENDPOINT);
                        break;
                    case "minne":
                        List<RandomAccessMemory> parsedRAM = new ArrayList<>();
                        for (Product RAM : products) {
                            if (parsedRAM.size() >= parseAmount) {
                                client.updateComponents(parsedRAM, RAM_ENDPOINT);
                                parsedRAM.removeAll(parsedRAM);
                            }
                            RandomAccessMemory randomAccessMemory = webExport.parseRAM(RAM);
                            if (randomAccessMemory != null) {
                                RAM.setParsed(true);
                                parsedProducts.add(RAM);
                                parsedRAM.add(randomAccessMemory);
                                System.out.println(parsedRAM.size() + "/" + parseAmount + " parsed!");
                            } else {
                                client.deleteProduct(RAM, PRODUCTS_ENDPOINT);
                            }
                        }
                        client.updateComponents(parsedRAM, RAM_ENDPOINT);
                        break;
                    case "psu":
                        List<PowerSupplyUnit> parsedPSU = new ArrayList<>();
                        for (Product PSU : products) {
                            if (parsedPSU.size() >= parseAmount) {
                                client.updateComponents(parsedPSU, PSU_ENDPOINT);
                                parsedPSU.removeAll(parsedPSU);
                            }
                            PowerSupplyUnit powerSupplyUnit = webExport.parsePSU(PSU);
                            if (powerSupplyUnit != null) {
                                PSU.setParsed(true);
                                parsedProducts.add(PSU);
                                parsedPSU.add(powerSupplyUnit);
                                System.out.println(parsedPSU.size() + "/" + parseAmount + " parsed!");
                            } else {
                                client.deleteProduct(PSU, PRODUCTS_ENDPOINT);
                            }
                        }
                        client.updateComponents(parsedPSU, PSU_ENDPOINT);
                        break;
                    case "moderkort":
                        List<MotherBoard> parsedMotherBoards = new ArrayList<>();
                        for (Product motherBoard : products) {
                            if (parsedMotherBoards.size() >= parseAmount) {
                                client.updateComponents(parsedMotherBoards, MOTHERBOARD_ENDPOINT);
                                parsedMotherBoards.removeAll(parsedMotherBoards);
                            }
                            MotherBoard parsedMotherboard = webExport.parseMotherBoard(motherBoard);
                            if (parsedMotherboard != null) {
                                motherBoard.setParsed(true);
                                parsedProducts.add(motherBoard);
                                parsedMotherBoards.add(parsedMotherboard);
                                System.out.println(parsedMotherBoards.size() + "/" + parseAmount + " parsed!");
                            } else {
                                client.deleteProduct(motherBoard, PRODUCTS_ENDPOINT);
                            }
                        }
                        client.updateComponents(parsedMotherBoards, MOTHERBOARD_ENDPOINT);
                        break;

                }
                client.saveProducts(parsedProducts,PRODUCTS_ENDPOINT);
            }
        } else if ("type".equals(method)) {
            List<Product> allProductByDomain = client.getAllNullAndDomain(domainName, PRODUCTS_ENDPOINT);
            List<Product> parsedProducts = new ArrayList<>();
            for (Product product : allProductByDomain) {
                if (parsedProducts.size() >= 5) {
                    client.updateProductTypes(parsedProducts, PRODUCTS_ENDPOINT);
                    parsedProducts.removeAll(parsedProducts);
                }
                parsedProducts.add(webExport.parseType(product));
                System.out.println(parsedProducts.size() + "/5 parsed");
            }
            client.updateProductTypes(parsedProducts, PRODUCTS_ENDPOINT);
        } else if ("update".equals(method)) {
            List<? extends ComputerComponent> components = client.getAllComponentsByDomainName(domainName);
            System.out.println("Update is Called on domain: " + domainName);
            List<GraphicsCard> graphicsCards = new ArrayList<>();
            List<MotherBoard> motherboards = new ArrayList<>();
            List<PowerSupplyUnit> psus = new ArrayList<>();
            List<RandomAccessMemory> ram = new ArrayList<>();
            List<CentralProcessingUnit> cpus = new ArrayList<>();
            List<Storage> storages = new ArrayList<>();
            List<Chassi> chassis = new ArrayList<>();
            for (ComputerComponent component : components) {
                if (webExport != null) {
                    if (component.getClass().equals(GraphicsCard.class)) {
                        System.out.println("Old Price: " + component.getPrice());
                        GraphicsCard graphicsCard = (GraphicsCard) webExport.updatePrice(component);
                        if (graphicsCards.size() >= 5){
                            client.updateComponents(graphicsCards,GRAPHICSCARD_ENDPOINT);
                            graphicsCards.removeAll(graphicsCards);
                        }
                        if (graphicsCard != null){
                            System.out.println("New Price: " + graphicsCard.getPrice());
                            graphicsCards.add(graphicsCard);
                            System.out.println(graphicsCards.size() + " graphicscards has been updated!");
                        }else {
                            client.deleteComponent(component,GRAPHICSCARD_ENDPOINT);
                            System.out.println(component.getName() + " Deleted! : URL: " + component.getUrl());
                        }
                    } else if (component.getClass().equals(MotherBoard.class)) {
                        System.out.println("Old Price: " + component.getPrice());
                        MotherBoard motherBoard = (MotherBoard) webExport.updatePrice(component);
                        if (motherboards.size() >= 5){
                            client.updateComponents(graphicsCards,MOTHERBOARD_ENDPOINT);
                            motherboards.removeAll(motherboards);
                        }
                        if (motherBoard != null){
                            System.out.println("New Price: " + motherBoard.getPrice());
                            motherboards.add(motherBoard);
                            System.out.println(motherboards.size() + " motherboards has been updated!");
                        }else {
                            client.deleteComponent(component,MOTHERBOARD_ENDPOINT);
                            System.out.println(component.getName() + " Deleted! : URL: " + component.getUrl());
                        }
                    } else if (component.getClass().equals(RandomAccessMemory.class)) {
                        System.out.println("Old Price: " + component.getPrice());
                        RandomAccessMemory randomAccessMemory = (RandomAccessMemory) webExport.updatePrice(component);
                        if (ram.size() >= 5){
                            client.updateComponents(ram,RAM_ENDPOINT);
                            ram.removeAll(ram);
                        }
                        if (randomAccessMemory != null){
                            System.out.println("New Price: " + randomAccessMemory.getPrice());
                            ram.add(randomAccessMemory);
                            System.out.println(ram.size() + " ram has been updated!");
                        }else {
                            client.deleteComponent(component,RAM_ENDPOINT);
                            System.out.println(component.getName() + " Deleted! : URL: " + component.getUrl());
                        }
                    } else if (component.getClass().equals(PowerSupplyUnit.class)) {
                        System.out.println("Old Price: " + component.getPrice());
                        PowerSupplyUnit powerSupplyUnit = (PowerSupplyUnit) webExport.updatePrice(component);
                        if (psus.size() >= 5){
                            client.updateComponents(psus,PSU_ENDPOINT);
                            psus.removeAll(psus);
                        }
                        if (powerSupplyUnit != null){
                            System.out.println("New Price: " + powerSupplyUnit.getPrice());
                            psus.add(powerSupplyUnit);
                            System.out.println(psus.size() + " PSU's has been updated!");
                        }else {
                            client.deleteComponent(component,PSU_ENDPOINT);
                            System.out.println(component.getName() + " Deleted! : URL: " + component.getUrl());
                        }
                    } else if (component.getClass().equals(Storage.class)) {
                        System.out.println("Old Price: " + component.getPrice());
                        Storage storage = (Storage) webExport.updatePrice(component);
                        if (storages.size() >= 5){
                            client.updateComponents(storages,STORAGE_ENDPOINT);
                            storages.removeAll(storages);
                        }
                        if (storage != null){
                            System.out.println("New Price: " + storage.getPrice());
                            storages.add(storage);
                            System.out.println(storages.size() + " storage's has been updated!");
                        }else {
                            client.deleteComponent(component,PSU_ENDPOINT);
                            System.out.println(component.getName() + " Deleted! : URL: " + component.getUrl());
                        }
                    } else if (component.getClass().equals(CentralProcessingUnit.class)) {
                        System.out.println("Old Price: " + component.getPrice());
                        CentralProcessingUnit cpu = (CentralProcessingUnit) webExport.updatePrice(component);
                        if (cpus.size() >= 5){
                            client.updateComponents(cpus,CPU_ENDPOINT);
                            cpus.removeAll(cpus);
                        }
                        if (cpu != null){
                            System.out.println("New Price: " + cpu.getPrice());
                            cpus.add(cpu);
                            System.out.println(cpus.size() + " cpu's has been updated!");
                        }else {
                            client.deleteComponent(component,CPU_ENDPOINT);
                            System.out.println(component.getName() + " Deleted! : URL: " + component.getUrl());
                        }

                    } else if (component.getClass().equals(Chassi.class)) {
                        System.out.println("Old Price: " + component.getPrice());
                        Chassi chassi = (Chassi) webExport.updatePrice(component);
                        if (chassis.size() >= 5){
                            client.updateComponents(chassis,CHASSI_ENDPOINT);
                            chassis.removeAll(chassis);
                        }
                        if (chassi != null){
                            System.out.println("new Price: " + chassi.getPrice());
                            chassis.add(chassi);
                            System.out.println(chassis.size() + " chassi's has been updated!");
                        }else {
                            client.deleteComponent(component,CHASSI_ENDPOINT);
                            System.out.println(component.getName() + " Deleted! : URL: " + component.getUrl());
                        }

                    }

                }
            }
        }
    }
}
