package ee.ut.math.tvt.vapradjailusad;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.vapradjailusad.domain.data.Order;
import ee.ut.math.tvt.vapradjailusad.domain.data.SoldItem;
import ee.ut.math.tvt.vapradjailusad.domain.data.StockItem;
import ee.ut.math.tvt.vapradjailusad.ui.model.HistoryTableModel;

public class HistoryTableModelTest {
	
	HistoryTableModel htm;
	StockItem stockItem1;
	SoldItem soldItem1;
	List<SoldItem> soldItems;

	@Before
	public void setUp() {
		
		htm = new HistoryTableModel();
		stockItem1 = new StockItem((long) 1, "Vesi", "", 10);
		soldItem1 = new SoldItem(stockItem1, 1);

	}
	
	@Test
	public void testGetColumnValue() {
		
		soldItems = new ArrayList<SoldItem>();
		soldItems.add(soldItem1);
		Order order = new Order("12/12/2014", "12:34:56", soldItems);
		htm.addOrder(order);
		
		assertEquals(htm.getColumnValue(order, 0), (Long)((long) 2));
		assertEquals(htm.getColumnValue(order, 1), "12/12/2014");
		assertEquals(htm.getColumnValue(order, 2), "12:34:56");
		assertEquals(htm.getColumnValue(order, 3), (Integer) 10);
		
	}
	
	@Test
	(expected = NoSuchElementException.class)
	public void testGetColumnValueWhenThrowsException() {
		
		soldItems = new ArrayList<SoldItem>();
		soldItems.add(soldItem1);
		Order order = new Order("12/12/2014", "12:34:56", soldItems);
		
		htm.containsOrder(order);
	}
}
