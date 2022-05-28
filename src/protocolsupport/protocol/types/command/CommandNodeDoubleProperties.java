package protocolsupport.protocol.types.command;

public class CommandNodeDoubleProperties extends CommandNodeProperties {

	protected final int flags;
	protected final double min;
	protected final double max;

	public CommandNodeDoubleProperties(int flags, double min, double max) {
		this.flags = flags;
		this.min = min;
		this.max = max;
	}

	public int getFlags() {
		return flags;
	}

	public double getMin() {
		return min;
	}

	public double getMax() {
		return max;
	}

}