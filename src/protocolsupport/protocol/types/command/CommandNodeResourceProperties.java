package protocolsupport.protocol.types.command;

public class CommandNodeResourceProperties extends CommandNodeProperties {

	protected final String identifier;

	public CommandNodeResourceProperties(String identifier) {
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}

}