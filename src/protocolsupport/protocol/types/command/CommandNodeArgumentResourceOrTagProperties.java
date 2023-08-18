package protocolsupport.protocol.types.command;

public class CommandNodeArgumentResourceOrTagProperties extends CommandNodeArgumentProperties {

	protected final String identifier;

	public CommandNodeArgumentResourceOrTagProperties(String identifier) {
		super(CommandNodeArgumentType.RESOURCE_OR_TAG);
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}

}