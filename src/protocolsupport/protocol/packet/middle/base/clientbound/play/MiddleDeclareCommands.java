package protocolsupport.protocol.packet.middle.base.clientbound.play;

import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.types.command.CommandNode;
import protocolsupport.protocol.types.command.CommandNodeArgumentDoubleProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentEntityProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentFloatProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentIntegerProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentLongProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentResourceKeyProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentResourceOrTagKeyProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentResourceOrTagProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentResourceProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentScoreHolderProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentStringProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentTimeProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentType;
import protocolsupport.utils.BitUtils;

public abstract class MiddleDeclareCommands extends ClientBoundMiddlePacket {

	protected MiddleDeclareCommands(IMiddlePacketInit init) {
		super(init);
	}

	protected CommandNode[] nodes;
	protected int rootNodeIndex;

	@Override
	protected void decode(ByteBuf serverdata) {
		nodes = ArrayCodec.readVarIntTArray(serverdata, CommandNode.class, MiddleDeclareCommands::readNode);
		rootNodeIndex = VarNumberCodec.readVarInt(serverdata);
	}

	protected static final int NODE_FLAGS_TYPE_MASK = BitUtils.createIBitMaskFromBits(new int[] {0, 1});
	protected static final int NODE_FLAGS_HAS_REDIRECT_BIT = 3;
	protected static final int NODE_FLAGS_HAS_SUGGESTIONS_TYPE = 4;

	protected static final int NODE_TYPE_ROOT = 0;
	protected static final int NODE_TYPE_LITERAL = 1;
	protected static final int NODE_TYPE_ARGUMENT = 2;

	protected static final Map<CommandNodeArgumentType, Function<ByteBuf, CommandNodeArgumentProperties>> argumentPropertiesDeserializer = new EnumMap<>(CommandNodeArgumentType.class);
	static {
		argumentPropertiesDeserializer.put(CommandNodeArgumentType.DOUBLE, data -> {
			int flags = data.readByte();
			double min = BitUtils.isIBitSet(flags, 0) ? data.readDouble() : Double.MIN_VALUE;
			double max = BitUtils.isIBitSet(flags, 1) ? data.readDouble() : Double.MAX_VALUE;
			return new CommandNodeArgumentDoubleProperties(flags, min, max);
		});
		argumentPropertiesDeserializer.put(CommandNodeArgumentType.FLOAT, data -> {
			int flags = data.readByte();
			float min = BitUtils.isIBitSet(flags, 0) ? data.readFloat() : Float.MIN_VALUE;
			float max = BitUtils.isIBitSet(flags, 1) ? data.readFloat() : Float.MAX_VALUE;
			return new CommandNodeArgumentFloatProperties(flags, min, max);
		});
		argumentPropertiesDeserializer.put(CommandNodeArgumentType.LONG, data -> {
			int flags = data.readByte();
			long min = BitUtils.isIBitSet(flags, 0) ? data.readLong() : Long.MIN_VALUE;
			long max = BitUtils.isIBitSet(flags, 1) ? data.readLong() : Long.MAX_VALUE;
			return new CommandNodeArgumentLongProperties(flags, min, max);
		});
		argumentPropertiesDeserializer.put(CommandNodeArgumentType.INTEGER, data -> {
			int flags = data.readByte();
			int min = BitUtils.isIBitSet(flags, 0) ? data.readInt() : Integer.MIN_VALUE;
			int max = BitUtils.isIBitSet(flags, 1) ? data.readInt() : Integer.MAX_VALUE;
			return new CommandNodeArgumentIntegerProperties(flags, min, max);
		});
		argumentPropertiesDeserializer.put(CommandNodeArgumentType.STRING, data -> {
			CommandNodeArgumentStringProperties.StringType type = MiscDataCodec.readVarIntEnum(data, CommandNodeArgumentStringProperties.StringType.CONSTANT_LOOKUP);
			return new CommandNodeArgumentStringProperties(type);
		});
		argumentPropertiesDeserializer.put(CommandNodeArgumentType.ENTITY, data -> {
			int flags = data.readByte();
			return new CommandNodeArgumentEntityProperties(flags);
		});
		argumentPropertiesDeserializer.put(CommandNodeArgumentType.SCORE_HOLDER, data -> {
			int flags = data.readByte();
			return new CommandNodeArgumentScoreHolderProperties(flags);
		});
		argumentPropertiesDeserializer.put(CommandNodeArgumentType.TIME, data -> {
			int min = data.readInt();
			return new CommandNodeArgumentTimeProperties(min);
		});
		argumentPropertiesDeserializer.put(CommandNodeArgumentType.RESOURCE_OR_TAG, data -> {
			String identifier = StringCodec.readVarIntUTF8String(data);
			return new CommandNodeArgumentResourceOrTagProperties(identifier);
		});
		argumentPropertiesDeserializer.put(CommandNodeArgumentType.RESOURCE_OR_TAG_KEY, data -> {
			String identifier = StringCodec.readVarIntUTF8String(data);
			return new CommandNodeArgumentResourceOrTagKeyProperties(identifier);
		});
		argumentPropertiesDeserializer.put(CommandNodeArgumentType.RESOURCE, data -> {
			String identifier = StringCodec.readVarIntUTF8String(data);
			return new CommandNodeArgumentResourceProperties(identifier);
		});
		argumentPropertiesDeserializer.put(CommandNodeArgumentType.RESOURCE_KEY, data -> {
			String identifier = StringCodec.readVarIntUTF8String(data);
			return new CommandNodeArgumentResourceKeyProperties(identifier);
		});
	}

	protected static CommandNode readNode(ByteBuf data) {
		byte flags = data.readByte();
		int nodeType = flags & NODE_FLAGS_TYPE_MASK;
		int[] childNodesIndexes = ArrayCodec.readVarIntVarIntArray(data);
		int redirectNodeIndex = BitUtils.isIBitSet(flags, NODE_FLAGS_HAS_REDIRECT_BIT) ? VarNumberCodec.readVarInt(data) : -1;
		String name = (nodeType == NODE_TYPE_LITERAL) || (nodeType == NODE_TYPE_ARGUMENT) ? StringCodec.readVarIntUTF8String(data) : null;
		CommandNodeArgumentType argumentType = nodeType == NODE_TYPE_ARGUMENT ? CommandNodeArgumentType.CONSTANT_LOOKUP.getByOrdinalOrDefault(VarNumberCodec.readVarInt(data), CommandNodeArgumentType.STRING) : null;
		CommandNodeArgumentProperties argumentProperties = null;
		if (argumentType != null) {
			Function<ByteBuf, CommandNodeArgumentProperties> propertiesFunc = argumentPropertiesDeserializer.get(argumentType);
			if (propertiesFunc != null) {
				argumentProperties = propertiesFunc.apply(data);
				if (argumentType != argumentProperties.getType()) {
					throw new IllegalStateException(MessageFormat.format("Command argument type missmatch, expected {0}, returend {1}", argumentType, argumentProperties.getType()));
				}
			} else {
				argumentProperties = new CommandNodeArgumentProperties(argumentType);
			}
		}
		String suggestionsType = BitUtils.isIBitSet(flags, NODE_FLAGS_HAS_SUGGESTIONS_TYPE) ? StringCodec.readVarIntUTF8String(data) : null;
		return new CommandNode(flags, childNodesIndexes, redirectNodeIndex, name, argumentProperties, suggestionsType);
	}

}
