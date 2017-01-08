package com.jpmorgan.simple.stock.model;

import java.io.Serializable;

import com.jpmorgan.simple.stock.constant.TradeIndicator;

public class Trade implements Serializable {
	
	private static final long serialVersionUID = 6423373937097177270L;
	
	private TradeIndicator type;
	private Integer quantity;
	private Double price;

	/**
	 * @return
	 */
	public TradeIndicator getType() {
		return type;
	}

	/**
	 * @param type
	 */
	public void setType(TradeIndicator type) {
		this.type = type;
	}

	/**
	 * @return
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 * @param type
	 * @param quantity
	 * @param price
	 */
	public Trade(TradeIndicator type, Integer quantity, Double price) {
		this.setType(type);
		this.setQuantity(quantity);
		this.setPrice(price);
	}

	@Override
	public String toString() {
		String pattern = "Trade Object [ type: %s, quantity: %7d, price: %8.2f]";
		return String.format(pattern, type,quantity,  price);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trade other = (Trade) obj;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	
	
	
	
}
