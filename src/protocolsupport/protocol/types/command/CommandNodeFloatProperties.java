package protocolsupport.protocol.types.command;

public class CommandNodeFloatProperties extends CommandNodeProperties {

	protected final int flags;
	protected final float min;
	protected final float max;

	public CommandNodeFloatProperties(int flags, float min, float max) {
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