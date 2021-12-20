package com.example.solution;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.solution.model.StoreOrder;
import com.example.solution.service.ProcessService;
import com.opencsv.CSVWriter;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
class SolutionApplicationTests {

	@Autowired
	private ProcessService processService;

	private static final String TEST_FILE = "test-file.csv";

	@Test
	@Order(1)
	public void testNormalData() throws Exception {
		try (Writer writer = Files.newBufferedWriter(Paths.get(TEST_FILE));

				CSVWriter csvWriter = new CSVWriter(writer);) {
			String[] headerRecord = { "Row ID", "Order ID", "Order Date", "Ship Date", "Ship Mode", "Customer ID",
					"Customer Name", "Segment", "Country", "City", "State", "Postal Code", "Region", "Product ID", "Category",
					"Sub-Category", "Product Name", "Sales", "Quantity", "Discount", "Profit" };
			csvWriter.writeNext(headerRecord);

			csvWriter.writeNext(new String[] { "1", "CA-2016-121212", "08.11.16", "11.11.16", "Second Class", "CG-12520",
					"Claire Gute", "Consumer", "United States", "Henderson", "Kentucky", "42420", "South", "FUR-BO-10001798",
					"Furniture", "Bookcases", "Bush Somerset Collection Bookcase", "261.96", "2", "0", "41.9136" });
		}

		List<StoreOrder> result = processService.readFile(TEST_FILE);
		Assertions.assertEquals(1, result.size());
	}

	@Test
	@Order(2)
	public void testDoubleQuoteInColumn() throws Exception {
		try (Writer writer = Files.newBufferedWriter(Paths.get(TEST_FILE));

				CSVWriter csvWriter = new CSVWriter(writer);) {
			String[] headerRecord = { "Row ID", "Order ID", "Order Date", "Ship Date", "Ship Mode", "Customer ID",
					"Customer Name", "Segment", "Country", "City", "State", "Postal Code", "Region", "Product ID", "Category",
					"Sub-Category", "Product Name", "Sales", "Quantity", "Discount", "Profit" };
			csvWriter.writeNext(headerRecord);

			csvWriter.writeNext(new String[] { "1", "CA-2016-232323", "08.11.16", "11.11.16", "Second \"Class", "CG-12520",
					"Claire Gute", "Consumer", "United States", "Henderson", "Kentucky", "42420", "South", "FUR-BO-10001798",
					"Furniture", "Bookcases", "Bush Somerset Collection Bookcase", "261.96", "2", "0", "41.9136" });
		}

		List<StoreOrder> result = processService.readFile(TEST_FILE);
		Assertions.assertEquals("Second \"Class", result.get(0).getShipMode());
	}

	@Test
	@Order(3)
	public void testContainsSingleQuoteInColumn() throws Exception {
		try (Writer writer = Files.newBufferedWriter(Paths.get(TEST_FILE));

				CSVWriter csvWriter = new CSVWriter(writer);) {
			String[] headerRecord = { "Row ID", "Order ID", "Order Date", "Ship Date", "Ship Mode", "Customer ID",
					"Customer Name", "Segment", "Country", "City", "State", "Postal Code", "Region", "Product ID", "Category",
					"Sub-Category", "Product Name", "Sales", "Quantity", "Discount", "Profit" };
			csvWriter.writeNext(headerRecord);

			csvWriter.writeNext(new String[] { "1", "CA-2016-334433", "08.11.16", "11.11.16", "Second Class", "CG-12520",
					"Sean O'Donnell", "Consumer", "United States", "Henderson", "Kentucky", "42420", "South", "FUR-BO-10001798",
					"Furniture", "Bookcases", "Bretford CR4500 Series Slim Rectangular Table", "261.96", "2", "0", "41.9136" });
		}

		List<StoreOrder> result = processService.readFile(TEST_FILE);
		Assertions.assertEquals("Sean O'Donnell", result.get(0).getCustomerName());
	}

	@Test
	@Order(4)
	public void testContainsEnterInColumn() throws Exception {
		try (Writer writer = Files.newBufferedWriter(Paths.get(TEST_FILE));
				CSVWriter csvWriter = new CSVWriter(writer);) {
			String[] headerRecord = { "Row ID", "Order ID", "Order Date", "Ship Date", "Ship Mode", "Customer ID",
					"Customer Name", "Segment", "Country", "City", "State", "Postal Code", "Region", "Product ID", "Category",
					"Sub-Category", "Product Name", "Sales", "Quantity", "Discount", "Profit" };
			csvWriter.writeNext(headerRecord);

			csvWriter.writeNext(new String[] { "1", "CA-2016-343434", "08.11.16", "11.11.16", "Second Class", "CG-12520",
					"Claire Gute", "Consumer", "United States", "Henderson", "Kentucky", "42420", "South", "FUR-BO-10001798",
					"Furniture", "Bookcases", "Eldon Expressions Wood\n" + "and Plastic Desk, Accessories", "261.96", "2", "0",
					"41.9136" });
		}

		List<StoreOrder> result = processService.readFile(TEST_FILE);
		Assertions.assertEquals("Eldon Expressions Wood\nand Plastic Desk, Accessories", result.get(0).getProductName());
	}

	@Test
	@Order(5)
	public void testContainsCommaInColumn() throws Exception {
		try (Writer writer = Files.newBufferedWriter(Paths.get(TEST_FILE));
				CSVWriter csvWriter = new CSVWriter(writer);) {
			String[] headerRecord = { "Row ID", "Order ID", "Order Date", "Ship Date", "Ship Mode", "Customer ID",
					"Customer Name", "Segment", "Country", "City", "State", "Postal Code", "Region", "Product ID", "Category",
					"Sub-Category", "Product Name", "Sales", "Quantity", "Discount", "Profit" };
			csvWriter.writeNext(headerRecord);

			csvWriter.writeNext(new String[] { "1", "CA-2016-000000", "08.11.16", "11.11.16", "Second Class", "CG-12520",
					"Claire Gute", "Consumer", "United States", "Henderson", "Kentucky", "42420", "South", "FUR-BO-10001798",
					"Furniture", "Bookcases", "Eldon Expressions Wood, Accessories", "261.96", "2", "0", "41.9136" });
		}

		List<StoreOrder> result = processService.readFile(TEST_FILE);
		Assertions.assertEquals("Eldon Expressions Wood, Accessories", result.get(0).getProductName());
	}
	
	@Test
	@Order(6)
	public void testContainsEmptyStringInColumn() throws Exception {
		try (Writer writer = Files.newBufferedWriter(Paths.get(TEST_FILE));
				CSVWriter csvWriter = new CSVWriter(writer);) {
			String[] headerRecord = { "Row ID", "Order ID", "Order Date", "Ship Date", "Ship Mode", "Customer ID",
					"Customer Name", "Segment", "Country", "City", "State", "Postal Code", "Region", "Product ID", "Category",
					"Sub-Category", "Product Name", "Sales", "Quantity", "Discount", "Profit" };
			csvWriter.writeNext(headerRecord);

			csvWriter.writeNext(new String[] { "1", "CA-2016-888888", "08.11.16", "11.11.16", "Second Class", "CG-12520",
					"Claire Gute", "Consumer", "United States", "Henderson", "Kentucky", "42420", "South", "",
					"Furniture", "Bookcases", "Eldon Expressions Wood, Accessories", "261.96", "2", "0", "41.9136" });
		}

		List<StoreOrder> result = processService.readFile(TEST_FILE);
		Assertions.assertEquals("Eldon Expressions Wood, Accessories", result.get(0).getProductName());
	}

}
