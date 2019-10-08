package com.Hardwar.Persistence.Service;

import com.Hardwar.Persistence.Entitys.CentralProcessingUnit;
import com.Hardwar.Persistence.Repository.CentralProcessingUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CentralProcessingUnitService {

    @Autowired
    CentralProcessingUnitRepository repository;

    public List<CentralProcessingUnit> getAllCPUs() {
        return repository.findAll();
    }

    public List<CentralProcessingUnit> getAllCPUsByDomainName(String domainName) {
        return repository.findAllByDomain(domainName);
    }

    public List<CentralProcessingUnit> saveAll(List<CentralProcessingUnit> cpuList) {
        List<CentralProcessingUnit> allCPUs = getAllCPUs();

        for (int i = 0; i < cpuList.size(); i++) {
            CentralProcessingUnit targetCPU = cpuList.get(i);
            for (CentralProcessingUnit persistedCPU : allCPUs) {
                if (targetCPU.getUrl() != null && persistedCPU.getUrl() != null) {
                    if (targetCPU.getUrl().equals(persistedCPU.getUrl())) {
                        targetCPU.setId(persistedCPU.getId());
                    }
                    if (targetCPU.getArticleNumber().equals(persistedCPU.getArticleNumber())) {
                        if (targetCPU.getCoreClock() == null && persistedCPU.getCoreClock() != null) {
                            targetCPU.setCoreClock(persistedCPU.getCoreClock());
                        }
                        if (targetCPU.getBoostClock() == null && persistedCPU.getBoostClock() != null) {
                            targetCPU.setBoostClock(persistedCPU.getBoostClock());
                        }

                    }
                }
            }
            repository.save(targetCPU);
        }
        return cpuList;
    }
}
