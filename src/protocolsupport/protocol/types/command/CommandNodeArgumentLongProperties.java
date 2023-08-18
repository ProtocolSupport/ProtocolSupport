package protocolsupport.protocol.types.command;

public class CommandNodeArgumentLongProperties extends CommandNodeArgumentProperties {

	protected final int flags;
	protected final long min;
	protected final long max;

	public CommandNodeArgumentLongProperties(int flags, long min, long max) {
		super(CommandNodeArgumentType.LONG);
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