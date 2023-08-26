package com.db.tradestorage.DTO;

import com.db.tradestorage.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepositoryDTO extends JpaRepository<Trade,String> {

}
