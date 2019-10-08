package com.Hardwar.Persistence.Resource;

import com.Hardwar.Persistence.Entitys.GraphicsCard;
import com.Hardwar.Persistence.Service.GraphicsCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/graphicscard/")
public class GraphicsCardResource {

    @Autowired
    GraphicsCardService service;

    @GetMapping
    public @ResponseBody
    List<GraphicsCard> getAllGraphicsCards(){
        return service.getAllGraphicsCards();
    }

    @GetMapping("{domainName}")
    public @ResponseBody
    List<GraphicsCard> getAllGraphicsCardsByDomainName(@PathVariable("domainName") String domainName){
       return service.getAllGraphicsCardsByDomainName(domainName);
    }

    @PutMapping
    public List<GraphicsCard> saveAllGraphicsCards(@RequestBody List<GraphicsCard> graphicsCardList){
        return service.saveAll(graphicsCardList);
    }
}
