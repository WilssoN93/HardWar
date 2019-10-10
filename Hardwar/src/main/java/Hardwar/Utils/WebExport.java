package Hardwar.Utils;

import com.Hardwar.Persistence.Entitys.*;

import java.util.List;

public interface WebExport {

    List<Product> scan();

    GraphicsCard parseGraphicsCard(Product product);

    CentralProcessingUnit parseCPU(Product product);
    MotherBoard parseMotherBoard(Product product);
    RandomAcessMemory parseRAM(Product product);
    Product parseType(Product product);


    String getDomainName();
}
