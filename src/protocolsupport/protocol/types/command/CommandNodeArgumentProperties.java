package protocolsupport.protocol.types.command;

public class CommandNodeArgumentProperties {

	protected final CommandNodeArgumentType type;

	public CommandNodeArgumentProperties(CommandNodeArgumentType type) {
		this.type = type;
	}

	public CommandNodeArgumentType getType() {
		return type;
	}

}