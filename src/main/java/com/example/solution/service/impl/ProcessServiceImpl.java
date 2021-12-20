package com.example.solution.service.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.solution.exception.CustomFileNotFoundException;
import com.example.solution.mapper.StoreOrderRowMapper;
import com.example.solution.model.StoreOrder;
import com.example.solution.service.ProcessService;
import com.example.solution.util.DateUtil;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Service
@Transactional
public class ProcessServiceImpl implements ProcessService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessServiceImpl.class);

	@Value("${batch.size}")
	private Integer batchSize;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void readCsv(String fileName) throws Exception {
		List<StoreOrder> orders = readFile(fileName);
		insertData(orders);
	}

	private void insertData(List<StoreOrder> orders) {
		try {
			jdbcTemplate.batchUpdate(
					"INSERT INTO STORE_ORDER(ORDER_ID,ORDER_DATE,SHIP_DATE,SHIP_MODE,QUANTITY,DISCOUNT,PROFIT,PRODUCT_ID,CUSTOMER_NAME,CATEGORY,CUSTOMER_ID,PRODUCT_NAME) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
					orders, batchSize, new ParameterizedPreparedStatementSetter<StoreOrder>() {
						public void setValues(PreparedStatement ps, StoreOrder argument) throws SQLException {
							ps.setString(1, argument.getOrderId());
							ps.setDate(2, (Date) argument.getOrderDate());
							ps.setDate(3, (Date) argument.getShipDate());
							ps.setString(4, argument.getShipMode());
							ps.setInt(5, argument.getQuantity());
							ps.setBigDecimal(6, argument.getDiscount());
							ps.setBigDecimal(7, argument.getProfit());
							ps.setString(8, argument.getProductId());
							ps.setString(9, argument.getCustomerName());
							ps.setString(10, argument.getCategory());
							ps.setString(11, argument.getCustomerId());
							ps.setString(12, argument.getProductName());
						}
					});
		} catch (DuplicateKeyException a) {
			throw new DuplicateKeyException("Duplicate entry", a.getCause());
		}
	}

	public List<StoreOrder> readFile(String fileName) throws Exception {
		CSVReader csvReader = null;
		List<StoreOrder> orders = new ArrayList<StoreOrder>();
		try {
			FileReader filereader = new FileReader(fileName);

			csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();
			String[] nextRecord;

			while ((nextRecord = csvReader.readNext()) != null) {
				String[] data = nextRecord;
				StoreOrder order = new StoreOrder();
				String orderId = data[1];
				String orderDateStr = data[2];
				String shipDateStr = data[3];
				String shipMode = data[4];
				String customerId = data[5];
				String customerName = data[6];
				String productId = data[13];
				String category = data[14];
				String productName = data[16];
				Integer quantity = Integer.valueOf(data[18]);
				BigDecimal discount = new BigDecimal(data[19]);
				BigDecimal profit = new BigDecimal(data[20]);

				order.setOrderId(orderId);
				order.setOrderDate(DateUtil.convertStringToUtilDate(orderDateStr));
				order.setShipDate(DateUtil.convertStringToUtilDate(shipDateStr));
				order.setShipMode(shipMode);
				order.setCustomerId(customerId);
				order.setCustomerName(customerName);
				order.setProductId(productId);
				order.setCategory(category);
				order.setProductName(productName);
				order.setQuantity(quantity);
				order.setDiscount(discount);
				order.setProfit(profit);

				orders.add(order);
			}
		} catch (FileNotFoundException e) {
			throw new CustomFileNotFoundException("File not found", e);
		} catch (IOException e) {
			throw new IOException("There is a problem while reading csv file!", e);
		} finally {
			try {
				if (csvReader != null)
					csvReader.close();
			} catch (IOException e) {
				throw new RuntimeException("There is a problem while close reader!", e);
			}
		}
		return orders;
	}

	@Override
	public StoreOrder findByOrderId(String orderId) {
		String sql = "SELECT * FROM STORE_ORDER WHERE ORDER_ID = ?";
		return jdbcTemplate.queryForObject(sql, new Object[]{orderId}, new StoreOrderRowMapper());
	}

}
