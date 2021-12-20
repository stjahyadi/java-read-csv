package com.example.solution.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.solution.model.StoreOrder;

public class StoreOrderRowMapper implements RowMapper<StoreOrder> {

	@Override
	public StoreOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
		StoreOrder order = new StoreOrder();
		order.setId(rs.getInt("ID"));
		order.setOrderId(rs.getString("ORDER_ID"));
		order.setOrderDate(rs.getDate("ORDER_DATE"));
		order.setShipDate(rs.getDate("SHIP_DATE"));
		order.setShipMode(rs.getString("SHIP_MODE"));
		order.setQuantity(rs.getInt("QUANTITY"));
		order.setDiscount(rs.getBigDecimal("DISCOUNT"));
		order.setProfit(rs.getBigDecimal("PROFIT"));
		order.setProductId(rs.getString("PRODUCT_ID"));
		order.setCustomerName(rs.getString("CUSTOMER_NAME"));
		order.setCategory(rs.getString("CATEGORY"));
		order.setCustomerId(rs.getString("CUSTOMER_ID"));
		order.setProductName(rs.getString("PRODUCT_NAME"));
		return order;
	}

}
