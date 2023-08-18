package protocolsupport.protocol.types.command;

public class CommandNodeArgumentResourceKeyProperties extends CommandNodeArgumentProperties {

	protected final String identifier;

	public CommandNodeArgumentResourceKeyProperties(String identifier) {
		super(CommandNodeArgumentType.RESOURCE_KEY);
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}

}