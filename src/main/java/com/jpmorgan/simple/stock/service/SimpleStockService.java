package com.jpmorgan.simple.stock.service;

import java.util.Map;

import com.jpmorgan.simple.stock.model.Stock;

/**
 * 
 *
 */
public interface SimpleStockService {
	
	/**
	 * @param stocks
	 * @return
	 * @throws Exception
	 */
	public Double calculateShareIndex(Map<String, Stock> stocks) throws Exception;
	/**
	 * @param stock
	 * @param price
	 * @return
	 * @throws Exception
	 */
	public Double calculateDividend(Stock stock, Double price) throws Exception; 
	
	/**
	 * @param stock
	 * @param quantity
	 * @param price
	 * @throws Exception
	 */
	public void buyStock(Stock stock, Integer quantity, Double price) throws Exception; 
	/**
	 * @param stock
	 * @param pastMinutesToCal
	 * @return
	 * @throws Exception
	 */
	public Double calculateVolumeWeightedStockPrice(Stock stock, int pastMinutesToCal) throws Exception ;
	/**
	 * @param stock
	 * @return
	 * @throws Exception
	 */
	public Double getStockPrice(Stock stock) throws Exception ;
	/**
	 * @param stock
	 * @param quantity
	 * @param price
	 * @throws Exception
	 */
	public void sellStock(Stock stock, Integer quantity, Double price) throws Exception;
	/**
	 * @param stock
	 * @param price
	 * @return
	 * @throws Exception
	 */
	public Double calculatePERatio(Stock stock, Double price)throws Exception;
	
	/**
	 * @param stock
	 * @return
	 */
	public Double getLastTradedPrice(Stock stock) ;
	
}
