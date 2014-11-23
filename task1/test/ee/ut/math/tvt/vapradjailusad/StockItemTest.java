package ee.ut.math.tvt.vapradjailusad;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.vapradjailusad.domain.data.SoldItem;
import ee.ut.math.tvt.vapradjailusad.domain.data.StockItem;

public class StockItemTest {
	
	private static double cost1 = 50;
	private static int kogus = 1;
	private static String kirjeldus = "jahe";
	private static String nimi = "Vesi";
	StockItem stockItem1;
	
	
	@Before
	public void setUp() {
		
		stockItem1 = new StockItem((long) 1, nimi, kirjeldus, cost1);
		stockItem1.setQuantity(kogus);

	}
	
	@Test
	public void testClone() {
		
		StockItem stockItemClone = (StockItem) stockItem1.clone();
		assertEquals(stockItem1.getDescription(), stockItemClone.getDescription());
		assertEquals(stockItem1.getId(), stockItemClone.getId());
		assertEquals(stockItem1.getName(), stockItemClone.getName());
		assertEquals(stockItem1.getQuantity(), stockItemClone.getQuantity());
		assertEquals(stockItem1.getPrice(), stockItemClone.getPrice(), 0.0001);
	}
	
	@Test
	public void testGetColumn() {
		
		assertEquals(stockItem1.getColumn(0), (Long)((long) 1));
		assertEquals(stockItem1.getColumn(1), nimi);
		assertEquals(stockItem1.getColumn(2), (Double) cost1);
		assertEquals(stockItem1.getColumn(3), kogus);
		
	}
}
