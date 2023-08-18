package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_20;

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
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV20;
import protocolsupport.protocol.typeremapper.legacy.LegacyCommandDataRegistry;
import protocolsupport.protocol.typeremapper.legacy.LegacyCommandDataRegistry.LegacyCommandDataMappingTable;
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
import protocolsupport.utils.BitUtils;

public class DeclareCommands extends MiddleDeclareCommands implements
IClientboundMiddlePacketV20 {

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
			if ((nodeType == NODE_TYPE_LITERAL) || (nodeType == NODE_TYPE_ARGUMENT)) {
				StringCodec.writeVarIntUTF8String(nodeData, node.getName());
			}
			if (nodeType == NODE_TYPE_ARGUMENT) {
				CommandNodeArgumentProperties argumentProperties = node.getArgumentProperties();
				argumentProperties = commanddataTabe.<CommandNodeArgumentProperties>get(argumentProperties.getType()).apply(argumentProperties);
				VarNumberCodec.writeVarInt(nodeData, argumentProperties.getType().ordinal()); //TODO: flattening
				BiConsumer<ByteBuf, CommandNodeArgumentProperties> serializer = argumentPropertiesSerializer.get(argumentProperties.getClass());
				if (serializer == null) {
					throw new IllegalArgumentException("Missing argument type " + argumentProperties.getType() + " properties serializer");
				}
				serializer.accept(nodeData, argumentProperties);
			}
			if (BitUtils.isIBitSet(flags, NODE_FLAGS_HAS_SUGGESTIONS_TYPE)) {
				StringCodec.writeVarIntUTF8String(nodeData, node.getSuggestType());
			}
		});
		VarNumberCodec.writeVarInt(declarecommands, rootNodeIndex);
		io.writeClientbound(declarecommands);
	}

	protected static final Map<Class<CommandNodeArgumentProperties>, BiConsumer<ByteBuf, CommandNodeArgumentProperties>> argumentPropertiesSerializer = new HashMap<>();
	@SuppressWarnings("unchecked")
	protected static <T extends CommandNodeArgumentProperties> void registerPropertiesSerializer(Class<T> clazz, BiConsumer<ByteBuf, T> serializer) {
		argumentPropertiesSerializer.put((Class<CommandNodeArgumentProperties>) clazz, (BiConsumer<ByteBuf, CommandNodeArgumentProperties>) serializer);
	}
	static {
		registerPropertiesSerializer(CommandNodeArgumentProperties.class, (data, properties) -> {});
		registerPropertiesSerializer(CommandNodeArgumentDoubleProperties.class, (data, properties) -> {
			int flags = properties.getFlags();
			data.writeByte(flags);
			if (BitUtils.isIBitSet(flags, 0)) {
				data.writeDouble(properties.getMin());
			}
			if (BitUtils.isIBitSet(flags, 1)) {
				data.writeDouble(properties.getMax());
			}
		});
		registerPropertiesSerializer(CommandNodeArgumentFloatProperties.class, (data, properties) -> {
			int flags = properties.getFlags();
			data.writeByte(flags);
			if (BitUtils.isIBitSet(flags, 0)) {
				data.writeFloat(properties.getMin());
			}
			if (BitUtils.isIBitSet(flags, 1)) {
				data.writeFloat(properties.getMax());
			}
		});
		registerPropertiesSerializer(CommandNodeArgumentLongProperties.class, (data, properties) -> {
			int flags = properties.getFlags();
			data.writeByte(flags);
			if (BitUtils.isIBitSet(flags, 0)) {
				data.writeLong(properties.getMin());
			}
			if (BitUtils.isIBitSet(flags, 1)) {
				data.writeLong(properties.getMax());
			}
		});
		registerPropertiesSerializer(CommandNodeArgumentIntegerProperties.class, (data, properties) -> {
			int flags = properties.getFlags();
			data.writeByte(flags);
			if (BitUtils.isIBitSet(flags, 0)) {
				data.writeInt(properties.getMin());
			}
			if (BitUtils.isIBitSet(flags, 1)) {
				data.writeInt(properties.getMax());
			}
		});
		registerPropertiesSerializer(CommandNodeArgumentStringProperties.class, (data, properties) -> {
			MiscDataCodec.writeVarIntEnum(data, properties.getStringType());
		});
		registerPropertiesSerializer(CommandNodeArgumentEntityProperties.class, (data, properties) -> {
			data.writeByte(properties.getFlags());
		});
		registerPropertiesSerializer(CommandNodeArgumentScoreHolderProperties.class, (data, properties) -> {
			data.writeByte(properties.getFlags());
		});
		registerPropertiesSerializer(CommandNodeArgumentTimeProperties.class, (data, properties) -> {
			data.writeInt(properties.getMin());
		});
		registerPropertiesSerializer(CommandNodeArgumentResourceOrTagProperties.class, (data, properties) -> {
			StringCodec.writeVarIntUTF8String(data, properties.getIdentifier());
		});
		registerPropertiesSerializer(CommandNodeArgumentResourceOrTagKeyProperties.class, (data, properties) -> {
			StringCodec.writeVarIntUTF8String(data, properties.getIdentifier());
		});
		registerPropertiesSerializer(CommandNodeArgumentResourceProperties.class, (data, properties) -> {
			StringCodec.writeVarIntUTF8String(data, properties.getIdentifier());
		});
		registerPropertiesSerializer(CommandNodeArgumentResourceKeyProperties.class, (data, properties) -> {
			StringCodec.writeVarIntUTF8String(data, properties.getIdentifier());
		});
	}

}
