package com.Hardwar.Persistence.Service;

import com.Hardwar.Persistence.Entitys.GraphicsCard;
import com.Hardwar.Persistence.Repository.GraphicsCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GraphicsCardService {

    @Autowired
    GraphicsCardRepository repository;

    public List<GraphicsCard> getAllGraphicsCardsByDomainName(String domainName) {
        return repository.findAllByDomainName(domainName);
    }

    public List<GraphicsCard> getAllGraphicsCards() {
        return repository.findAll();
    }

    public List<GraphicsCard> saveAll(List<GraphicsCard> graphicsCardsList) {
        List<GraphicsCard> allGraphicsCards = repository.findAll();
        for (int i = 0; i < graphicsCardsList.size(); i++) {
            GraphicsCard targetGpu = graphicsCardsList.get(i);
            for (GraphicsCard graphicsCard : allGraphicsCards) {
                if (targetGpu.getUrl().equals(graphicsCard.getUrl())){
                    targetGpu.setId(graphicsCard.getId());
                }
                if (targetGpu.getArticleNumber().equals(graphicsCard.getArticleNumber())) {
                    if (targetGpu.getCoreClock() == null && graphicsCard.getCoreClock() != null) {
                        targetGpu.setCoreClock(graphicsCard.getCoreClock());
                        System.out.println(targetGpu.getName()+ " CoreClock");
                    }
                    if (targetGpu.getBoostClock() == null && graphicsCard.getBoostClock() != null) {
                        System.out.println(targetGpu.getName()+ " BoostClock");
                        targetGpu.setBoostClock(graphicsCard.getBoostClock());
                    }
                    if (targetGpu.getCudaCores() == null && graphicsCard.getCudaCores() != null) {
                        targetGpu.setCudaCores(graphicsCard.getCudaCores());
                        System.out.println(targetGpu.getName() + "cuda cores" );
                    }
                }

            }
            repository.save(targetGpu);
        }

        return graphicsCardsList;
    }

}
