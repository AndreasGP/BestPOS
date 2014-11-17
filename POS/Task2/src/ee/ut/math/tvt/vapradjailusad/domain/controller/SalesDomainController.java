package ee.ut.math.tvt.vapradjailusad.domain.controller;

import java.util.List;

import ee.ut.math.tvt.vapradjailusad.domain.data.Order;
import ee.ut.math.tvt.vapradjailusad.domain.data.SoldItem;
import ee.ut.math.tvt.vapradjailusad.domain.data.StockItem;
import ee.ut.math.tvt.vapradjailusad.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.vapradjailusad.ui.model.SalesSystemModel;


/**
 * Sales domain controller is responsible for the domain specific business
 * processes.
 */
public interface SalesDomainController {
	
    /**
     * Load the current state of the warehouse.
     * 
     * @return List of ${link
     *         ee.ut.math.tvt.salessystem.domain.data.StockItem}s.
     */
    public List<StockItem> loadWarehouseState();

    // business processes
    /**
     * Initiate new business transaction - purchase of the goods.
     * 
     * @throws VerificationFailedException
     */
    public void startNewPurchase() throws VerificationFailedException;

    /**
     * Rollback business transaction - purchase of goods.
     * 
     * @throws VerificationFailedException
     */
    public void cancelCurrentPurchase() throws VerificationFailedException;

    /**
     * Commit business transaction - purchsae of goods.
     * 
     * @param goods
     *            Goods that the buyer has chosen to buy.
     * @throws VerificationFailedException
     */
    public void submitCurrentPurchase(SalesSystemModel model, List<SoldItem> goods)
            throws VerificationFailedException;
    
    
    public void endSession();

	public void addItem(StockItem item);

	public List<Order> loadHistoryTableState();

    
}
