package protocolsupport.protocol.types.command;

import protocolsupport.protocol.utils.EnumConstantLookup;

public class CommandNodeStringProperties extends CommandNodeProperties {

	protected final CommandNodeStringProperties.Type type;

	public CommandNodeStringProperties(CommandNodeStringProperties.Type type) {
		this.type = type;
	}

	public CommandNodeStringProperties.Type getType() {
		return type;
	}

	public enum Type {

	 	SINGLE_WORD, QUOTABLE_PHRASE, GREEDY_PHRASE;
		public static final EnumConstantLookup<CommandNodeStringProperties.Type> CONSTANT_LOOKUP = new EnumConstantLookup<>(CommandNodeStringProperties.Type.class);

	}

}