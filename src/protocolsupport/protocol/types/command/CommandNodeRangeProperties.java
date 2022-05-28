package protocolsupport.protocol.types.command;

public class CommandNodeRangeProperties extends CommandNodeProperties {

	protected final boolean allowDecimals;

	public CommandNodeRangeProperties(boolean allowDecimals) {
		this.allowDecimals = allowDecimals;
	}

	public boolean allowDecimals() {
		return allowDecimals;
	}

}