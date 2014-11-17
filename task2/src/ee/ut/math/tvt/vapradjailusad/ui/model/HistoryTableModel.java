package ee.ut.math.tvt.vapradjailusad.ui.model;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.vapradjailusad.domain.data.Order;

/** Order history table model */
public class HistoryTableModel extends SalesSystemTableModel<Order> {

	private static final long serialVersionUID = 1462723472478L;

	private static final Logger logger = Logger.getLogger(HistoryTableModel.class);

	public HistoryTableModel() {
		super(new String[] {"Id", "Date", "Time", "Price"});
	}

	@Override
	protected Object getColumnValue(Order order, int columnIndex) {
		
		switch (columnIndex) {
		case 0:
			return order.getId();
		case 1:
			return order.getDate();
		case 2:
			return order.getTime();
		case 3:
			return order.getPrice();
		}
		throw new IllegalArgumentException("Column index is out of range.(" + columnIndex + ")");
	}


	/**
	 * Add a new order to the table.
	 * @param order
	 */
	public void addOrder(final Order order) {
		rows.add(order);
		logger.debug("Added Order with id " + order.getId() + " and timestamp " + order.getDate() + " " + order.getTime());
		fireTableDataChanged();
	}
	
	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final Order order : rows) {
			buffer.append(order.getId() + "\t");
			buffer.append(order.getDate() + "\t");
			buffer.append(order.getTime() + "\t");
			buffer.append(order.getPrice() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}
}
