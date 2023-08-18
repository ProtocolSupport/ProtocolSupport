package protocolsupport.protocol.types.command;

public class CommandNodeArgumentDoubleProperties extends CommandNodeArgumentProperties {

	protected final int flags;
	protected final double min;
	protected final double max;

	public CommandNodeArgumentDoubleProperties(int flags, double min, double max) {
		super(CommandNodeArgumentType.DOUBLE);
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