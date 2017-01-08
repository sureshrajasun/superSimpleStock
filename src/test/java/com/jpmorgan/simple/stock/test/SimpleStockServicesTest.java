package com.jpmorgan.simple.stock.test;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jpmorgan.simple.stock.constant.StockType;
import com.jpmorgan.simple.stock.constant.TradeIndicator;
import com.jpmorgan.simple.stock.model.Stock;
import com.jpmorgan.simple.stock.model.Trade;
import com.jpmorgan.simple.stock.repo.StockRepository;
import com.jpmorgan.simple.stock.service.SimpleStockService;

/**
 * 
 * Junit Test cases for Super Simple Stock
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SimpleStockAppConfiguration.class })
public class SimpleStockServicesTest {

	public static final String POP_SYMBOL = "POP";
	public static final String GIN_SYMBOL = "GIN";
	
	Logger log = Logger.getLogger(SimpleStockServicesTest.class);

	@Autowired
	SimpleStockService simpleStockService;
	
	@Autowired
	StockRepository stockRepository;
	
	@Test
	public void testAllShareIndex() throws Exception {
        HashMap<String, Stock> db = stockRepository.getAllStocks();
        Double GBCEallShareIndex = 0.0d;
        
        for (Stock stock: db.values()) {
            // Record 10 trades
        	for (int i=1; i <= 10; i++) {
				simpleStockService.buyStock(stock, 3, 2.0);
				simpleStockService.sellStock(stock, 3, 2.5);
			}	
        }
        
		GBCEallShareIndex = simpleStockService.calculateShareIndex(db);
        assertEquals(1.6572270086699934, GBCEallShareIndex, 0.0);
        log.info("testAllShareIndex test Success!!");
	}

    // Test dividend for Common method
	@Test
	public void testDividend() throws Exception {
		
        Stock stockPOP = new Stock(POP_SYMBOL, StockType.COMMON, 8.0, 0.0, 100.0);
        Stock stockGIN = new Stock(GIN_SYMBOL, StockType.PREFERRED, 8.0, 0.02, 100.0);
        
		Double dividendPOP 			= 0.0;
		Double expectedDividendALE 	= 0.0;
		Double dividendGIN 			= 0.0;
		
		dividendPOP = simpleStockService.calculateDividend(stockPOP,1.0);
		expectedDividendALE = stockPOP.getLastDividend()/1.0;
		
		assertEquals(expectedDividendALE, dividendPOP, 0.0);
		
		// Test dividend for Preferred method
		dividendGIN = simpleStockService.calculateDividend(stockGIN, 1.0);
		Double expectedDividendGIN = stockGIN.getFixedDividend() * stockGIN.getParValue() / 1.0;
		
		assertEquals(expectedDividendGIN, dividendGIN, 0.0);
		log.info("testDividend test Success!!");
	}

	@Test
	public void testPERatio() throws Exception{
		
        Stock stockJOE = new Stock("JOE", StockType.COMMON, 13.0, 0.0, 250.0);
        Double peRatioJOE = 0.0d;
        Double expectedPeRatioALE = 0.0;
        
		peRatioJOE = simpleStockService.calculatePERatio(stockJOE, 1.0);
		expectedPeRatioALE = 1.0 / stockJOE.getLastDividend();
		
        assertEquals(expectedPeRatioALE, peRatioJOE, 0.0);
        log.info("testPERatio test Success!!");
	}

	@Test
	public void testStockBuying() throws Exception{
		Stock stockPOP = new Stock(POP_SYMBOL, StockType.COMMON, 8.0, 0.0, 100.0);
		simpleStockService.buyStock(stockPOP, 1, 10.0);
		
		assertEquals(10.0, simpleStockService.getLastTradedPrice(stockPOP), 0.0);
		log.info("testStockBuying test Success!!");
	}

	@Test
	public void testStockSelling() throws Exception {
		Stock stockPOP = new Stock(POP_SYMBOL, StockType.COMMON, 8.0, 0.0, 100.0);
		simpleStockService.sellStock(stockPOP, 1, 10.0);
		
		assertEquals(10.0, simpleStockService.getLastTradedPrice(stockPOP), 0.0);
		log.info("testStockSelling test Success!!");   
	}

	@Test
	public void testGetStocckPrice()throws Exception {
		Stock stockTEA = new Stock("TEA", StockType.COMMON, 0.0, 0.0, 100.0);
		simpleStockService.sellStock(stockTEA, 1, 10.0);
		simpleStockService.buyStock(stockTEA, 1, 11.0);
			
		assertEquals(11.0, simpleStockService.getLastTradedPrice(stockTEA), 0.0);
		log.info("testGetStocckPrice test Success!!");
	}

	@Test
	public void testCalculateVolumeWeightedStockPrice() throws Exception {
		
		Stock stockALE = new Stock("ALE", StockType.COMMON, 23.0, 0.0, 60.0);
		Double volumeWeightedStockPrice = 0.0d;
		int pastMinutesToCal = 15;
		Date startTime = new Date(new Date().getTime() - (16 * 60 * 1000)); // to get the past 15 mins trade
		
		simpleStockService.sellStock(stockALE,2, 11.5);
		simpleStockService.buyStock(stockALE,2, 11.5);
		volumeWeightedStockPrice = simpleStockService.calculateVolumeWeightedStockPrice(stockALE, pastMinutesToCal);
		
		assertEquals(11.5, volumeWeightedStockPrice, 0.0);
		
		stockRepository.getTrades().put(startTime, new Trade(TradeIndicator.BUY, 1, 20.0));
		volumeWeightedStockPrice = simpleStockService.calculateVolumeWeightedStockPrice(stockALE, pastMinutesToCal);
		
		assertEquals(11.5, volumeWeightedStockPrice, 0.0);
		
		log.info("testCalculateVolumeWeightedStockPrice test Success!!");
	}
	
}
