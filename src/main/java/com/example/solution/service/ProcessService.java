package com.example.solution.service;

import java.util.List;

import com.example.solution.model.StoreOrder;

public interface ProcessService {
	
	public void readCsv(String fileName) throws Exception;
	public StoreOrder findByOrderId(String orderId);
	public List<StoreOrder> readFile(String fileName) throws Exception;
}
