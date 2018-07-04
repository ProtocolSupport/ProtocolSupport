package protocolsupport.protocol.storage.netcache;

public class PETitleCache {

	private String title;
	private int visibleOnScreenTicks = 20 + 60 + 20;
	private long lastSentTitle;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
		this.lastSentTitle = System.currentTimeMillis();
	}

	public int getVisibleOnScreenTicks() {
		return visibleOnScreenTicks;
	}

	public void setVisibleOnScreenTicks(int visibleOnScreenTicks) {
		this.visibleOnScreenTicks = visibleOnScreenTicks;
	}

	public long getLastSentTitle() {
		return lastSentTitle;
	}

}
