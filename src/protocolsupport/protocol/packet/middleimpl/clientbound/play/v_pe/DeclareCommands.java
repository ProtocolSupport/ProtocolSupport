package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleDeclareCommands;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.typeremapper.pe.PEPacketIDs;
import protocolsupport.utils.Utils;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeclareCommands extends MiddleDeclareCommands {

	private static final byte FLAG_IS_LITERAL = 1;
	private static final byte FLAG_IS_ARGUMENT = 2;
	private static final byte FLAG_IS_COMMAND_PATH_END = 4;
	private static final byte FLAG_HAS_REDIRECT = 8;
	private static final byte FLAG_HAS_SUGGESTION = 16;

	private static final byte FLAG_HAS_MIN_VALUE = 1;
	private static final byte FLAG_HAS_MAX_VALUE = 2;

	// Different kinds of string types. Not used here; documented for posterity.
	private static final byte STRING_IS_WORD = 1;
	private static final byte STRING_IS_PHRASE = 2;
	private static final byte STRING_IS_GREEDY = 3;

	// Flags for different kinds of entities. Not used here; documented for posterity.
	private static final byte FLAG_ENTITY_AMOUNT_IS_SINGLE = 1;
	private static final byte FLAG_ENTITY_TYPE_IS_PLAYER = 2;

	// PE argument types that we use. These do not match values used by Nukkit, since they did
	// not work for us. The reason for this is unclear. Many different values produced seemingly
	// the same data type in the PE client chat GUI. Documented here for posterity:
	// 8-13 target (with wildcard)
	// 14-17 file path
	// 18-26 "unknown"
	// 27-28 string
	// 29-31 position ("x y z")
	// 32-33 message
	// 34-36 text
	// 37-43 json
	// 44-... ? (tested up to 99) command
	public static final int ARG_TYPE_INT = 1;
	public static final int ARG_TYPE_FLOAT = 2;
	public static final int ARG_TYPE_TARGET = 6;
	public static final int ARG_TYPE_STRING = 27;
	public static final int ARG_TYPE_POSITION = 29;
	public static final int ARG_TYPE_MESSAGE = 32;
	// Not sure if we will need these..?
	public static final int ARG_TYPE_RAWTEXT = 34;
	public static final int ARG_TYPE_JSON = 37;
	public static final int ARG_TYPE_COMMAND = 44;

	public static final int ARG_FLAG_VALID = 0x100000;
	public static final int ARG_FLAG_LITERAL = 0x200000;

	private CommandNode[] allNodes;
	private CommandNode[] topLevelNodes; // A subset of allNodes containing the starting "command" nodes
	private int rootNodeIndex;

	private static class CommandNode {
		private final String name;
		private final String argType;
		private final String suggestion;
		private final int[] children;
		private final int redirect;
		private final boolean isPathEnd;

		public CommandNode(String name, String argType, String suggestion, int[] children, int redirect, boolean isPathEnd) {
			this.name = name;
			this.argType = argType;
			this.suggestion = suggestion;
			this.children = children;
			this.redirect = redirect;
			this.isPathEnd = isPathEnd;
		}

		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}

		public boolean isLeaf() {
			return this.getChildren().length == 0;
		}

		public String getName() {
			return name;
		}

		public String getArgType() {
			return argType;
		}

		public int[] getChildren() {
			return children;
		}
	}

	private static class PEArgumentNode {
		private final String name;
		private final String argType;
		private final int nameIndex;

		public PEArgumentNode(CommandNode node, PECommandsStructure peStruct) {
			this.name = node.getName();
			this.argType = node.getArgType();

			// Cache literal index, if this is a literal argument
			if (this.argType == null) {
				this.nameIndex = peStruct.registerLiteral(this.name);
			} else {
				this.nameIndex = -1;
			}
		}

		public String getName() {
			return name;
		}

		public String getArgType() {
			return argType;
		}

		public int getNameIndex() {
			return nameIndex;
		}
	}

	private static class PECommandsStructure {
		private Map<CommandNode, List<List<PEArgumentNode>>> overloadsRegistry = new HashMap<>();
		private Map<String, Integer> literalRegistry = new HashMap<>();
		private String[] literalArray;

		/**
		 * Register a list of overloads for a top-level node.
		 */
		public void registerOverloads(CommandNode node, List<List<PEArgumentNode>> overloads) {
			overloadsRegistry.put(node, overloads);
		}

		public List<List<PEArgumentNode>> getOverloads(CommandNode node) {
			return overloadsRegistry.get(node);
		}

		/**
		 * Register a literal in the internal literal array if it was new, and return it's index value.
		 */
		public int registerLiteral(String literal) {
			if (literalArray != null) {
				throw new IllegalStateException("Cannot register literals after getLiteralArray() is called");
			}

			Integer index = literalRegistry.get(literal);
			if (index == null) {
				index = literalRegistry.size();
				literalRegistry.put(literal, index);
			}
			return index;
		}

		/**
		 * Return an array of all registered literals. All strings in the array are unique.
		 */
		public String[] getLiteralArray() {
			// Not thread safe...
			if (literalArray == null) {
				// Convert literalRegistry to proper array using assigned indices
				literalArray = new String[literalRegistry.size()];
				for (Map.Entry<String, Integer> entry : literalRegistry.entrySet()) {
					literalArray[entry.getValue()] = entry.getKey();
				}
			}

			return literalArray;
		}
	}

	public DeclareCommands(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromServerData(ByteBuf from) {
		// In theory, we could read this in the superclass. However, right now only PE needs this data, so save us
		// the trouble of parsing it for everyone else by doing it here.
		int length = VarNumberSerializer.readVarInt(from);

		allNodes = new CommandNode[length];
		for (int i = 0; i < length; i++) {
			CommandNode node = readCommandNode(from);
			allNodes[i] = node;
		}

		rootNodeIndex = VarNumberSerializer.readVarInt(from);

		// For convenience, store all top-level nodes in a separate array. (These correspond to the
		// actual "commands", i.e. the first literal, like "weather").
		int[] topLevelNodeIndices = allNodes[rootNodeIndex].getChildren();
		int i = 0;
		topLevelNodes = new CommandNode[topLevelNodeIndices.length];
		for (int index : topLevelNodeIndices) {
			topLevelNodes[i++] = allNodes[index];
		}
	}

	private CommandNode readCommandNode(ByteBuf from) {
		byte flags = from.readByte();
		boolean isPathEnd;
		int redirect;
		int[] children = ArraySerializer.readVarIntVarIntArray(from).clone();
		if ((flags & FLAG_HAS_REDIRECT) != 0) {
			redirect = VarNumberSerializer.readVarInt(from);
		} else {
			redirect = -1;
		}
		String name;
		String argType;
		String suggestion;

		isPathEnd = ((flags & FLAG_IS_COMMAND_PATH_END) != 0);

		if ((flags & FLAG_IS_LITERAL) != 0) {
			name = StringSerializer.readVarIntUTF8String(from);
			argType = null; // no argType signals this is a literal
			suggestion = null;
		} else if ((flags & FLAG_IS_ARGUMENT) != 0) {
			name = StringSerializer.readVarIntUTF8String(from);
			// This is slightly lossy; some arg types has extra information that we throw away
			// To properly encode that, we'd need a specialized class, not a string.
			argType = readArgType(from);

			if ((flags & FLAG_HAS_SUGGESTION) != 0) {
				suggestion = StringSerializer.readVarIntUTF8String(from);
			} else {
				suggestion = null;
			}
		} else {
			// This is only allowed for the root node
			name = null;
			argType = null;
			suggestion = null;
		}
		return new CommandNode(name, argType, suggestion, children, redirect, isPathEnd);
	}

	private String readArgType(ByteBuf from) {
		String argType = StringSerializer.readVarIntUTF8String(from);
		// Depending on argType, there might be additional data.
		// At this point, we're just throwing this away, but we need at least
		// skip over it in the buffer.

		if (argType.equals("brigadier:string")) {
			// Determine kind of string, any of STRING_IS_*...
			int stringType = VarNumberSerializer.readVarInt(from);
		} else if (argType.equals("brigadier:integer")) {
			byte flag = from.readByte();
			if ((flag & FLAG_HAS_MIN_VALUE) != 0) {
				int min = from.readInt();
			}
			if ((flag & FLAG_HAS_MAX_VALUE) != 0) {
				int max = from.readInt();
			}
		} else if (argType.equals("brigadier:float")) {
			byte flag = from.readByte();
			if ((flag & FLAG_HAS_MIN_VALUE) != 0) {
				float min = from.readFloat();
			}
			if ((flag & FLAG_HAS_MAX_VALUE) != 0) {
				float max = from.readFloat();
			}
		} else if (argType.equals("brigadier:double")) {
			byte flag = from.readByte();
			if ((flag & FLAG_HAS_MIN_VALUE) != 0) {
				double min = from.readDouble();
			}
			if ((flag & FLAG_HAS_MAX_VALUE) != 0) {
				double max = from.readDouble();
			}
		} else if (argType.equals("minecraft:entity")) {
			// The flag determines the amount (single or double) and type (players or entities)
			// See FLAG_ENTITY_AMOUNT_IS_SINGLE and FLAG_ENTITY_TYPE_IS_PLAYER.
			byte flag = from.readByte();
		} else if (argType.equals("minecraft:score_holder")) {
			// The "multiple" boolean is true if multiple, false if single.
			byte multiple = from.readByte();
		} else {
			// For all other types, there are no additional data. This might change in future versions of Minecraft.
		}
		return argType;
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		PECommandsStructure struct = buildPEStructure();

		return RecyclableSingletonList.create(create(struct));
	}

	private PECommandsStructure buildPEStructure() {
		PECommandsStructure peStruct = new PECommandsStructure();

		// For every command, we need to create a set of overloads, i.e. the different
		// ways the command can be called, with a list of argument nodes for each alternative.
		for (CommandNode node : topLevelNodes) {
			List<List<PEArgumentNode>> overloads = new ArrayList<>();
			walkNode(peStruct, overloads, node, new ArrayList<>());
			peStruct.registerOverloads(node, overloads);
		}

		return peStruct;
	}

	private void walkNode(PECommandsStructure peStruct, List<List<PEArgumentNode>> overloads, CommandNode currentNode, List<CommandNode> nodePath) {
		if (currentNode.isLeaf()) {
			List<PEArgumentNode> argumentList = new ArrayList<>();
			for (CommandNode node : nodePath) {
				PEArgumentNode peNode = new PEArgumentNode(node, peStruct);
				argumentList.add(peNode);
			}
			overloads.add(argumentList);
		} else {
			for (int childNodeIndex : currentNode.getChildren()) {
				CommandNode childNode = allNodes[childNodeIndex];
				List<CommandNode> newPath = new ArrayList<>(nodePath);
				newPath.add(childNode);
				walkNode(peStruct, overloads, childNode, newPath);
			}
		}
	}

	public ClientBoundPacketData create(PECommandsStructure peStruct) {
		ClientBoundPacketData serializer = ClientBoundPacketData.create(PEPacketIDs.AVAILABLE_COMMANDS);

		// Write all literals (a way to number strings)
		writeLiterals(serializer, peStruct.getLiteralArray());

		// Write "postfixes". Start with the count. We don't have any, so ignore the structure.
		VarNumberSerializer.writeVarInt(serializer, 0);

		// Write literal groups, a way to group the literals that can be referred to from
		// argument nodes.
		// We have a 1-to-1 match between literals and literal groups.
		writeLiteralGroups(serializer, peStruct.getLiteralArray());

		// Now process the actual commands. Write on per top-level ("command") node.
		VarNumberSerializer.writeVarInt(serializer, topLevelNodes.length);
		for (CommandNode node : topLevelNodes) {
			StringSerializer.writeVarIntUTF8String(serializer, node.getName());
			// PC does not have any description, so just send an empty string
			StringSerializer.writeVarIntUTF8String(serializer, "");
			serializer.writeByte(0); // Flags? Always 0.
			serializer.writeByte(0);  // Permissions? Always 0.

			// The literal group index that contain our alias list, or -1 if none.
			serializer.writeIntLE(-1);

			// Write out all "overloads", i.e. all different ways to call this command with arguments.
			List<List<PEArgumentNode>> overloads = peStruct.getOverloads(node);

			// First write number of overloads for this command
			VarNumberSerializer.writeVarInt(serializer, overloads.size());
			for (List<PEArgumentNode> overload : overloads) {
				// For each overload, write out all arguments in order
				VarNumberSerializer.writeVarInt(serializer, overload.size());
				for (PEArgumentNode argumentNode : overload) {
					writeArgumentNode(serializer, argumentNode);
				}
			}
		}

		// Write "soft enums". Start with the count. We don't have any, so ignore the structure.
		VarNumberSerializer.writeVarInt(serializer, 0);

		return serializer;
	}

	private void writeLiterals(ClientBoundPacketData serializer, String[] literals) {
		// First write the size
		VarNumberSerializer.writeVarInt(serializer, literals.length);
		// Then one string per index
		for (String s : literals) {
			StringSerializer.writeVarIntUTF8String(serializer, s);
		}
	}

	private void writeLiteralGroups(ClientBoundPacketData serializer, String[] literals) {
		// We create a literal group with a single member per literal, ordered so each group has
		// has the same index as the corresponding literal.

		// First write number of literal groups
		VarNumberSerializer.writeVarInt(serializer, literals.length);
		for (int i = 0; i < literals.length; i++) {
			// Literal groups have a "name". It is never used, but needs to be unique.
			StringSerializer.writeVarIntUTF8String(serializer, "e" + i);
			// Number of literals in group, always just 1.
			VarNumberSerializer.writeVarInt(serializer, 1);
			writeSingleLiteral(serializer, i, literals.length);
		}
	}

	private void writeSingleLiteral(ClientBoundPacketData serializer, int value, int maxValue) {
		// Serialize literal index by using minimal data type
		if (maxValue < 256) {
			serializer.writeByte(value);
		} else if (maxValue < 65536) {
			serializer.writeShortLE(value);
		} else {
			serializer.writeIntLE(value);
		}
	}

	private void writeArgumentNode(ClientBoundPacketData serializer, PEArgumentNode argumentNode) {
		int flag;

		if (argumentNode.getArgType() != null) {
			// This is a variable; write the name and it's type
			StringSerializer.writeVarIntUTF8String(serializer, argumentNode.getName());
			flag = getPEArgTypeCode(argumentNode.getArgType()) | ARG_FLAG_VALID;
		} else {
			// This is a literal argument

			// The literal arguments also has a "name", but it's not used, so leave it empty.
			// (The actual value shown in the GUI is from the literal group)
			StringSerializer.writeVarIntUTF8String(serializer, "");

			// In theory, this is the index of a literal group, but we have the same index
			// in our literal array so we can use that without conversion.
			int index = argumentNode.getNameIndex();
			flag = index | ARG_FLAG_VALID | ARG_FLAG_LITERAL;
		}

		// The "flag" design is really... odd. Bedrock Edition engineers. Don't ask.
		serializer.writeIntLE(flag);
		serializer.writeByte(0); // Boolean IS_OPTIONAL (1 = true). For now, call everything compulsory.
		serializer.writeByte(0); // Flags? Always 0.
	}

	private int getPEArgTypeCode(String pcArgType) {
		if (pcArgType.equals("brigadier:bool")) {
			return ARG_TYPE_STRING;
		} else if (pcArgType.equals("brigadier:float")) {
			return ARG_TYPE_FLOAT;
		} else if (pcArgType.equals("brigadier:double")) {
			return ARG_TYPE_FLOAT;
		} else if (pcArgType.equals("brigadier:integer")) {
			return ARG_TYPE_INT;
		} else if (pcArgType.equals("brigadier:string")) {
			return ARG_TYPE_STRING;
		} else if (pcArgType.equals("minecraft:int_range")) {
			return ARG_TYPE_INT;
		} else if (pcArgType.equals("minecraft:float_range")) {
			return ARG_TYPE_FLOAT;
		} else if (pcArgType.equals("minecraft:vec3")) {
			return ARG_TYPE_POSITION;
		} else if (pcArgType.equals("minecraft:entity")) {
			return ARG_TYPE_TARGET;
		} else if (pcArgType.equals("minecraft:message")) {
			return ARG_TYPE_MESSAGE;
		} else {
			// We have a specialized type in PC which has no correspondance in PE. Sucks!
			// Tried ARG_TYPE_RAWTEXT before, but that "swallows" everything to the end of the line
			return ARG_TYPE_STRING;
		}
	}
}
