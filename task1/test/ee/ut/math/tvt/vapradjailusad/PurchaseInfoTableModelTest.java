package ee.ut.math.tvt.vapradjailusad;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.vapradjailusad.domain.data.Order;
import ee.ut.math.tvt.vapradjailusad.domain.data.SoldItem;
import ee.ut.math.tvt.vapradjailusad.domain.data.StockItem;
import ee.ut.math.tvt.vapradjailusad.ui.model.HistoryTableModel;
import ee.ut.math.tvt.vapradjailusad.ui.model.PurchaseInfoTableModel;

public class PurchaseInfoTableModelTest {

	PurchaseInfoTableModel pitm;
	StockItem stockItem1;
	SoldItem soldItem1;
	
	@Before
	public void setUp() {
		
		pitm = new PurchaseInfoTableModel();
		stockItem1 = new StockItem((long) 1, "Vesi", "", 10);
		soldItem1 = new SoldItem(stockItem1, 1);
	}
	
	@Test
	public void testGetColumnValue() {
				
		assertEquals(pitm.getColumnValue(soldItem1, 0), (Long)((long) 12));
		assertEquals(pitm.getColumnValue(soldItem1, 1), "Vesi");
		assertEquals((Double) pitm.getColumnValue(soldItem1, 2), (Double) 10.0, 0.0001);
		assertEquals(pitm.getColumnValue(soldItem1, 3), (Integer) 1);
		assertEquals((Double) pitm.getColumnValue(soldItem1, 4), (Double) 10.0, 0.0001);
		
	}
	
	@Test
	(expected = IllegalArgumentException.class)
	public void testGetColumnValueWhenThrowsException() {
		
		assertEquals(pitm.getColumnValue(soldItem1, 5), null);
	}
}
