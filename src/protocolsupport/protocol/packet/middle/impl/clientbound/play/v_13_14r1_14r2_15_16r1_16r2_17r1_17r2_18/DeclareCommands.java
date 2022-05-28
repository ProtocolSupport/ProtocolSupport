package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_13_14r1_14r2_15_16r1_16r2_17r1_17r2_18;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.ArrayCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleDeclareCommands;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV13;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV14r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV15;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV16r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;
import protocolsupport.protocol.typeremapper.legacy.LegacyCommandDataRegistry;
import protocolsupport.protocol.typeremapper.legacy.LegacyCommandDataRegistry.LegacyCommandDataMappingTable;
import protocolsupport.protocol.types.command.CommandNodeDoubleProperties;
import protocolsupport.protocol.types.command.CommandNodeEntityProperties;
import protocolsupport.protocol.types.command.CommandNodeFloatProperties;
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

public class DeclareCommands extends MiddleDeclareCommands implements
IClientboundMiddlePacketV13,
IClientboundMiddlePacketV14r1,
IClientboundMiddlePacketV14r2,
IClientboundMiddlePacketV15,
IClientboundMiddlePacketV16r1,
IClientboundMiddlePacketV16r2,
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2,
IClientboundMiddlePacketV18 {

	public DeclareCommands(IMiddlePacketInit init) {
		super(init);
	}

	protected final LegacyCommandDataMappingTable commanddataTabe = LegacyCommandDataRegistry.INSTANCE.getTable(version);

	@Override
	protected void write() {
		ClientBoundPacketData declarecommands = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_DECLARE_COMMANDS);
		ArrayCodec.writeVarIntTArray(declarecommands, nodes, (nodeData, node) -> {
			int flags = node.getFlags();
			int nodeType = flags & NODE_FLAGS_TYPE_MASK;
			nodeData.writeByte(flags);
			ArrayCodec.writeVarIntVarIntArray(nodeData, node.getChildNodesIndexes());
			if (BitUtils.isIBitSet(flags, NODE_FLAGS_HAS_REDIRECT_BIT)) {
				VarNumberCodec.writeVarInt(nodeData, node.getRedirectNodeIndex());
			}
			if (nodeType == NODE_TYPE_LITERAL || nodeType == NODE_TYPE_ARGUMENT) {
				StringCodec.writeVarIntUTF8String(nodeData, node.getName());
			}
			if (nodeType == NODE_TYPE_ARGUMENT) {
				CommandNodeProperties properties = node.getProperties();
				properties = commanddataTabe.<CommandNodeProperties>get(properties.getClass()).apply(properties);
				propertiesSerializer.get(properties.getClass()).accept(nodeData, properties);
			}
			if (BitUtils.isIBitSet(flags, NODE_FLAGS_HAS_SUGGESTIONS_TYPE)) {
				StringCodec.writeVarIntUTF8String(nodeData, node.getSuggestType());
			}
		});
		VarNumberCodec.writeVarInt(declarecommands, rootNodeIndex);
		io.writeClientbound(declarecommands);
	}

	protected static final Map<Class<CommandNodeProperties>, BiConsumer<ByteBuf, CommandNodeProperties>> propertiesSerializer = new HashMap<>();
	@SuppressWarnings("unchecked")
	protected static <T extends CommandNodeProperties> void registerPropertiesSerializer(Class<T> clazz, BiConsumer<ByteBuf, T> serializer) {
		propertiesSerializer.put((Class<CommandNodeProperties>) clazz, (BiConsumer<ByteBuf, CommandNodeProperties>) serializer);
	}
	static {
		registerPropertiesSerializer(CommandNodeSimpleProperties.class, (data, properties) -> {
			StringCodec.writeVarIntUTF8String(data, properties.getParser());
		});
		registerPropertiesSerializer(CommandNodeDoubleProperties.class, (data, properties) -> {
			int flags = properties.getFlags();
			StringCodec.writeVarIntUTF8String(data, "brigadier:double");
			data.writeByte(flags);
			if (BitUtils.isIBitSet(flags, 0)) {
				data.writeDouble(properties.getMin());
			}
			if (BitUtils.isIBitSet(flags, 1)) {
				data.writeDouble(properties.getMax());
			}
		});
		registerPropertiesSerializer(CommandNodeFloatProperties.class, (data, properties) -> {
			int flags = properties.getFlags();
			StringCodec.writeVarIntUTF8String(data, "brigadier:float");
			data.writeByte(flags);
			if (BitUtils.isIBitSet(flags, 0)) {
				data.writeFloat(properties.getMin());
			}
			if (BitUtils.isIBitSet(flags, 1)) {
				data.writeFloat(properties.getMax());
			}
		});
		registerPropertiesSerializer(CommandNodeLongProperties.class, (data, properties) -> {
			int flags = properties.getFlags();
			StringCodec.writeVarIntUTF8String(data, "brigadier:long");
			data.writeByte(flags);
			if (BitUtils.isIBitSet(flags, 0)) {
				data.writeLong(properties.getMin());
			}
			if (BitUtils.isIBitSet(flags, 1)) {
				data.writeLong(properties.getMax());
			}
		});
		registerPropertiesSerializer(CommandNodeIntegerProperties.class, (data, properties) -> {
			int flags = properties.getFlags();
			StringCodec.writeVarIntUTF8String(data, "brigadier:integer");
			data.writeByte(flags);
			if (BitUtils.isIBitSet(flags, 0)) {
				data.writeInt(properties.getMin());
			}
			if (BitUtils.isIBitSet(flags, 1)) {
				data.writeInt(properties.getMax());
			}
		});
		registerPropertiesSerializer(CommandNodeStringProperties.class, (data, properties) -> {
			StringCodec.writeVarIntUTF8String(data, "brigadier:string");
			MiscDataCodec.writeVarIntEnum(data, properties.getType());
		});
		registerPropertiesSerializer(CommandNodeEntityProperties.class, (data, properties) -> {
			StringCodec.writeVarIntUTF8String(data, "minecraft:entity");
			data.writeByte(properties.getFlags());
		});
		registerPropertiesSerializer(CommandNodeScoreHolderProperties.class, (data, properties) -> {
			StringCodec.writeVarIntUTF8String(data, "minecraft:score_holder");
			data.writeByte(properties.getFlags());
		});
		registerPropertiesSerializer(CommandNodeRangeProperties.class, (data, properties) -> {
			StringCodec.writeVarIntUTF8String(data, "minecraft:range");
			data.writeBoolean(properties.allowDecimals());
		});
		registerPropertiesSerializer(CommandNodeResourceOrTagProperties.class, (data, properties) -> {
			StringCodec.writeVarIntUTF8String(data, "minecraft:resource_or_tag");
			StringCodec.writeVarIntUTF8String(data, properties.getIdentifier());
		});
		registerPropertiesSerializer(CommandNodeResourceProperties.class, (data, properties) -> {
			StringCodec.writeVarIntUTF8String(data, "minecraft:resource");
			StringCodec.writeVarIntUTF8String(data, properties.getIdentifier());
		});
	}

}
