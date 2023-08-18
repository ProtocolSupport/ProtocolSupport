package protocolsupport.protocol.types.networkentity.metadata;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.util.Vector;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.MiscDataCodec;
import protocolsupport.protocol.codec.OptionalCodec;
import protocolsupport.protocol.codec.PositionCodec;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.codec.chat.ChatCodec;
import protocolsupport.protocol.typeremapper.particle.FlatteningNetworkParticleDataSerializer;
import protocolsupport.protocol.typeremapper.particle.FlatteningNetworkParticleIdRegistry;
import protocolsupport.protocol.types.Vector4f;
import protocolsupport.protocol.types.networkentity.data.VillagerData;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBlockData;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBoolean;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectCatVariant;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectChat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectDirection;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectEntityPose;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectFloat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectFrogVariant;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectInt;
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
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectShort;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectSnifferState;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectString;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarLong;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVector3f;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVector3i;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVector4f;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVillagerData;
import protocolsupport.protocol.types.particle.NetworkParticle;
import protocolsupport.protocol.types.particle.NetworkParticleRegistry;

//TODO: version based registry
public class NetworkEntityMetadataObjectSerializerRegistry {

	private NetworkEntityMetadataObjectSerializerRegistry() {
	}

	protected static final Map<Class<? extends NetworkEntityMetadataObject<?>>, NetworkEntityMetadataObjectSerializer<? extends NetworkEntityMetadataObject<?>>> registry = new HashMap<>();

	protected static <T extends NetworkEntityMetadataObject<?>> void register(Class<T> clazz, NetworkEntityMetadataObjectSerializer<T> serializer) {
		registry.put(clazz, serializer);
	}

	static {
		register(NetworkEntityMetadataObjectByte.class, (buffer, version, locale, object) -> buffer.writeByte(object.getValue()));

		register(NetworkEntityMetadataObjectVarInt.class, (buffer, version, locale, object) -> VarNumberCodec.writeVarInt(buffer, object.getValue()));

		register(NetworkEntityMetadataObjectVarLong.class, (buffer, version, locale, object) -> VarNumberCodec.writeVarLong(buffer, object.getValue()));

		register(NetworkEntityMetadataObjectFloat.class, (buffer, version, locale, object) -> buffer.writeFloat(object.getValue()));

		register(NetworkEntityMetadataObjectString.class, (buffer, version, locale, object) -> StringCodec.writeString(buffer, version, object.getValue()));

		register(NetworkEntityMetadataObjectChat.class, (buffer, version, locale, object) -> StringCodec.writeVarIntUTF8String(buffer, ChatCodec.serialize(version, locale, object.getValue())));

		register(NetworkEntityMetadataObjectOptionalChat.class, (buffer, version, locale, object) -> OptionalCodec.writeOptionalVarIntUTF8String(buffer, ChatCodec.serialize(version, locale, object.getValue())));

		register(NetworkEntityMetadataObjectItemStack.class, (buffer, version, locale, object) -> ItemStackCodec.writeItemStack(buffer, version, locale, object.getValue()));

		register(NetworkEntityMetadataObjectBoolean.class, (buffer, version, locale, object) -> buffer.writeBoolean(object.getValue()));

		register(NetworkEntityMetadataObjectRotation.class, (buffer, version, locale, object) -> {
			Vector value = object.getValue();
			buffer.writeFloat((float) value.getX());
			buffer.writeFloat((float) value.getY());
			buffer.writeFloat((float) value.getZ());
		});

		register(NetworkEntityMetadataObjectVector3f.class, (buffer, version, locale, object) -> {
			Vector value = object.getValue();
			buffer.writeFloat((float) value.getX());
			buffer.writeFloat((float) value.getY());
			buffer.writeFloat((float) value.getZ());
		});

		register(NetworkEntityMetadataObjectPosition.class, (buffer, version, locale, object) -> {
			if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_14)) {
				PositionCodec.writePosition(buffer, object.getValue());
			} else {
				PositionCodec.writePositionLXYZ(buffer, object.getValue());
			}
		});

		register(NetworkEntityMetadataObjectOptionalPosition.class, (buffer, version, locale, object) -> {
			OptionalCodec.writeOptional(buffer, object.getValue(), (positionData, positionElement) -> {
				if (version.isAfterOrEq(ProtocolVersion.MINECRAFT_1_14)) {
					PositionCodec.writePosition(buffer, object.getValue());
				} else {
					PositionCodec.writePositionLXYZ(buffer, object.getValue());
				}
			});
		});

		register(NetworkEntityMetadataObjectDirection.class, (buffer, version, locale, object) -> MiscDataCodec.writeVarIntEnum(buffer, object.getValue()));

		register(NetworkEntityMetadataObjectOptionalUUID.class, (buffer, version, locale, object) -> OptionalCodec.writeOptional(buffer, object.getValue(), UUIDCodec::writeUUID));

		register(NetworkEntityMetadataObjectBlockData.class, (buffer, version, locale, object) -> VarNumberCodec.writeVarInt(buffer, object.getValue()));

		register(NetworkEntityMetadataObjectOptionalBlockData.class, (buffer, version, locale, object) -> OptionalCodec.writeOptional(buffer, object.getValue(), VarNumberCodec::writeVarInt));

		register(NetworkEntityMetadataObjectNBT.class, (buffer, version, locale, object) -> ItemStackCodec.writeDirectTag(buffer, object.getValue()));

		register(NetworkEntityMetadataObjectParticle.class, (buffer, version, locale, object) -> {
			NetworkParticle value = object.getValue();
			VarNumberCodec.writeVarInt(buffer, FlatteningNetworkParticleIdRegistry.INSTANCE.getTable(version).get(NetworkParticleRegistry.getId(value)));
			FlatteningNetworkParticleDataSerializer.INSTANCE.get(version).write(buffer, value);
		});

		register(NetworkEntityMetadataObjectVillagerData.class, (buffer, version, locale, object) -> {
			VillagerData value = object.getValue();
			VarNumberCodec.writeVarInt(buffer, value.getType());
			VarNumberCodec.writeVarInt(buffer, value.getProfession().ordinal());
			VarNumberCodec.writeVarInt(buffer, value.getLevel());
		});

		register(NetworkEntityMetadataObjectOptionalVarInt.class, (buffer, version, locale, object) -> {
			Integer value = object.getValue();
			VarNumberCodec.writeVarInt(buffer, value != null ? value + 1 : 0);
		});

		//TODO: flattening table
		register(NetworkEntityMetadataObjectEntityPose.class, (buffer, version, locale, object) -> MiscDataCodec.writeByteEnum(buffer, object.getValue()));

		register(NetworkEntityMetadataObjectCatVariant.class, (buffer, version, locale, object) -> VarNumberCodec.writeVarInt(buffer, object.getValue()));

		register(NetworkEntityMetadataObjectFrogVariant.class, (buffer, version, locale, object) -> VarNumberCodec.writeVarInt(buffer, object.getValue()));

		register(NetworkEntityMetadataObjectPaintingVariant.class, (buffer, version, locale, object) -> VarNumberCodec.writeVarInt(buffer, object.getValue()));

		register(NetworkEntityMetadataObjectSnifferState.class, (buffer, version, locale, object) -> VarNumberCodec.writeVarInt(buffer, object.getValue()));

		register(NetworkEntityMetadataObjectOptionalWorldPosition.class, (buffer, version, locale, object) -> OptionalCodec.writeOptional(buffer, object.getValue(), PositionCodec::writeWorldPosition));

		register(NetworkEntityMetadataObjectVector4f.class, (buffer, version, locale, object) -> {
			Vector4f value = object.getValue();
			buffer.writeFloat((float) value.getX());
			buffer.writeFloat((float) value.getY());
			buffer.writeFloat((float) value.getZ());
			buffer.writeFloat((float) value.getW());
		});

		register(NetworkEntityMetadataObjectShort.class, (buffer, version, locale, object) -> buffer.writeShort(object.getValue()));

		register(NetworkEntityMetadataObjectInt.class, (buffer, version, locale, object) -> buffer.writeInt(object.getValue()));

		register(NetworkEntityMetadataObjectVector3i.class, (buffer, version, locale, object) -> PositionCodec.writePositionIII(buffer, object.getValue()));
	}

	@SuppressWarnings("unchecked")
	public static <T extends NetworkEntityMetadataObject<?>> NetworkEntityMetadataObjectSerializer<T> get(Class<T> clazz) {
		return (NetworkEntityMetadataObjectSerializer<T>) registry.get(clazz);
	}

	public interface NetworkEntityMetadataObjectSerializer<T extends NetworkEntityMetadataObject<?>> {
		public void write(ByteBuf to, ProtocolVersion version, String locale, T object);
	}

}
