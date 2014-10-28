package ee.ut.math.tvt.vapradjailusad.ui.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.vapradjailusad.domain.controller.SalesDomainController;
import ee.ut.math.tvt.vapradjailusad.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.vapradjailusad.ui.model.SalesSystemModel;
import ee.ut.math.tvt.vapradjailusad.ui.panels.PurchaseItemPanel;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "Point-of-sale" in the menu).
 */
public class PurchaseTab {

  private static final Logger log = Logger.getLogger(PurchaseTab.class);

  private final SalesDomainController domainController;

  private JButton newPurchase;

  private JButton submitPurchase;

  private JButton cancelPurchase;

  private PurchaseItemPanel purchasePane;

  private SalesSystemModel model;
  
  public PurchaseTab(SalesDomainController controller,
      SalesSystemModel model)
  {
    this.domainController = controller;
    this.model = model;
  }


  /**
   * The purchase tab. Consists of the purchase menu, current purchase dialog and
   * shopping cart table.
   */
  public Component draw() {
    JPanel panel = new JPanel();

    // Layout
    panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    panel.setLayout(new GridBagLayout());

    // Add the purchase menu
    panel.add(getPurchaseMenuPane(), getConstraintsForPurchaseMenu());

    // Add the main purchase-panel
    purchasePane = new PurchaseItemPanel(model);
    panel.add(purchasePane, getConstraintsForPurchasePanel());

    return panel;
  }




  // The purchase menu. Contains buttons "New purchase", "Submit", "Cancel".
  private Component getPurchaseMenuPane() {
    JPanel panel = new JPanel();

    // Initialize layout
    panel.setLayout(new GridBagLayout());
    GridBagConstraints gc = getConstraintsForMenuButtons();

    // Initialize the buttons
    newPurchase = createNewPurchaseButton();
    submitPurchase = createConfirmButton();
    cancelPurchase = createCancelButton();

    // Add the buttons to the panel, using GridBagConstraints we defined above
    panel.add(newPurchase, gc);
    panel.add(submitPurchase, gc);
    panel.add(cancelPurchase, gc);

    return panel;
  }


  // Creates the button "New purchase"
  private JButton createNewPurchaseButton() {
    JButton b = new JButton("New purchase");
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        newPurchaseButtonClicked();
      }
    });

    return b;
  }

  // Creates the "Confirm" button
  private JButton createConfirmButton() {
    JButton b = new JButton("Confirm");
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        confirmPurchaseButtonClicked();
      }
    });
    b.setEnabled(false);

    return b;
  }


  // Creates the "Cancel" button
  private JButton createCancelButton() {
    JButton b = new JButton("Cancel");
    b.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelPurchaseButtonClicked();
      }
    });
    b.setEnabled(false);

    return b;
  }





  /* === Event handlers for the menu buttons
   *     (get executed when the buttons are clicked)
   */


  /** Event handler for the <code>new purchase</code> event. */
  protected void newPurchaseButtonClicked() {
    log.info("New sale process started");
    try {
      domainController.startNewPurchase();
      startNewSale();
    } catch (VerificationFailedException e1) {
      log.error(e1.getMessage());
    }
  }


  /**  Event handler for the <code>cancel purchase</code> event. */
  protected void cancelPurchaseButtonClicked() {
    log.info("Sale cancelled");
    try {
      domainController.cancelCurrentPurchase();
      endSale();
      model.getCurrentPurchaseTableModel().clear();
    } catch (VerificationFailedException e1) {
      log.error(e1.getMessage());
    }
  }
  
  /** Event handler for the <code>submit purchase</code> event. */
  protected void submitPurchaseButtonClicked() {
    log.info("Sale complete");
    try {
      log.debug("Contents of the current basket:\n" + model.getCurrentPurchaseTableModel());
      domainController.submitCurrentPurchase(model, model.getCurrentPurchaseTableModel().getTableRows()
      );
      endSale();
      model.getCurrentPurchaseTableModel().clear();
    } catch (VerificationFailedException e1) {
      log.error(e1.getMessage());
    }
  }
  
  protected void confirmPurchaseButtonClicked() {
	final float sum = calculateSum();
	
	final JTextField paymentAmount = new JTextField(5);
	final JPanel paymentConfirmationPanel = new JPanel();
	paymentConfirmationPanel.setLayout(new BoxLayout(paymentConfirmationPanel, BoxLayout.PAGE_AXIS));
    paymentConfirmationPanel.add(new JLabel("Total amount: " + sum + "\n"));
    paymentConfirmationPanel.add(new JLabel("Payment amount: "));
    paymentConfirmationPanel.add(paymentAmount);
    final StringBuilder input = new StringBuilder();
    input.append(".");
    final JLabel changeAmount = new JLabel("Change amount: -");
    paymentAmount.getDocument().addDocumentListener(new DocumentListener() {
		
		@Override
		public void changedUpdate(DocumentEvent e) {
			insertUpdate(e);
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			try{
            	if (Integer.parseInt(paymentAmount.getText())<=0){
            		JOptionPane.showMessageDialog(new JFrame(), "Amount must be positive");
            	}
            	else {
            		input.delete(0, input.length());
            		input.append(paymentAmount.getText());
            		changeAmount.setText("Change amount: " + (Float.parseFloat(input.toString()) - sum));
            		paymentConfirmationPanel.add(changeAmount);
            	}
            }
            catch (NumberFormatException nfe) {
            	changeAmount.setText("Change amount: -");
            	JOptionPane.showMessageDialog(new JFrame(), "Invalid number. Make sure you use a dot, not a coma.");
            } 
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			insertUpdate(e);
		}
	});
    paymentConfirmationPanel.add(Box.createHorizontalStrut(15));
    paymentConfirmationPanel.add(changeAmount);
    
    JFrame frame = new JFrame();
    String[] options = new String[2];
    options[0] = new String("Accept");
    options[1] = new String("Cancel");
    int result = JOptionPane.showOptionDialog(frame.getContentPane(),paymentConfirmationPanel,
    		"Confirmation", 0, JOptionPane.INFORMATION_MESSAGE, null, options, null);
    if (result == 0) {
    	log.info("Accepted payment.");
    	submitPurchaseButtonClicked();
    }
    else {
    	log.info("Cancelled payment.");
    }
  }
  
  protected float calculateSum() {
	  float sum = 0;
		for (int i = 0; i < model.getCurrentPurchaseTableModel().getRowCount(); i++) {
			sum += Float.parseFloat(model.getCurrentPurchaseTableModel().getValueAt(i, 4).toString());
		}
	return sum;
  }
  
  /* === Helper methods that bring the whole purchase-tab to a certain state
   *     when called.
   */

  // switch UI to the state that allows to proceed with the purchase
  private void startNewSale() {
    purchasePane.reset();

    purchasePane.setEnabled(true);
    submitPurchase.setEnabled(true);
    cancelPurchase.setEnabled(true);
    newPurchase.setEnabled(false);
  }

  // switch UI to the state that allows to initiate new purchase
  private void endSale() {
    purchasePane.reset();

    cancelPurchase.setEnabled(false);
    submitPurchase.setEnabled(false);
    newPurchase.setEnabled(true);
    purchasePane.setEnabled(false);
  }




  /* === Next methods just create the layout constraints objects that control the
   *     the layout of different elements in the purchase tab. These definitions are
   *     brought out here to separate contents from layout, and keep the methods
   *     that actually create the components shorter and cleaner.
   */

  private GridBagConstraints getConstraintsForPurchaseMenu() {
    GridBagConstraints gc = new GridBagConstraints();

    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.anchor = GridBagConstraints.NORTH;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.weightx = 1.0d;
    gc.weighty = 0d;

    return gc;
  }


  private GridBagConstraints getConstraintsForPurchasePanel() {
    GridBagConstraints gc = new GridBagConstraints();

    gc.fill = GridBagConstraints.BOTH;
    gc.anchor = GridBagConstraints.NORTH;
    gc.gridwidth = GridBagConstraints.REMAINDER;
    gc.weightx = 1.0d;
    gc.weighty = 1.0;

    return gc;
  }


  // The constraints that control the layout of the buttons in the purchase menu
  private GridBagConstraints getConstraintsForMenuButtons() {
    GridBagConstraints gc = new GridBagConstraints();

    gc.weightx = 0;
    gc.anchor = GridBagConstraints.CENTER;
    gc.gridwidth = GridBagConstraints.RELATIVE;

    return gc;
  }

}
