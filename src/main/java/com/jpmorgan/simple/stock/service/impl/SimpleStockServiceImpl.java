package com.jpmorgan.simple.stock.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpmorgan.simple.stock.constant.TradeIndicator;
import com.jpmorgan.simple.stock.model.Stock;
import com.jpmorgan.simple.stock.model.Trade;
import com.jpmorgan.simple.stock.repo.StockRepository;
import com.jpmorgan.simple.stock.service.SimpleStockService;

/**
 * 
 * This class contains all the operation on the stocks given in the requirements
 *
 */

@Service
public class SimpleStockServiceImpl implements SimpleStockService {
	
	private Logger log = Logger.getLogger(SimpleStockServiceImpl.class);
	
	
	@Autowired
	private StockRepository repository;

	/*
	 * This method will return the Calculated Share index
	 */
	public Double calculateShareIndex(Map<String, Stock> stocks) throws Exception {
		Double allShareIndex = 0.0;

		for(Stock stock: stocks.values()) {
			allShareIndex += getLastTradedPrice(stock);
		}
		return Math.pow(allShareIndex, 1.0 / stocks.size());
	}
	
	
	/*
	 * This method is to calculate the Dividend of a Stock
	 */
	public Double calculateDividend(Stock stock, Double price) throws Exception {
		log.info("Calculating the Dividend for the stock : " + stock);
		switch(stock.getType()) {
			case COMMON:
				return stock.getLastDividend() / price;
			case PREFERRED:
				return stock.getFixedDividend() * stock.getParValue() / price;
			default:
				return 0.0;
		}
	}
	
	/*
	 * This method is used to calculate PER Ration
	 * 
	 */
	public Double calculatePERatio(Stock stock, Double price) throws Exception {
		log.info("Calculating the PERation for the stock : "+stock);
		return price / stock.getLastDividend();
	}

	/*
	 * This method will perform the operation of stock buying
	 * 
	 */
	public void buyStock(Stock stock, Integer quantity, Double price) throws Exception {
		
		Trade trade = new Trade(TradeIndicator.BUY, quantity, price);
		repository.getTrades().put(new Date(), trade);
		
		log.info(quantity+ " Stock of symbol \""+stock.getSymbol() + "\" has been bought with "+price +" price.");
	}

	/*
	 *  This method will perform the operation of stock selling
	 * 
	 */
	public void sellStock(Stock stock, Integer quantity, Double price) throws Exception {
		Trade trade = new Trade(TradeIndicator.SELL, quantity, price);
		repository.getTrades().put(new Date(), trade);
		
		log.info(quantity+ " Stock \""+stock.getSymbol() + "\" has been sold with "+price +" price.");
	}
	
	/*
	 * This method will return the price of the stock last sold price 
	 * 
	 */
	public Double getStockPrice(Stock stock) throws Exception {
		if (!repository.getTrades().isEmpty()) {
			// Uses the last traded price as price
			return repository.getTrades().lastEntry().getValue().getPrice();
		} else {
			return 0.0;
		}
	}
	
	/*
	 * This method will perform the operation of calculating Volume weighted Stock price
	 * 
	 */
	public Double calculateVolumeWeightedStockPrice(Stock stock, int pastMinutesToCal) throws Exception  {
		Double volumeWeightedStockPrice = 0.0;
		Integer totalQuantity = 0;
		Date now = new Date();
		// Time 15 minutes ago
		Date startTime = new Date(now.getTime() - (pastMinutesToCal * 60 * 1000));
		// Get trades for the last 15 minutes
		SortedMap<Date, Trade> trades = repository.getTrades().tailMap(startTime);

		for (Trade trade: trades.values()) {
			totalQuantity += trade.getQuantity();
			volumeWeightedStockPrice += trade.getPrice() * trade.getQuantity();
		}
		return volumeWeightedStockPrice / totalQuantity;
	}
	
	/**
	 * @return
	 */
	public Double getLastTradedPrice(Stock stock) {
		if (!repository.getTrades().isEmpty()) {
			// Uses the last trade price as price
			return repository.getTrades().lastEntry().getValue().getPrice();
		} else {
			return 0.0;
		}
	}

}
