package protocolsupport.protocol.packet.middle.base.clientbound.play;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;
import protocolsupport.protocol.types.command.CommandNode;
import protocolsupport.protocol.types.command.CommandNodeDoubleProperties;
import protocolsupport.protocol.types.command.CommandNodeEntityProperties;
import protocolsupport.protocol.types.command.CommandNodeIntegerProperties;
import protocolsupport.protocol.types.command.CommandNodeLongProperties;
import protocolsupport.protocol.types.command.CommandNodeProperties;
import protocolsupport.protocol.types.command.CommandNodeRangeProperties;
import protocolsupport.protocol.types.command.CommandNodeResourceOrTagProperties;
import protocolsupport.protocol.types.command.CommandNodeResourceProperties;
import protocolsupport.protocol.types.command.CommandNodeScoreHolderProperties;
import protocolsupport.protocol.types.command.CommandNodeSimpleProperties;
import protocolsupport.protocol.types.command.CommandNodeStringProperties;
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

	protected static final Map<String, Function<ByteBuf, CommandNodeProperties>> propertiesDeserializer = new HashMap<>();
	static {
		propertiesDeserializer.put("brigadier:double", data -> {
			int flags = data.readByte();
			double min = BitUtils.isIBitSet(flags, 0) ? data.readDouble() : Double.MIN_VALUE;
			double max = BitUtils.isIBitSet(flags, 1) ? data.readDouble() : Double.MAX_VALUE;
			return new CommandNodeDoubleProperties(flags, min, max);
		});
		propertiesDeserializer.put("brigadier:float", data -> {
			int flags = data.readByte();
			float min = BitUtils.isIBitSet(flags, 0) ? data.readFloat() : Float.MIN_VALUE;
			float max = BitUtils.isIBitSet(flags, 1) ? data.readFloat() : Float.MAX_VALUE;
			return new CommandNodeDoubleProperties(flags, min, max);
		});
		propertiesDeserializer.put("brigadier:long", data -> {
			int flags = data.readByte();
			long min = BitUtils.isIBitSet(flags, 0) ? data.readLong() : Long.MIN_VALUE;
			long max = BitUtils.isIBitSet(flags, 1) ? data.readLong() : Long.MAX_VALUE;
			return new CommandNodeLongProperties(flags, min, max);
		});
		propertiesDeserializer.put("brigadier:integer", data -> {
			int flags = data.readByte();
			int min = BitUtils.isIBitSet(flags, 0) ? data.readInt() : Integer.MIN_VALUE;
			int max = BitUtils.isIBitSet(flags, 1) ? data.readInt() : Integer.MAX_VALUE;
			return new CommandNodeIntegerProperties(flags, min, max);
		});
		propertiesDeserializer.put("brigadier:string", data -> {
			CommandNodeStringProperties.Type type = MiscDataCodec.readVarIntEnum(data, CommandNodeStringProperties.Type.CONSTANT_LOOKUP);
			return new CommandNodeStringProperties(type);
		});
		propertiesDeserializer.put("minecraft:entity", data -> {
			int flags = data.readByte();
			return new CommandNodeEntityProperties(flags);
		});
		propertiesDeserializer.put("minecraft:score_holder", data -> {
			int flags = data.readByte();
			return new CommandNodeScoreHolderProperties(flags);
		});
		propertiesDeserializer.put("minecraft:range", data -> {
			boolean allowDecimals = data.readBoolean();
			return new CommandNodeRangeProperties(allowDecimals);
		});
		propertiesDeserializer.put("minecraft:resource_or_tag", data -> {
			String identifier = StringCodec.readVarIntUTF8String(data);
			return new CommandNodeResourceOrTagProperties(identifier);
		});
		propertiesDeserializer.put("minecraft:resource", data -> {
			String identifier = StringCodec.readVarIntUTF8String(data);
			return new CommandNodeResourceProperties(identifier);
		});
	}

	protected static CommandNode readNode(ByteBuf data) {
		byte flags = data.readByte();
		int nodeType = flags & NODE_FLAGS_TYPE_MASK;
		int[] childNodesIndexes = ArrayCodec.readVarIntVarIntArray(data);
		int redirectNodeIndex = BitUtils.isIBitSet(flags, NODE_FLAGS_HAS_REDIRECT_BIT) ? VarNumberCodec.readVarInt(data) : -1;
		String name = (nodeType == NODE_TYPE_LITERAL) || (nodeType == NODE_TYPE_ARGUMENT) ? StringCodec.readVarIntUTF8String(data) : null;
		String parser = nodeType == NODE_TYPE_ARGUMENT ? StringCodec.readVarIntUTF8String(data) : null;
		CommandNodeProperties properties = null;
		if (parser != null) {
			Function<ByteBuf, CommandNodeProperties> propertiesFunc = propertiesDeserializer.get(parser);
			if (propertiesFunc != null) {
				properties = propertiesFunc.apply(data);
			} else {
				properties = new CommandNodeSimpleProperties(parser);
			}
		}
		String suggestionsType = BitUtils.isIBitSet(flags, NODE_FLAGS_HAS_SUGGESTIONS_TYPE) ? StringCodec.readVarIntUTF8String(data) : null;
		return new CommandNode(flags, childNodesIndexes, redirectNodeIndex, name, parser, properties, suggestionsType);
	}


}
