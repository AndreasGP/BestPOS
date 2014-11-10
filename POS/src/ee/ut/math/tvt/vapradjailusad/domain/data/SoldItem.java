package ee.ut.math.tvt.vapradjailusad.domain.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;



/**
 * Already bought StockItem. SoldItem duplicates name and price for preserving history. 
 */

@Entity
@Table(name="SOLDITEM")
public class SoldItem implements Cloneable, DisplayableItem {

	public static long soldItemIndex = 1;
	
	@Id
	 @Column(name = "ID", nullable = false)
    private Long id;
	
	@OneToOne
	@JoinColumn(name="ORDER_ID", nullable = false)
    private Order order;
	
	@ManyToOne
	@JoinColumn(name="STOCKITEM_ID", nullable = false)
    private StockItem stockItem;
    
    @Column(name = "NAME")
    private String name;
    
    @Column(name = "QUANTITY")
    private Integer quantity;
    
    @Column(name = "ITEMPRICE")
    private double price;
    
    public SoldItem() {
    	
    }
    
    public SoldItem(StockItem stockItem, int quantity) {
        this.stockItem = stockItem;
        this.name = stockItem.getName();
        this.price = stockItem.getPrice();
        this.id = soldItemIndex++;
        this.quantity = quantity;
        
    }
    
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getSum() {
        return price * ((double) quantity);
    }

    public StockItem getStockItem() {
        return stockItem;
    }

    public void setStockItem(StockItem stockItem) {
        this.stockItem = stockItem;
    }

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
    
    
}
