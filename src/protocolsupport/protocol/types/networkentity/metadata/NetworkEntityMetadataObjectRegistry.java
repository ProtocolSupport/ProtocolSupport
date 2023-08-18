package protocolsupport.protocol.types.networkentity.metadata;

import java.text.MessageFormat;
import java.util.Map;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

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
import protocolsupport.utils.CollectionsUtils;
import protocolsupportbuildprocessor.Preload;

@Preload
public class NetworkEntityMetadataObjectRegistry {

	private NetworkEntityMetadataObjectRegistry() {
	}

	@SuppressWarnings("unchecked")
	protected static final Map.Entry<Class<? extends NetworkEntityMetadataObject<?>>, NetworkEntityMetadataObjectDeserializer<? extends NetworkEntityMetadataObject<?>>>[] registry = new Map.Entry[64];

	protected static <T extends NetworkEntityMetadataObject<?>> void register(@Nonnegative int id, @Nonnull Class<T> clazz, NetworkEntityMetadataObjectDeserializer<T> deserializer) {
		registry[id] = Map.entry(clazz, deserializer);
	}

	static {
		register(0, NetworkEntityMetadataObjectByte.class, NetworkEntityMetadataObjectDeserializer.BYTE);
		register(1, NetworkEntityMetadataObjectVarInt.class, NetworkEntityMetadataObjectDeserializer.VARINT);
		register(2, NetworkEntityMetadataObjectVarLong.class, NetworkEntityMetadataObjectDeserializer.VARLONG);
		register(3, NetworkEntityMetadataObjectFloat.class, NetworkEntityMetadataObjectDeserializer.FLOAT);
		register(4, NetworkEntityMetadataObjectString.class, NetworkEntityMetadataObjectDeserializer.STRING);
		register(5, NetworkEntityMetadataObjectChat.class, NetworkEntityMetadataObjectDeserializer.CHAT);
		register(6, NetworkEntityMetadataObjectOptionalChat.class, NetworkEntityMetadataObjectDeserializer.OPT_CHAT);
		register(7, NetworkEntityMetadataObjectItemStack.class, NetworkEntityMetadataObjectDeserializer.ITEMSTACK);
		register(8, NetworkEntityMetadataObjectBoolean.class, NetworkEntityMetadataObjectDeserializer.BOOLEAN);
		register(9, NetworkEntityMetadataObjectRotation.class, NetworkEntityMetadataObjectDeserializer.ROTATION);
		register(10, NetworkEntityMetadataObjectPosition.class, NetworkEntityMetadataObjectDeserializer.POSITION);
		register(11, NetworkEntityMetadataObjectOptionalPosition.class, NetworkEntityMetadataObjectDeserializer.OPT_POSITION);
		register(12, NetworkEntityMetadataObjectDirection.class, NetworkEntityMetadataObjectDeserializer.DIRECTION);
		register(13, NetworkEntityMetadataObjectOptionalUUID.class, NetworkEntityMetadataObjectDeserializer.OPT_UUID);
		register(14, NetworkEntityMetadataObjectBlockData.class, NetworkEntityMetadataObjectDeserializer.BLOCKDATA);
		register(15, NetworkEntityMetadataObjectOptionalBlockData.class, NetworkEntityMetadataObjectDeserializer.OPT_BLOCKDATA);
		register(16, NetworkEntityMetadataObjectNBT.class, NetworkEntityMetadataObjectDeserializer.NBT);
		register(17, NetworkEntityMetadataObjectParticle.class, NetworkEntityMetadataObjectDeserializer.PARTICLE);
		register(18, NetworkEntityMetadataObjectVillagerData.class, NetworkEntityMetadataObjectDeserializer.VILLAGER_DATA);
		register(19, NetworkEntityMetadataObjectOptionalVarInt.class, NetworkEntityMetadataObjectDeserializer.OPT_VARINT);
		register(20, NetworkEntityMetadataObjectEntityPose.class, NetworkEntityMetadataObjectDeserializer.ENTITY_POSE);
		register(21, NetworkEntityMetadataObjectCatVariant.class, NetworkEntityMetadataObjectDeserializer.CAT_VARIANT);
		register(22, NetworkEntityMetadataObjectFrogVariant.class, NetworkEntityMetadataObjectDeserializer.FROG_VARIANT);
		register(23, NetworkEntityMetadataObjectOptionalWorldPosition.class, NetworkEntityMetadataObjectDeserializer.OPT_WORLD_POSITION);
		register(24, NetworkEntityMetadataObjectPaintingVariant.class, NetworkEntityMetadataObjectDeserializer.PAINTING_VARIANT);
		register(25, NetworkEntityMetadataObjectSnifferState.class, NetworkEntityMetadataObjectDeserializer.SNIFFER_STATE);
		register(26, NetworkEntityMetadataObjectVector3f.class, NetworkEntityMetadataObjectDeserializer.VECTOR3F);
		register(27, NetworkEntityMetadataObjectVector4f.class, NetworkEntityMetadataObjectDeserializer.VECTOR4F);
	}

	public static Map.Entry<Class<? extends NetworkEntityMetadataObject<?>>, NetworkEntityMetadataObjectDeserializer<? extends NetworkEntityMetadataObject<?>>> get(int typeId) {
		Map.Entry<Class<? extends NetworkEntityMetadataObject<?>>, NetworkEntityMetadataObjectDeserializer<? extends NetworkEntityMetadataObject<?>>> entry = CollectionsUtils.getFromArrayOrNull(registry, typeId);
		if (entry == null) {
			throw new IllegalArgumentException(MessageFormat.format("No network entity metadata object type exists for id {0}", typeId));
		}
		return entry;
	}

}
