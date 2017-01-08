package com.jpmorgan.simple.stock.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jpmorgan.simple.stock.repo.StockRepository;
import com.jpmorgan.simple.stock.repo.impl.StockRepositoryImpl;
import com.jpmorgan.simple.stock.service.SimpleStockService;
import com.jpmorgan.simple.stock.service.impl.SimpleStockServiceImpl;

@Configuration
public class SimpleStockAppConfiguration {
	
	@Bean
    public SimpleStockService simpleStockService() {
        return new SimpleStockServiceImpl();
    }
	
	@Bean
    public StockRepository stockRepository() {
        return new StockRepositoryImpl();
    }

}
