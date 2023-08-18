package protocolsupport.protocol.types.command;

public class CommandNodeArgumentResourceProperties extends CommandNodeArgumentProperties {

	protected final String identifier;

	public CommandNodeArgumentResourceProperties(String identifier) {
		super(CommandNodeArgumentType.RESOURCE);
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return identifier;
	}

}