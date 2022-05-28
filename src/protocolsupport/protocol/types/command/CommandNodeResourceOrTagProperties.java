package protocolsupport.protocol.types.command;

public class CommandNodeResourceOrTagProperties extends CommandNodeProperties {

	protected final String identifier;

	public CommandNodeResourceOrTagProperties(String identifier) {
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}

}