package ee.ut.math.tvt.vapradjailusad.ui.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.vapradjailusad.domain.data.Order;
import ee.ut.math.tvt.vapradjailusad.domain.data.SoldItem;
import ee.ut.math.tvt.vapradjailusad.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.vapradjailusad.ui.model.SalesSystemModel;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "History" in the menu).
 */
public class HistoryTab {

	private static final Logger log = Logger.getLogger(PurchaseInfoTableModel.class);

	private final SalesSystemModel model;

	public HistoryTab(SalesSystemModel model) {
		this.model = model;
	} 

	// order history tab - consists of a menu and a table
	public Component draw() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints gc = new GridBagConstraints();
		panel.setLayout(gb);

		gc.weightx = 1.0d;
		gc.weighty = 1.0;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(createHistoryTable(), gc);
		return panel;
	}

	// History table
	private Component createHistoryTable() {
		JPanel panel = new JPanel();

		final JTable table = new JTable(model.getHistoryTableModel());

		table.addMouseListener(new java.awt.event.MouseAdapter() {
		    @Override
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		        int row = table.rowAtPoint(evt.getPoint());
		        showDetails(row);
		    }
		});
		
		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);

		JScrollPane scrollPane = new JScrollPane(table);

		GridBagConstraints gc = new GridBagConstraints();
		GridBagLayout gb = new GridBagLayout();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;

		panel.setLayout(gb);
		panel.add(scrollPane, gc);

		panel.setBorder(BorderFactory.createTitledBorder("Order history"));
		return panel; 
	}
	
	/** Shows the detailed view for the given row. */
	private void showDetails(int row) {
		Order order = model.getHistoryTableModel().getTableRows().get(row);
		StringBuilder details = new StringBuilder();
		for (SoldItem item : order.getSoldItems()) {
			details.append(item.getStockItem().toString() + "\n");
		}
		JOptionPane.showMessageDialog(new JFrame(), details);
	}
}