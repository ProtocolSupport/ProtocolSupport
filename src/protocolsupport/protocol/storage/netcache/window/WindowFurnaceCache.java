package protocolsupport.protocol.storage.netcache.window;

public class WindowFurnaceCache {

	protected static final int maxFuelBurnTimeDefault = 200;
	protected static final int maxProgressDefault = 200;

	protected int maxFuelBurnTime = maxFuelBurnTimeDefault;
	protected int maxProgress = maxProgressDefault;

	public int scaleCurrentFuelBurnTime(int value) {
		return (value * maxFuelBurnTimeDefault) / maxFuelBurnTime;
	}

	public int scaleCurrentCurrentProgress(int value) {
		return (value * maxProgressDefault) / maxProgress;
	}

	public void setMaxFuelBurnTime(int value) {
		this.maxFuelBurnTime = value;
	}

	public void setMaxProgress(int value) {
		this.maxProgress = value;
	}

}
