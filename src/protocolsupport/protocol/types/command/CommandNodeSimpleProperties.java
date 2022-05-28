package protocolsupport.protocol.types.command;

//TODO: implement properties for each parser
public class CommandNodeSimpleProperties extends CommandNodeProperties {

	protected final String parser;

	public CommandNodeSimpleProperties(String type) {
		this.parser = type;
	}

	public String getParser() {
		return parser;
	}

}
