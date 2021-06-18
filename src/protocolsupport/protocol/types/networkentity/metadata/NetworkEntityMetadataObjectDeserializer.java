package protocolsupport.protocol.types.networkentity.metadata;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.chat.ChatAPI;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.UUIDSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.BlockDirection;
import protocolsupport.protocol.types.EntityPose;
import protocolsupport.protocol.types.VillagerData;
import protocolsupport.protocol.types.VillagerProfession;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBlockData;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBoolean;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectChat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectDirection;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectEntityPose;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectFloat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectItemStack;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectNBT;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalChat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalPosition;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalUUID;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalVarInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectParticle;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectPosition;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectString;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVector3f;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVillagerData;
import protocolsupport.protocol.types.particle.NetworkParticle;
import protocolsupport.protocol.types.particle.NetworkParticleRegistry;

public interface NetworkEntityMetadataObjectDeserializer<T> {

	public T read(ByteBuf buffer);

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectByte> BYTE = buffer -> new NetworkEntityMetadataObjectByte(buffer.readByte());

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectVarInt> VARINT = buffer -> new NetworkEntityMetadataObjectVarInt(VarNumberSerializer.readVarInt(buffer));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectFloat> FLOAT = buffer -> new NetworkEntityMetadataObjectFloat(buffer.readFloat());

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectString> STRING = buffer -> new NetworkEntityMetadataObjectString(StringSerializer.readVarIntUTF8String(buffer));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectChat> CHAT = buffer -> new NetworkEntityMetadataObjectChat(ChatAPI.fromJSON(StringSerializer.readVarIntUTF8String(buffer), true));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectOptionalChat> OPT_CHAT = buffer -> buffer.readBoolean() ? new NetworkEntityMetadataObjectOptionalChat(ChatAPI.fromJSON(StringSerializer.readVarIntUTF8String(buffer), true)) : new NetworkEntityMetadataObjectOptionalChat();

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectItemStack> ITEMSTACK = buffer -> new NetworkEntityMetadataObjectItemStack(ItemStackSerializer.readItemStack(buffer));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectBoolean> BOOLEAN = buffer -> new NetworkEntityMetadataObjectBoolean(buffer.readBoolean());

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectVector3f> VECTOR3F = buffer -> new NetworkEntityMetadataObjectVector3f(new Vector(buffer.readFloat(), buffer.readFloat(), buffer.readFloat()));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectPosition> POSITION = buffer -> new NetworkEntityMetadataObjectPosition(PositionSerializer.readPosition(buffer));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectOptionalPosition> OPT_POSITION = buffer -> buffer.readBoolean() ? new NetworkEntityMetadataObjectOptionalPosition(PositionSerializer.readPosition(buffer)) : new NetworkEntityMetadataObjectOptionalPosition();

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectDirection> DIRECTION = buffer -> new NetworkEntityMetadataObjectDirection(MiscSerializer.readVarIntEnum(buffer, BlockDirection.CONSTANT_LOOKUP));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectOptionalUUID> OPT_UUID = buffer -> buffer.readBoolean() ? new NetworkEntityMetadataObjectOptionalUUID(UUIDSerializer.readUUID2L(buffer)) : new NetworkEntityMetadataObjectOptionalUUID();

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectBlockData> BLOCKDATA = buffer -> new NetworkEntityMetadataObjectBlockData(VarNumberSerializer.readVarInt(buffer));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectNBT> NBT = buffer -> new NetworkEntityMetadataObjectNBT(ItemStackSerializer.readDirectTag(buffer));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectParticle> PARTICLE = buffer -> {
		NetworkParticle particle = NetworkParticleRegistry.fromId(VarNumberSerializer.readVarInt(buffer));
		particle.readData(buffer);
		return new NetworkEntityMetadataObjectParticle(particle);
	};

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectVillagerData> VILLAGER_DATA = buffer -> new NetworkEntityMetadataObjectVillagerData(new VillagerData(
		VarNumberSerializer.readVarInt(buffer),
		VillagerProfession.CONSTANT_LOOKUP.getByOrdinalOrDefault(VarNumberSerializer.readVarInt(buffer), VillagerProfession.NONE),
		VarNumberSerializer.readVarInt(buffer)
	));

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectOptionalVarInt> OPT_VARINT = buffer -> {
		int value = VarNumberSerializer.readVarInt(buffer);
		return value > 0 ? new NetworkEntityMetadataObjectOptionalVarInt(value - 1) : new NetworkEntityMetadataObjectOptionalVarInt();
	};

	public static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObjectEntityPose> ENTITY_POSE = buffer -> new NetworkEntityMetadataObjectEntityPose(MiscSerializer.readByteEnum(buffer, EntityPose.CONSTANT_LOOKUP));

}
