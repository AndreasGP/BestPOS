package ee.ut.math.tvt.vapradjailusad.ui.tabs;


import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.vapradjailusad.domain.controller.SalesDomainController;
import ee.ut.math.tvt.vapradjailusad.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.vapradjailusad.domain.data.StockItem;
import ee.ut.math.tvt.vapradjailusad.ui.SalesSystemUI;
import ee.ut.math.tvt.vapradjailusad.ui.model.SalesSystemModel;


public class StockTab {

	private static final Logger log = Logger.getLogger(StockTab.class);

	private JButton addItemButton;

	private final SalesSystemModel model;
	
	private final SalesSystemUI salesSystem;
	
	private final SalesDomainController dc;

	public StockTab(SalesSystemUI salesSystem, SalesSystemModel model, SalesDomainController dc) {
		this.salesSystem = salesSystem;
		this.model = model;
		this.dc = dc;
	}

	// warehouse stock tab - consists of a menu and a table
	public Component draw() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints gc = new GridBagConstraints();
		panel.setLayout(gb);

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 0d;

		panel.add(drawStockMenuPane(), gc);

		gc.weighty = 1.0;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(drawStockMainPane(), gc);
		return panel;
	}

	// warehouse menu
	private Component drawStockMenuPane() {
		JPanel panel = new JPanel();

		GridBagConstraints gc = new GridBagConstraints();
		GridBagLayout gb = new GridBagLayout();

		panel.setLayout(gb);

		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.weightx = 0;

		addItemButton = new JButton("Add");
		addItemButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				addItemButtonClicked();
			}
		});     
		gc.gridwidth = GridBagConstraints.RELATIVE;
		gc.weightx = 1.0;
		panel.add(addItemButton, gc);

		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		return panel;
	}


	// table of the wareshouse stock
	private Component drawStockMainPane() {
		JPanel panel = new JPanel();

		JTable table = new JTable(model.getWarehouseTableModel());

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

		panel.setBorder(BorderFactory.createTitledBorder("Warehouse status"));
		return panel;
	}

	private void addItemButtonClicked() {
		final JPanel itemAddingPanel = new JPanel();
		itemAddingPanel.setLayout(new BoxLayout(itemAddingPanel, BoxLayout.PAGE_AXIS));
		itemAddingPanel.add(new JLabel("Enter item numeric id: \n"));
		final JTextField idTextField = new JTextField(5);
		idTextField.setBorder(BorderFactory.createLineBorder(Color.black));
		itemAddingPanel.add(idTextField);

		itemAddingPanel.add(new JLabel("Enter item name: "));
		final JTextField nameTextField = new JTextField(10);
		nameTextField.setBorder(BorderFactory.createLineBorder(Color.black));
		itemAddingPanel.add(nameTextField);

		itemAddingPanel.add(new JLabel("Enter item price: "));
		final JTextField priceTextField = new JTextField(10);
		priceTextField.setBorder(BorderFactory.createLineBorder(Color.black));
		itemAddingPanel.add(priceTextField);

		itemAddingPanel.add(new JLabel("Enter item quantity: "));
		final JTextField quantityTextField = new JTextField(10);
		quantityTextField.setBorder(BorderFactory.createLineBorder(Color.black));
		itemAddingPanel.add(quantityTextField);

		itemAddingPanel.add(new JLabel("Enter item description: "));
		final JTextField descriptionTextField = new JTextField();
		descriptionTextField.setBorder(BorderFactory.createLineBorder(Color.black));
		itemAddingPanel.add(descriptionTextField);

		JFrame frame = new JFrame();
		String[] options = new String[2];
		options[0] = new String("Add item");
		options[1] = new String("Cancel");
		int result = JOptionPane.showOptionDialog(frame.getContentPane(),itemAddingPanel,
				"Add a new item", 0, JOptionPane.INFORMATION_MESSAGE, null, options, null);
		if (result == 0) {
			log.info("Accepted stock adding.");

			boolean success = true;
			
			long id = 0;
			try {
				id = Long.parseLong(idTextField.getText());
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(new JFrame(), "Invalid ID. Has to be an integer.");
				success = false;
			}
			
			if(model.getWarehouseTableModel().containsId(id)) {
				JOptionPane.showMessageDialog(new JFrame(), "Given ID is already in use.");
				success = false;
			}
			
			double price = 0;
			try {
				price = Double.parseDouble(priceTextField.getText());
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(new JFrame(), "Invalid price. Make sure you use . instead of ,");
				success = false;
			}
			
			int quantity = 0;
			try {
				quantity = Integer.parseInt(quantityTextField.getText());
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(new JFrame(), "Invalid quantity. Has to be an integer.");
				success = false;
			}
			
			
			if(success) {
				String name = nameTextField.getText();
				String description = descriptionTextField.getText();

				StockItem item = new StockItem(id, name, description, price, quantity);
				model.getWarehouseTableModel().addItem(item);
				dc.addItem(item);
				
				salesSystem.getPurchaseTab().updateBarcodes();
			}
		}
		else {
			log.info("Cancelled stock adding.");
		}
	}

}
