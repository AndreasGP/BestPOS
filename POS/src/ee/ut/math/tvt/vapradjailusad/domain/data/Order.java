package ee.ut.math.tvt.vapradjailusad.domain.data;

import java.util.List;

public class Order implements DisplayableItem {

	private static long globalIdIndex = 1;
	private final Long id;
	private final String date;
	private final String time;
	private final List<SoldItem> soldItems;
	//Using a double as a price breaks the table view for some reason, no time to fix in initial release.
	private final int price;

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
	
	public int getPrice() {
		return price;
	}
}
