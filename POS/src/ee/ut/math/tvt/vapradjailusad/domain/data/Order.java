package ee.ut.math.tvt.vapradjailusad.domain.data;

import java.util.List;

public class Order implements DisplayableItem {

	private static long globalIdIndex = 1;
	private final Long id;
	private final String date;
	private final String time;
	private final List<SoldItem> soldItems;

	public Order(String date, String time, List<SoldItem> soldItems) {
		this.date = date;
		this.time = time;
		this.soldItems = soldItems;
		this.id = globalIdIndex++;
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
}
