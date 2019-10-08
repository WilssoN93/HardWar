package com.Hardwar.Persistence.Repository;

import com.Hardwar.Persistence.Entitys.CentralProcessingUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CentralProcessingUnitRepository extends JpaRepository<CentralProcessingUnit,Long > {
    List<CentralProcessingUnit> findAllByDomain(String domainName);
}
