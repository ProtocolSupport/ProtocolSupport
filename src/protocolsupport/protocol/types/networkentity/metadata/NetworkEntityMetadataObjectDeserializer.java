package protocolsupport.protocol.types.networkentity.metadata;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.types.BlockDirection;
import protocolsupport.protocol.types.Vector4f;
import protocolsupport.protocol.types.networkentity.data.EntityPose;
import protocolsupport.protocol.types.networkentity.data.VillagerData;
import protocolsupport.protocol.types.networkentity.data.VillagerProfession;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBlockData;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBoolean;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectCatVariant;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectChat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectDirection;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectEntityPose;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectFloat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectFrogVariant;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectItemStack;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectNBT;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalBlockData;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalChat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalPosition;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalUUID;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalVarInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalWorldPosition;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectPaintingVariant;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectParticle;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectPosition;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectRotation;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectSnifferState;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectString;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarLong;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVector3f;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVector4f;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVillagerData;
import protocolsupport.protocol.types.particle.NetworkParticle;
import protocolsupport.protocol.types.particle.NetworkParticleRegistry;

public interface NetworkEntityMetadataObjectDeserializer<T extends NetworkEntityMetadataObject<?>> {

	public T read(ByteBuf buffer);

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectByte> BYTE = buffer -> new NetworkEntityMetadataObjectByte(buffer.readByte());

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectVarInt> VARINT = buffer -> new NetworkEntityMetadataObjectVarInt(VarNumberCodec.readVarInt(buffer));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectVarLong> VARLONG = buffer -> new NetworkEntityMetadataObjectVarLong(VarNumberCodec.readVarLong(buffer));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectFloat> FLOAT = buffer -> new NetworkEntityMetadataObjectFloat(buffer.readFloat());

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectString> STRING = buffer -> new NetworkEntityMetadataObjectString(StringCodec.readVarIntUTF8String(buffer));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectChat> CHAT = buffer -> new NetworkEntityMetadataObjectChat(ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(buffer), true));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectOptionalChat> OPT_CHAT = buffer -> buffer.readBoolean() ? new NetworkEntityMetadataObjectOptionalChat(ChatAPI.fromJSON(StringCodec.readVarIntUTF8String(buffer), true)) : new NetworkEntityMetadataObjectOptionalChat();

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectItemStack> ITEMSTACK = buffer -> new NetworkEntityMetadataObjectItemStack(ItemStackCodec.readItemStack(buffer));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectBoolean> BOOLEAN = buffer -> new NetworkEntityMetadataObjectBoolean(buffer.readBoolean());

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectRotation> ROTATION = buffer -> new NetworkEntityMetadataObjectRotation(new Vector(buffer.readFloat(), buffer.readFloat(), buffer.readFloat()));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectVector3f> VECTOR3F = buffer -> new NetworkEntityMetadataObjectVector3f(new Vector(buffer.readFloat(), buffer.readFloat(), buffer.readFloat()));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectPosition> POSITION = buffer -> new NetworkEntityMetadataObjectPosition(PositionCodec.readPosition(buffer));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectOptionalPosition> OPT_POSITION = buffer -> new NetworkEntityMetadataObjectOptionalPosition(OptionalCodec.readOptional(buffer, PositionCodec::readPosition));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectDirection> DIRECTION = buffer -> new NetworkEntityMetadataObjectDirection(MiscDataCodec.readVarIntEnum(buffer, BlockDirection.CONSTANT_LOOKUP));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectOptionalUUID> OPT_UUID = buffer -> new NetworkEntityMetadataObjectOptionalUUID(OptionalCodec.readOptional(buffer, UUIDCodec::readUUID));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectBlockData> BLOCKDATA = buffer -> new NetworkEntityMetadataObjectBlockData(VarNumberCodec.readVarInt(buffer));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectOptionalBlockData> OPT_BLOCKDATA = buffer -> new NetworkEntityMetadataObjectOptionalBlockData(VarNumberCodec.readVarInt(buffer));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectNBT> NBT = buffer -> new NetworkEntityMetadataObjectNBT(ItemStackCodec.readDirectTag(buffer));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectParticle> PARTICLE = buffer -> {
		NetworkParticle particle = NetworkParticleRegistry.fromId(VarNumberCodec.readVarInt(buffer));
		particle.readData(buffer);
		return new NetworkEntityMetadataObjectParticle(particle);
	};

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectVillagerData> VILLAGER_DATA = buffer -> new NetworkEntityMetadataObjectVillagerData(new VillagerData(
		VarNumberCodec.readVarInt(buffer),
		VillagerProfession.CONSTANT_LOOKUP.getByOrdinalOrDefault(VarNumberCodec.readVarInt(buffer), VillagerProfession.NONE),
		VarNumberCodec.readVarInt(buffer)
	));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectOptionalVarInt> OPT_VARINT = buffer -> {
		int value = VarNumberCodec.readVarInt(buffer);
		return value > 0 ? new NetworkEntityMetadataObjectOptionalVarInt(value - 1) : new NetworkEntityMetadataObjectOptionalVarInt();
	};

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectEntityPose> ENTITY_POSE = buffer -> new NetworkEntityMetadataObjectEntityPose(MiscDataCodec.readByteEnum(buffer, EntityPose.CONSTANT_LOOKUP));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectCatVariant> CAT_VARIANT = buffer -> new NetworkEntityMetadataObjectCatVariant(VarNumberCodec.readVarInt(buffer));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectFrogVariant> FROG_VARIANT = buffer -> new NetworkEntityMetadataObjectFrogVariant(VarNumberCodec.readVarInt(buffer));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectOptionalWorldPosition> OPT_WORLD_POSITION = buffer -> new NetworkEntityMetadataObjectOptionalWorldPosition(OptionalCodec.readOptional(buffer, PositionCodec::readWorldPosition));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectPaintingVariant> PAINTING_VARIANT = buffer -> new NetworkEntityMetadataObjectPaintingVariant(VarNumberCodec.readVarInt(buffer));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectSnifferState> SNIFFER_STATE = buffer -> new NetworkEntityMetadataObjectSnifferState(VarNumberCodec.readVarInt(buffer));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectVector4f> VECTOR4F = buffer -> new NetworkEntityMetadataObjectVector4f(new Vector4f(buffer.readFloat(), buffer.readFloat(), buffer.readFloat(), buffer.readFloat()));

}
