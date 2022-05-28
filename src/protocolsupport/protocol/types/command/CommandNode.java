package protocolsupport.protocol.types.command;

public class CommandNode {

	protected final int flags;
	protected final int[] childNodesIndexes;
	protected final int redirectNodeIndex;
	protected final String name;
	protected final String parser;
	protected final CommandNodeProperties properties;
	protected final String suggestType;

	public CommandNode(int flags, int[] childNodesIndexes, int redirectNodeIndex, String name, String parser, CommandNodeProperties properties, String suggestType) {
		this.flags = flags;
		this.childNodesIndexes = childNodesIndexes;
		this.redirectNodeIndex = redirectNodeIndex;
		this.name = name;
		this.parser = parser;
		this.properties = properties;
		this.suggestType = suggestType;
	}

	public int getFlags() {
		return flags;
	}


	public int[] getChildNodesIndexes() {
		return childNodesIndexes;
	}


	public int getRedirectNodeIndex() {
		return redirectNodeIndex;
	}


	public String getName() {
		return name;
	}


	public String getParser() {
		return parser;
	}


	public CommandNodeProperties getProperties() {
		return properties;
	}


	public String getSuggestType() {
		return suggestType;
	}

}