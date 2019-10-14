package Hardwar.Utils;

import com.Hardwar.Persistence.Entitys.*;

import java.util.List;

public interface WebExport {

    List<Product> scan();

    GraphicsCard parseGraphicsCard(Product product);

    CentralProcessingUnit parseCPU(Product product);
    MotherBoard parseMotherBoard(Product product);
    RandomAccessMemory parseRAM(Product product);
    Chassi parseChassi(Product product);
    Storage parseStorage(Product product);
    PowerSupplyUnit parsePSU(Product product);
    Product parseType(Product product);


    String getDomainName();
}
