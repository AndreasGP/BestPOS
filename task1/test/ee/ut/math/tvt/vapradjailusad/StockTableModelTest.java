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
import ee.ut.math.tvt.vapradjailusad.ui.model.StockTableModel;

public class StockTableModelTest {

	StockTableModel stm;
	StockItem stockItem1;
	StockItem stockItem2;
	
	StockItem stockItem3;
	SoldItem soldItem1;
	List<SoldItem> soldItems;
	
	@Before
	public void setUp() {
		
		stockItem1 = new StockItem((long) 1, "Vesi", "", 10);
		stockItem2 = new StockItem((long) 1, "Viin", "", 20);
		stockItem3 = new StockItem((long) 1, "Vesi", "", 10, 2);
		soldItem1 = new SoldItem(stockItem3, 3);
		stm = new StockTableModel();
	}
	
	
	/**
	  *Since our program allows same name to be used, we test if adding item with same ID has same name.
	  *If item has same name, we increase quantity.
	  *If the name is different, then ID is not unique.
	 */
	
	@Test
	(expected = IllegalArgumentException.class)
	public void testValidateNameUniqueness() {
		
		stm.addItem(stockItem1);
		stm.addItem(stockItem2);
	}
	
	@Test
	(expected = IllegalArgumentException.class)
	public void testHasEnoughInStock() {
		
		soldItems = new ArrayList<SoldItem>();
		soldItems.add(soldItem1);
		Order order = new Order("12/12/2014", "12:34:56", soldItems);
		stm.addItem(stockItem3);
		stm.decreaseItems(order);
	}
	
	@Test
	public void testGetItemByIdWhenItemExists() {
		
		stm.addItem(stockItem1);
		assertEquals(stm.containsId(1L), true);
	}
	
	@Test
	(expected = NoSuchElementException.class)
	public void testGetItemByIdWhenThrowsException() {
		
		stm.containsId(2L);
	}
}
