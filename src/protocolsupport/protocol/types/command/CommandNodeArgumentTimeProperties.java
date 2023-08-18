package protocolsupport.protocol.types.command;

public class CommandNodeArgumentTimeProperties extends CommandNodeArgumentProperties {

	protected final int min;

	public CommandNodeArgumentTimeProperties(int min) {
		super(CommandNodeArgumentType.TIME);
		this.min = min;
	}

	public int getMin() {
		return min;
	}

}
