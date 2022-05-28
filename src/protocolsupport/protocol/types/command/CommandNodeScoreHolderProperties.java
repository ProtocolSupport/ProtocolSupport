package protocolsupport.protocol.types.command;

public class CommandNodeScoreHolderProperties extends CommandNodeProperties {

	protected final int flags;

	public CommandNodeScoreHolderProperties(int flags) {
		this.flags = flags;
	}

	public int getFlags() {
		return flags;
	}

}