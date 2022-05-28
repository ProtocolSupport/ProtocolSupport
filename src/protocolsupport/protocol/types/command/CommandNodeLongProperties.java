package protocolsupport.protocol.types.command;

public class CommandNodeLongProperties extends CommandNodeProperties {

	protected final int flags;
	protected final long min;
	protected final long max;

	public CommandNodeLongProperties(int flags, long min, long max) {
		this.flags = flags;
		this.min = min;
		this.max = max;
	}

	public int getFlags() {
		return flags;
	}

	public long getMin() {
		return min;
	}

	public long getMax() {
		return max;
	}

}