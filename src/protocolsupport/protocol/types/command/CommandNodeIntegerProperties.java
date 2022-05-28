package protocolsupport.protocol.types.command;

public class CommandNodeIntegerProperties extends CommandNodeProperties {

	protected final int flags;
	protected final int min;
	protected final int max;

	public CommandNodeIntegerProperties(int flags, int min, int max) {
		this.flags = flags;
		this.min = min;
		this.max = max;
	}

	public int getFlags() {
		return flags;
	}

	public int getMin() {
		return min;
	}

	public int getMax() {
		return max;
	}

}