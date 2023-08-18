package protocolsupport.protocol.types.command;

public class CommandNodeArgumentScoreHolderProperties extends CommandNodeArgumentProperties {

	protected final int flags;

	public CommandNodeArgumentScoreHolderProperties(int flags) {
		super(CommandNodeArgumentType.SCORE_HOLDER);
		this.flags = flags;
	}

	public int getFlags() {
		return flags;
	}

}