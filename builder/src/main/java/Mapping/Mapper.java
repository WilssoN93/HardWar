package Mapping;

import com.example.purrsistence.Entity.Product;
import entity.*;

import java.util.List;

public class Mapper {

    public ComputerComponent mapProducts(List<Product> productList) {

        for (Product product : productList) {
            if (product.getTypeOfHardWare().equals("Graphics Card")) {
                GraphicsCard gpu = new GraphicsCard();
                gpu.setPrice(Integer.parseInt(product.getPrice()));
                gpu.setName(product.getName());
                gpu.setArticleNumber(product.getArticleNumber());
                gpu.setCudaCores(Integer.parseInt(product.getSpecification().get("Cuda Cores")));
                gpu.setClockSpeed(product.getSpecification().get("Clock Speed"));
                gpu.setTurboClock(product.getSpecification().get("Boost Clock"));
                gpu.setHeight(product.getSpecification().get("Height"));
                gpu.setWidth(product.getSpecification().get("Width"));
                gpu.setvRam(product.getSpecification().get("vRam"));
                return gpu;
            } else if (product.getTypeOfHardWare().equals("Power Supply")) {
                PowerSupplyUnit psu = new PowerSupplyUnit();
            } else if (product.getTypeOfHardWare().equals("Fan")) {
                Fan fan = new Fan();
            } else if (product.getTypeOfHardWare().equals("Motherboard")) {
                MotherBoard motherBoard = new MotherBoard();
            } else if (product.getTypeOfHardWare().equals("Drive")) {
                Drive drive = new Drive();
            } else if (product.getTypeOfHardWare().equals("RAM")) {
                RandomAccessMemory ram = new RandomAccessMemory();
            } else if (product.getTypeOfHardWare().equals("Processor")) {
                CentralProcessingUnit cpu = new CentralProcessingUnit();
            }


        }
        return null;
    }
}
