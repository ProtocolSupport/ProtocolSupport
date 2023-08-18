package protocolsupport.protocol.types.command;

public class CommandNodeArgumentIntegerProperties extends CommandNodeArgumentProperties {

	protected final int flags;
	protected final int min;
	protected final int max;

	public CommandNodeArgumentIntegerProperties(int flags, int min, int max) {
		super(CommandNodeArgumentType.INTEGER);
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