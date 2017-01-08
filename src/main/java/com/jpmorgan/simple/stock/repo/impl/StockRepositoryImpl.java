package com.jpmorgan.simple.stock.repo.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.jpmorgan.simple.stock.constant.StockType;
import com.jpmorgan.simple.stock.model.Stock;
import com.jpmorgan.simple.stock.model.Trade;
import com.jpmorgan.simple.stock.repo.StockRepository;
import com.jpmorgan.simple.stock.service.impl.SimpleStockServiceImpl;

/**
 * Stock Repository, to get the stock from the back end, implementation can be changed as per the back end usage.
 * 
 */
public class StockRepositoryImpl implements StockRepository {
	
	 
	 private Logger log = Logger.getLogger(SimpleStockServiceImpl.class);
	 
	 private HashMap<String, Stock> testStockList = new HashMap<>();
	 private TreeMap<Date, Trade> trades =  new TreeMap<>();
	 
     /*
     * 
     * This method will return all the stocks which is added in this system
     * This implementation can be changed to get the data from database or any storage
     * 
     */
    public HashMap<String, Stock> getAllStocks(){
    	 
    	 log.info("Selecting all stocks");
    	 //testStockList = new HashMap<String, Stock>();
    	 
    	 testStockList.put("TEA", new Stock("TEA", StockType.COMMON, 	0.0, 	0.0, 100.0));
         testStockList.put("POP", new Stock("POP", StockType.COMMON, 	8.0, 	0.0, 100.0));
         testStockList.put("ALE", new Stock("ALE", StockType.COMMON, 	23.0, 	0.0, 60.0));
         testStockList.put("GIN", new Stock("GIN", StockType.PREFERRED, 8.0, 	0.02, 100.0));
         testStockList.put("JOE", new Stock("JOE", StockType.COMMON, 	13.0, 	0.0, 250.0));
         
         log.info("No. of stocks selected are : "+testStockList.size());
         
         return testStockList;
         
     }
     
     public Stock getStock(String stockSybmol) throws Exception{
    	 
    	 if(testStockList !=null && testStockList.size() > 0) {
    		 return testStockList.get(stockSybmol);
    	 }
    	 throw new Exception("No Stock is found with the given name: "+stockSybmol);
    	 
     }

	public TreeMap<Date, Trade> getTrades() {
		
		return trades;
	}

	public void setTrades(TreeMap<Date, Trade> trades) {
		this.trades = trades;
	}
     

}
