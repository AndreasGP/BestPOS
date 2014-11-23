package ee.ut.math.tvt.vapradjailusad;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.vapradjailusad.domain.data.Order;
import ee.ut.math.tvt.vapradjailusad.domain.data.SoldItem;
import ee.ut.math.tvt.vapradjailusad.domain.data.StockItem;

public class OrderTest {

	private static double cost1 = 50;
	private static double cost2 = 150;
	private static double totalSum = 300;

	StockItem stockItem1;
	StockItem stockItem2;
	SoldItem soldItem1;
	SoldItem soldItem2;

	List<SoldItem> soldItems;
	List<SoldItem> soldItems1item;


	@Before
	public void setUp() {
		
		stockItem1 = new StockItem((long) 1, "Vesi", "", cost1);
		stockItem2 = new StockItem((long) 2, "Viin", "", cost2);
		soldItem1 = new SoldItem(stockItem1, 3);
		soldItem2 = new SoldItem(stockItem2, 1);
		soldItems = new ArrayList<SoldItem>();
		soldItems1item = new ArrayList<SoldItem>();
	}
	
	@Test
	public void testAddSoldItem() {
		
		Order order = new Order("12/12/2014", "12:34:56", soldItems);
		soldItems.add(soldItem1);
		soldItems.add(soldItem2);
		order.setSoldItems(soldItems);
		assertEquals(order.getPrice(), totalSum, 0.0001);
	}

	@Test
	public void testGetSumWithNoItems() {
		
		Order order = new Order("12/12/2014", "12:34:56", new ArrayList<SoldItem>());
		assertEquals(order.getPrice(), 0.0, 0.0);
	}

	@Test
	public void testGetSumWithOneItem() {

		soldItems1item.add(soldItem2);
		Order order = new Order("12/12/2014", "12:34:56", soldItems1item);		
		assertEquals(order.getPrice(), cost2, 0.0);
	}

	@Test
	public void testGetSumWithMultipleItems() {

		soldItems.add(soldItem1);
		soldItems.add(soldItem2);
		Order order = new Order("12/12/2014", "12:34:56", soldItems);
		assertEquals(order.getPrice(), totalSum, 0.0001);
	}
}
