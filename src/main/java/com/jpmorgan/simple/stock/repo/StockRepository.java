package com.jpmorgan.simple.stock.repo;

import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

import com.jpmorgan.simple.stock.model.Stock;
import com.jpmorgan.simple.stock.model.Trade;

/**
 *
 */
public interface StockRepository {
	
	/**
	 * @return
	 */
	public HashMap<String, Stock> getAllStocks();
	
	/**
	 * @param stockSybmol
	 * @return
	 * @throws Exception
	 */
	public Stock getStock(String stockSybmol) throws Exception;
	
	public TreeMap<Date, Trade> getTrades() ;

	public void setTrades(TreeMap<Date, Trade> trades);
}
