package ee.ut.math.tvt.vapradjailusad;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.vapradjailusad.domain.data.SoldItem;
import ee.ut.math.tvt.vapradjailusad.domain.data.StockItem;

public class SoldItemTest {

	StockItem stockItem1;
	SoldItem soldItem1;
	private static double cost1 = 50;
	
	@Before
	public void setUp() {
		
		stockItem1 = new StockItem((long) 1, "Viin", "40%", cost1);

	}
	
	@Test
	public void testGetSum() {
		
		soldItem1 = new SoldItem(stockItem1, 4);	
		assertEquals(soldItem1.getSum(), cost1 * 4, 0.0001);
	}
	
	@Test
	public void testGetSumWithZeroQuantity() {
		
		soldItem1 = new SoldItem(stockItem1, 0);	
		assertEquals(soldItem1.getSum(), 0.00, 0.0001);
	}

}
