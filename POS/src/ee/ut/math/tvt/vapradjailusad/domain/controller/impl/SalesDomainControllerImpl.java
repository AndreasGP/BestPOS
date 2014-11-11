package ee.ut.math.tvt.vapradjailusad.domain.controller.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import ee.ut.math.tvt.salessystem.util.HibernateUtil;
import ee.ut.math.tvt.vapradjailusad.domain.controller.SalesDomainController;
import ee.ut.math.tvt.vapradjailusad.domain.data.Order;
import ee.ut.math.tvt.vapradjailusad.domain.data.SoldItem;
import ee.ut.math.tvt.vapradjailusad.domain.data.StockItem;
import ee.ut.math.tvt.vapradjailusad.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.vapradjailusad.ui.model.SalesSystemModel;


/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {

	private final Session currentSession = HibernateUtil.currentSession();

	public void submitCurrentPurchase(SalesSystemModel model, List<SoldItem> goods) throws VerificationFailedException {

		Transaction transaction = currentSession.beginTransaction();

		Order order = new Order(
				((DateFormat)new SimpleDateFormat("yyyy/MM/dd")).format(Calendar.getInstance().getTime()),
				((DateFormat)new SimpleDateFormat("HH:mm:ss")).format(Calendar.getInstance().getTime()),
				goods
				);


		for(SoldItem si : goods) {
			
			currentSession.persist(si);
			si.setOrder(order);
			
			//Decrease stockitem's quantity
			Query query = currentSession.createQuery("UPDATE StockItem set QUANTITY = QUANTITY - :kogus WHERE ID = :solditem_id");
			query.setParameter("kogus", si.getQuantity());
			query.setParameter("solditem_id", si.getStockItem().getId());
			query.executeUpdate();
		}

		currentSession.persist(order);
		currentSession.flush();

		transaction.commit();
		model.getWarehouseTableModel().decreaseItems(order);
		model.getHistoryTableModel().addOrder(order);
	}

	public void cancelCurrentPurchase() throws VerificationFailedException {				
		// XXX - Cancel current purchase
	}


	public void startNewPurchase() throws VerificationFailedException {
		// XXX - Start new purchase
	}

	@SuppressWarnings("unchecked")
	public List<StockItem> loadWarehouseState() {

		return currentSession.createQuery("from StockItem").list();

	}

	public void updateWarehouseState() {


	}

	public void endSession() {
		HibernateUtil.closeSession();
	}

	@Override
	public void addItem(StockItem item) {

		Transaction transaction = null;
		transaction = currentSession.beginTransaction();
		currentSession.merge(item);
		currentSession.flush();
		transaction.commit();

	}

	@Override
	public List<Order> loadHistoryTableState() {

		if(currentSession.createSQLQuery("SELECT MAX(ID) FROM ORDER").list().get(0) == null) {
			Order.globalIdIndex = 1;
			SoldItem.soldItemIndex = 1;
		} else {
			int id = (Integer) currentSession.createSQLQuery("SELECT MAX(ID) FROM ORDER").list().get(0);
			int soldId = (Integer) currentSession.createSQLQuery("SELECT MAX(ID) FROM SOLDITEM").list().get(0);
			System.out.println("Setting starting id to " + id);
			Order.globalIdIndex = id + 1;
			SoldItem.soldItemIndex = soldId + 1;
		}
		List query  = currentSession.createQuery("from Order").list();
		return query;
	}
}
