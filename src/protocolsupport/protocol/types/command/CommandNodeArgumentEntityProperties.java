package protocolsupport.protocol.types.command;

public class CommandNodeArgumentEntityProperties extends CommandNodeArgumentProperties {

	protected final int flags;

	public CommandNodeArgumentEntityProperties(int flags) {
		super(CommandNodeArgumentType.ENTITY);
		this.flags = flags;
	}

	public int getFlags() {
		return flags;
	}

}