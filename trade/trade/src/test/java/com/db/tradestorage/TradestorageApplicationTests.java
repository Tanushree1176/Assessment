package com.db.tradestorage;

import com.db.tradestorage.controller.TradeController;
import com.db.tradestorage.exception.InvalidTradeFoundException;
import com.db.tradestorage.model.Trade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TradestorageApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private TradeController tradeController;

	@Test
	void tradeValidateAndStore_successful() {
		ResponseEntity response = tradeController.checkTradeValidStore(createTrade("trade1",1,LocalDate.now()));
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build(),response);
		List<Trade> tradeList =tradeController.findAllTrades();
		Assertions.assertEquals(1, tradeList.size());
		Assertions.assertEquals("trade1",tradeList.get(0).getTradeId());
	}

	@Test
	void testTradeValidityWhenMaturityExpired() {
		try {
			LocalDate localDate = getLocalDate(2015, 05, 21);
			ResponseEntity responseEntity = tradeController.checkTradeValidStore(createTrade("trade2", 1, localDate));
		}catch (InvalidTradeFoundException ie) {
			Assertions.assertEquals("Invalid trade is not", ie.getMessage());
		}
	}

	@Test
	void testTradeValidityWhenTradeIsPasses() {
		// step-1 create trade
		ResponseEntity responseEntity = tradeController.checkTradeValidStore(createTrade("trade3",2,LocalDate.now()));
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build(),responseEntity);
		List<Trade> tradeList =tradeController.findAllTrades();
		Assertions.assertEquals(1, tradeList.size());
		Assertions.assertEquals("trade1",tradeList.get(0).getTradeId());
		Assertions.assertEquals(2,tradeList.get(0).getVersion());
		Assertions.assertEquals("T1B1",tradeList.get(0).getBookId());

		//step-2 create trade with old version
		try {
			ResponseEntity responseEntity1 = tradeController.checkTradeValidStore(createTrade("trade1", 1, LocalDate.now()));


		}catch (InvalidTradeFoundException e){
			System.out.println(e.getId());
			System.out.println(e.getMessage());
		}
		List<Trade> tradeList1 =tradeController.findAllTrades();
		Assertions.assertEquals(1, tradeList1.size());
		Assertions.assertEquals("trade1",tradeList1.get(0).getTradeId());
		Assertions.assertEquals(2,tradeList1.get(0).getVersion());
		Assertions.assertEquals("T1B1",tradeList.get(0).getBookId());
	}

	@Test
	void testTradeValidateAndStoreWhenSameVersionTrade(){
		ResponseEntity responseEntity = tradeController.checkTradeValidStore(createTrade("trade1",2,LocalDate.now()));
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build(),responseEntity);
		List<Trade> tradeList =tradeController.findAllTrades();
		Assertions.assertEquals(1, tradeList.size());
		Assertions.assertEquals("trade1",tradeList.get(0).getTradeId());
		Assertions.assertEquals(2,tradeList.get(0).getVersion());
		Assertions.assertEquals("T1B1",tradeList.get(0).getBookId());

		//step-2 create trade with same version
		Trade trade2 = createTrade("trade1",2,LocalDate.now());
		trade2.setBookId("T1B1V2");
		ResponseEntity responseEntity2 = tradeController.checkTradeValidStore(trade2);
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build(),responseEntity2);
		List<Trade> tradeList2 =tradeController.findAllTrades();
		Assertions.assertEquals(1, tradeList2.size());
		Assertions.assertEquals("trade1",tradeList2.get(0).getTradeId());
		Assertions.assertEquals(2,tradeList2.get(0).getVersion());
		Assertions.assertEquals("T1B1V2",tradeList2.get(0).getBookId());

		//step-2 create trade with new version
		Trade trade3 = createTrade("trade1",2,LocalDate.now());
		trade3.setBookId("T1B1V3");
		ResponseEntity responseEntity3 = tradeController.checkTradeValidStore(trade3);
		Assertions.assertEquals(ResponseEntity.status(HttpStatus.OK).build(),responseEntity3);
		List<Trade> tradeList3 =tradeController.findAllTrades();
		Assertions.assertEquals(1, tradeList3.size());
		Assertions.assertEquals("trade1",tradeList3.get(0).getTradeId());
		Assertions.assertEquals(2,tradeList3.get(0).getVersion());
		Assertions.assertEquals("T1B1V3",tradeList3.get(0).getBookId());

	}
	private Trade createTrade(String tradeId,int version,LocalDate  maturityDate){
		Trade trade = new Trade();
		trade.setTradeId(tradeId);
		trade.setBookId(tradeId+"B1");
		trade.setVersion(version);
		trade.setCounterParty(tradeId+"Cpty");
		trade.setMaturityDate(maturityDate);
		trade.setExpiredFlag("Y");
		return trade;
	}

	public static LocalDate getLocalDate(int year,int month, int day){
		LocalDate localDate = LocalDate.of(year,month,day);
		return localDate;
	}




}
