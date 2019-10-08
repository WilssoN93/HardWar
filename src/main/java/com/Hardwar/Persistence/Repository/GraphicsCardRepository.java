package com.Hardwar.Persistence.Repository;

import com.Hardwar.Persistence.Entitys.GraphicsCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GraphicsCardRepository extends JpaRepository<GraphicsCard,Long> {

    List<GraphicsCard> findAllByDomainName(String domainName);
}
