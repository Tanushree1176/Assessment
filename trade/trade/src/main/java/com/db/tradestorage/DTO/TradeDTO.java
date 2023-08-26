package com.db.tradestorage.DTO;

import com.db.tradestorage.model.Trade;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface TradeDTO {


    public  static Map<String,Trade> tradeMap =new ConcurrentHashMap<>();

    public void saveTrade(Trade trade);

    List<Trade> findAllTrade();

    Trade findTradeById(String tradeId);
}
