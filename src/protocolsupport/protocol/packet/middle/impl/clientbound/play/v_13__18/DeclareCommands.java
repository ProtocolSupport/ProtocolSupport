package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_13__18;

import java.util.EnumMap;
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
import protocolsupport.protocol.types.command.CommandNodeArgumentDoubleProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentEntityProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentFloatProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentIntegerProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentLongProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentResourceOrTagProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentResourceProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentScoreHolderProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentStringProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentTimeProperties;
import protocolsupport.protocol.types.command.CommandNodeArgumentType;
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
			if ((nodeType == NODE_TYPE_LITERAL) || (nodeType == NODE_TYPE_ARGUMENT)) {
				StringCodec.writeVarIntUTF8String(nodeData, node.getName());
			}
			if (nodeType == NODE_TYPE_ARGUMENT) {
				CommandNodeArgumentProperties argumentProperties = node.getArgumentProperties();
				argumentProperties = commanddataTabe.<CommandNodeArgumentProperties>get(argumentProperties.getType()).apply(argumentProperties);
				String legacyArgumentType = argumentTypeId.get(argumentProperties.getType());
				if (legacyArgumentType == null) {
					throw new IllegalArgumentException("Missing argument type " + argumentProperties.getType() + " legacy id");
				}
				StringCodec.writeVarIntUTF8String(nodeData, legacyArgumentType);
				BiConsumer<ByteBuf, CommandNodeArgumentProperties> serializer = argumentPropertiesSerializer.get(argumentProperties.getClass());
				if (serializer == null) {
					throw new IllegalArgumentException("Missing argument type " + argumentProperties.getType() + " properties serializer");
				}
				serializer.accept(nodeData, argumentProperties);
				if (BitUtils.isIBitSet(flags, NODE_FLAGS_HAS_SUGGESTIONS_TYPE)) {
					StringCodec.writeVarIntUTF8String(nodeData, node.getSuggestType());
				}
			}
		});
		VarNumberCodec.writeVarInt(declarecommands, rootNodeIndex);
		io.writeClientbound(declarecommands);
	}

	protected static final Map<CommandNodeArgumentType, String> argumentTypeId = new EnumMap<>(CommandNodeArgumentType.class);
	protected static final Map<Class<CommandNodeArgumentProperties>, BiConsumer<ByteBuf, CommandNodeArgumentProperties>> argumentPropertiesSerializer = new HashMap<>();
	@SuppressWarnings("unchecked")
	protected static <T extends CommandNodeArgumentProperties> void registerPropertiesSerializer(Class<T> clazz, BiConsumer<ByteBuf, T> serializer) {
		argumentPropertiesSerializer.put((Class<CommandNodeArgumentProperties>) clazz, (BiConsumer<ByteBuf, CommandNodeArgumentProperties>) serializer);
	}
	static {
		argumentTypeId.put(CommandNodeArgumentType.BOOLEAN, "brigadier:bool");
		argumentTypeId.put(CommandNodeArgumentType.FLOAT, "brigadier:float");
		argumentTypeId.put(CommandNodeArgumentType.DOUBLE, "brigadier:double");
		argumentTypeId.put(CommandNodeArgumentType.INTEGER, "brigadier:integer");
		argumentTypeId.put(CommandNodeArgumentType.LONG, "brigadier:long");
		argumentTypeId.put(CommandNodeArgumentType.STRING, "brigadier:string");
		argumentTypeId.put(CommandNodeArgumentType.ENTITY, "minecraft:entity");
		argumentTypeId.put(CommandNodeArgumentType.GAMEPROFILE, "minecraft:game_profile");
		argumentTypeId.put(CommandNodeArgumentType.BLOCK_POSITION, "minecraft:block_pos");
		argumentTypeId.put(CommandNodeArgumentType.COLUMN_POSITION, "minecraft:column_pos");
		argumentTypeId.put(CommandNodeArgumentType.VEC3D, "minecraft:vec3");
		argumentTypeId.put(CommandNodeArgumentType.VEC2D, "minecraft:vec2");
		argumentTypeId.put(CommandNodeArgumentType.BLOCKSTATE, "minecraft:block_state");
		argumentTypeId.put(CommandNodeArgumentType.BLOCK_PREDICATE, "minecraft:block_predicate");
		argumentTypeId.put(CommandNodeArgumentType.ITEMSTACK, "minecraft:item_stack");
		argumentTypeId.put(CommandNodeArgumentType.ITEM_PREDICATE, "minecraft:item_predicate");
		argumentTypeId.put(CommandNodeArgumentType.CHAT_COLOR, "minecraft:color");
		argumentTypeId.put(CommandNodeArgumentType.CHAT_COMPONENT, "minecraft:component");
		argumentTypeId.put(CommandNodeArgumentType.CHAT_MESSAGE, "minecraft:message");
		argumentTypeId.put(CommandNodeArgumentType.NBT, "minecraft:nbt");
		argumentTypeId.put(CommandNodeArgumentType.NBT_TAG, "minecraft:nbt_tag");
		argumentTypeId.put(CommandNodeArgumentType.NBT_PATH, "minecraft:nbt_path");
		argumentTypeId.put(CommandNodeArgumentType.OBJECTIVE, "minecraft:objective");
		argumentTypeId.put(CommandNodeArgumentType.OBJECTIVE_CRITERIA, "minecraft:objective_criteria");
		argumentTypeId.put(CommandNodeArgumentType.OPERATION, "minecraft:operation");
		argumentTypeId.put(CommandNodeArgumentType.PARTICLE, "minecraft:particle");
		argumentTypeId.put(CommandNodeArgumentType.ANGLE, "minecraft:angle");
		argumentTypeId.put(CommandNodeArgumentType.ROTATION, "minecraft:rotation");
		argumentTypeId.put(CommandNodeArgumentType.SCOREBOARD_SLOT, "minecraft:scoreboard_slot");
		argumentTypeId.put(CommandNodeArgumentType.SCORE_HOLDER, "minecraft:score_holder");
		argumentTypeId.put(CommandNodeArgumentType.SWIZZLE, "minecraft:swizzle");
		argumentTypeId.put(CommandNodeArgumentType.TEAM, "minecraft:team");
		argumentTypeId.put(CommandNodeArgumentType.SLOT, "minecraft:item_slot");
		argumentTypeId.put(CommandNodeArgumentType.RESOURCE_LOCATION, "minecraft:resource_location");
//		argumentTypeId.put(CommandNodeArgumentType.POTION_EFFECT, "minecraft:mob_effect");
		argumentTypeId.put(CommandNodeArgumentType.FUNCTION, "minecraft:function");
		argumentTypeId.put(CommandNodeArgumentType.ENTITY_ANCHOR, "minecraft:entity_anchor");
		argumentTypeId.put(CommandNodeArgumentType.RANGE_INT, "minecraft:int_range");
		argumentTypeId.put(CommandNodeArgumentType.RANGE_FLOAT, "minecraft:float_range");
//		argumentTypeId.put(CommandNodeArgumentType.ENCHANTMENT, "minecraft:item_enchantment");
//		argumentTypeId.put(CommandNodeArgumentType.ENTITY_SUMMON, "minecraft:entity_summon");
		argumentTypeId.put(CommandNodeArgumentType.DIMENSION, "minecraft:dimension");
		argumentTypeId.put(CommandNodeArgumentType.TIME, "minecraft:time");
		argumentTypeId.put(CommandNodeArgumentType.RESOURCE_OR_TAG, "minecraft:resource_or_tag");
		argumentTypeId.put(CommandNodeArgumentType.RESOURCE, "minecraft:resource");

		registerPropertiesSerializer(CommandNodeArgumentProperties.class, (data, properties) -> {});
		registerPropertiesSerializer(CommandNodeArgumentTimeProperties.class, (data, properties) -> {});
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
		registerPropertiesSerializer(CommandNodeArgumentResourceOrTagProperties.class, (data, properties) -> {
			StringCodec.writeVarIntUTF8String(data, properties.getIdentifier());
		});
		registerPropertiesSerializer(CommandNodeArgumentResourceProperties.class, (data, properties) -> {
			StringCodec.writeVarIntUTF8String(data, properties.getIdentifier());
		});
	}

}
