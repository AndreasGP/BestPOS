package ee.ut.math.tvt.vapradjailusad.domain.data;

/**
 * Base interface for data items, so one JTable can be used to display different entities.
 */
public interface DisplayableItem {
	/**
	 * Id of entity.
	 */
	public Long getId();
}
