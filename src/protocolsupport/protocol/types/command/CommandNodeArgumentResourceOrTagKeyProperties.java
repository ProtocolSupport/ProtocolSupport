package protocolsupport.protocol.types.command;

public class CommandNodeArgumentResourceOrTagKeyProperties extends CommandNodeArgumentProperties {

	protected final String identifier;

	public CommandNodeArgumentResourceOrTagKeyProperties(String identifier) {
		super(CommandNodeArgumentType.RESOURCE_OR_TAG_KEY);
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}

}