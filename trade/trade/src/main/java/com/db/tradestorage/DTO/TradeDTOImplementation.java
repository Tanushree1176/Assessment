package com.db.tradestorage.DTO;

import com.db.tradestorage.model.Trade;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class TradeDTOImplementation implements TradeDTO {
    @Override
    public void saveTrade(Trade trade) {
        trade.setCreatedDate(LocalDate.now());
        tradeMap.put(trade.getTradeId(),trade);
    }

    @Override
    public List<Trade> findAllTrade() {
         return tradeMap.values().stream().
                 collect(Collectors.toList());
    }

    @Override
    public Trade findTradeById(String tradeId) {
        return tradeMap.get(tradeId);
    }
}
