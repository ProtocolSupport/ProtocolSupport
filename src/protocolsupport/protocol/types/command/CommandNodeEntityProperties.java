package protocolsupport.protocol.types.command;

public class CommandNodeEntityProperties extends CommandNodeProperties {

	protected final int flags;

	public CommandNodeEntityProperties(int flags) {
		this.flags = flags;
	}

	public int getFlags() {
		return flags;
	}

}