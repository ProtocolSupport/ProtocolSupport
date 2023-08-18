package protocolsupport.protocol.types.command;

import protocolsupport.protocol.utils.EnumConstantLookup;

public class CommandNodeArgumentStringProperties extends CommandNodeArgumentProperties {

	protected final CommandNodeArgumentStringProperties.StringType stringType;

	public CommandNodeArgumentStringProperties(CommandNodeArgumentStringProperties.StringType type) {
		super(CommandNodeArgumentType.STRING);
		this.stringType = type;
	}

	public CommandNodeArgumentStringProperties.StringType getStringType() {
		return stringType;
	}

	public enum StringType {

	 	SINGLE_WORD, QUOTABLE_PHRASE, GREEDY_PHRASE;
		public static final EnumConstantLookup<CommandNodeArgumentStringProperties.StringType> CONSTANT_LOOKUP = new EnumConstantLookup<>(CommandNodeArgumentStringProperties.StringType.class);

	}

}