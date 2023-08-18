package protocolsupport.protocol.types.command;

public class CommandNodeArgumentFloatProperties extends CommandNodeArgumentProperties {

	protected final int flags;
	protected final float min;
	protected final float max;

	public CommandNodeArgumentFloatProperties(int flags, float min, float max) {
		super(CommandNodeArgumentType.FLOAT);
		this.flags = flags;
		this.min = min;
		this.max = max;
	}

	public int getFlags() {
		return flags;
	}

	public float getMin() {
		return min;
	}

	public float getMax() {
		return max;
	}

}