package protocolsupport.protocol.types.command;

public class CommandNode {

	protected final int flags;
	protected final int[] childNodesIndexes;
	protected final int redirectNodeIndex;
	protected final String name;
	protected final CommandNodeArgumentProperties argumentProperties;
	protected final String suggestType;

	public CommandNode(int flags, int[] childNodesIndexes, int redirectNodeIndex, String name, CommandNodeArgumentProperties argumentProperties, String suggestType) {
		this.flags = flags;
		this.childNodesIndexes = childNodesIndexes;
		this.redirectNodeIndex = redirectNodeIndex;
		this.name = name;
		this.argumentProperties = argumentProperties;
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

	public CommandNodeArgumentProperties getArgumentProperties() {
		return argumentProperties;
	}

	public String getSuggestType() {
		return suggestType;
	}

}