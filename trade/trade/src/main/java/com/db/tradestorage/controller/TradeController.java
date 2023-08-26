package com.db.tradestorage.controller;

import com.db.tradestorage.exception.InvalidTradeFoundException;
import com.db.tradestorage.model.Trade;
import com.db.tradestorage.service.TradeAssesmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TradeController {
    @Autowired
    TradeAssesmentService tradeService;

    @PostMapping("/insert/trade")
    public ResponseEntity<String> checkTradeValidStore(@RequestBody Trade trade){
       if(tradeService.isValidTrade(trade)) {
           tradeService.doesTradepersist(trade);
       }else{
           throw new InvalidTradeFoundException(trade.getTradeId()+"  no such trade found");
       }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("get/trade")
    public List<Trade> findAllTrades(){
        return tradeService.findAll();
    }
}
