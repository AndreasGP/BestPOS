package ee.ut.math.tvt.vapradjailusad.domain.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ORDER")
public class Order implements DisplayableItem {

	public static long globalIdIndex = 1;
	
	@Id
	@Column(name = "ID", nullable = false)
	private  Long id;
	
	@Column(name = "price")
	private int price;
	
	@Column(name = "date")
	private  String date;
	
	@Column(name = "time")
	private  String time;
	
	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private  List<SoldItem> soldItems;	

	public Order(String date, String time, List<SoldItem> soldItems) {
		this.date = date;
		this.time = time;
		this.soldItems = soldItems;
		this.id = globalIdIndex++;
		double sum = 0;
		for(SoldItem item : soldItems) {
			sum += item.getSum();
		}
		price = (int)sum;
	}
	
	public Order() {
	}

	@Override
	public Long getId() {
		return id;
	}
	
	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}

	public List<SoldItem> getSoldItems() {
		return soldItems;
	}
	
	public void setSoldItems(List<SoldItem> soldItems) {
		this.soldItems = soldItems;
		double sum = 0;
		for(SoldItem item : soldItems) {
			sum += item.getSum();
		}
		price = (int)sum;
	}
	
	public int getPrice() {
		return price;
	}
}
