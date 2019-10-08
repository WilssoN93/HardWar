package com.Hardwar.Persistence.Resource;

import com.Hardwar.Persistence.Entitys.CentralProcessingUnit;
import com.Hardwar.Persistence.Service.CentralProcessingUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/cpu")
public class CentralProcessingUnitResource {

    @Autowired
    CentralProcessingUnitService service;

    @GetMapping
    public @ResponseBody List<CentralProcessingUnit> getAll(){
        return service.getAllCPUs();
    }

    @GetMapping(path = "{domainName}")
    public List<CentralProcessingUnit> getAllByDomainName(@PathVariable("domainName")String domainName){
        return service.getAllCPUsByDomainName(domainName);
    }
    @PutMapping
    public List<CentralProcessingUnit> updateCPUs(@RequestBody List<CentralProcessingUnit> centralProcessingUnits){
        return service.saveAll(centralProcessingUnits);
    }

}
