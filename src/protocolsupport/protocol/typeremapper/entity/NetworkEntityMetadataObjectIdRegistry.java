package protocolsupport.protocol.typeremapper.entity;

import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.Map;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
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
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class NetworkEntityMetadataObjectIdRegistry {

	private static final Map<ProtocolVersion, Object2IntMap<Class<? extends NetworkEntityMetadataObject<?>>>> registry = new EnumMap<>(ProtocolVersion.class);

	private static void register(@Nonnull Class<? extends NetworkEntityMetadataObject<?>> clazz, @Nonnegative int id, @Nonnull ProtocolVersion... versions) {
		for (ProtocolVersion version : versions) {
			registry.computeIfAbsent(version, k -> {
				Object2IntMap<Class<? extends NetworkEntityMetadataObject<?>>> map = new Object2IntOpenHashMap<>();
				map.defaultReturnValue(-1);
				return map;
			}).put(clazz, id);
		}
	}

	static {
		register(NetworkEntityMetadataObjectByte.class, 0, ProtocolVersionsHelper.ALL_PC);

		register(NetworkEntityMetadataObjectVarInt.class, 1, ProtocolVersionsHelper.UP_1_9);

		register(NetworkEntityMetadataObjectVarLong.class, 2, ProtocolVersionsHelper.UP_1_19_4);

		register(NetworkEntityMetadataObjectFloat.class, 3, ProtocolVersionsHelper.UP_1_19_4);
		register(NetworkEntityMetadataObjectFloat.class, 2, ProtocolVersionsHelper.RANGE__1_9__1_19_3);
		register(NetworkEntityMetadataObjectFloat.class, 3, ProtocolVersionsHelper.DOWN_1_8);

		register(NetworkEntityMetadataObjectString.class, 4, ProtocolVersionsHelper.UP_1_19_4);
		register(NetworkEntityMetadataObjectString.class, 3, ProtocolVersionsHelper.RANGE__1_9__1_19_3);
		register(NetworkEntityMetadataObjectString.class, 4, ProtocolVersionsHelper.DOWN_1_8);

		register(NetworkEntityMetadataObjectChat.class, 5, ProtocolVersionsHelper.UP_1_19_4);
		register(NetworkEntityMetadataObjectChat.class, 4, ProtocolVersionsHelper.RANGE__1_9__1_19_3);

		register(NetworkEntityMetadataObjectOptionalChat.class, 6, ProtocolVersionsHelper.UP_1_19_4);
		register(NetworkEntityMetadataObjectOptionalChat.class, 5, ProtocolVersionsHelper.RANGE__1_13__1_19_3);

		register(NetworkEntityMetadataObjectItemStack.class, 7, ProtocolVersionsHelper.UP_1_19_4);
		register(NetworkEntityMetadataObjectItemStack.class, 6, ProtocolVersionsHelper.RANGE__1_13__1_19_3);
		register(NetworkEntityMetadataObjectItemStack.class, 5, ProtocolVersionsHelper.DOWN_1_12_2);

		register(NetworkEntityMetadataObjectBoolean.class, 8, ProtocolVersionsHelper.UP_1_19_4);
		register(NetworkEntityMetadataObjectBoolean.class, 7, ProtocolVersionsHelper.RANGE__1_13__1_19_3);
		register(NetworkEntityMetadataObjectBoolean.class, 6, ProtocolVersionsHelper.RANGE__1_9__1_12_2);

		register(NetworkEntityMetadataObjectRotation.class, 9, ProtocolVersionsHelper.UP_1_19_4);
		register(NetworkEntityMetadataObjectRotation.class, 8, ProtocolVersionsHelper.RANGE__1_13__1_19_3);
		register(NetworkEntityMetadataObjectRotation.class, 7, ProtocolVersionsHelper.DOWN_1_12_2);

		register(NetworkEntityMetadataObjectPosition.class, 10, ProtocolVersionsHelper.UP_1_19_4);
		register(NetworkEntityMetadataObjectPosition.class, 9, ProtocolVersionsHelper.RANGE__1_13__1_19_3);
		register(NetworkEntityMetadataObjectPosition.class, 8, ProtocolVersionsHelper.RANGE__1_9__1_12_2);

		register(NetworkEntityMetadataObjectOptionalPosition.class, 11, ProtocolVersionsHelper.UP_1_19_4);
		register(NetworkEntityMetadataObjectOptionalPosition.class, 10, ProtocolVersionsHelper.RANGE__1_13__1_19_3);
		register(NetworkEntityMetadataObjectOptionalPosition.class, 9, ProtocolVersionsHelper.RANGE__1_9__1_12_2);

		register(NetworkEntityMetadataObjectDirection.class, 12, ProtocolVersionsHelper.UP_1_19_4);
		register(NetworkEntityMetadataObjectDirection.class, 11, ProtocolVersionsHelper.RANGE__1_13__1_19_3);
		register(NetworkEntityMetadataObjectDirection.class, 10, ProtocolVersionsHelper.RANGE__1_9__1_12_2);

		register(NetworkEntityMetadataObjectOptionalUUID.class, 13, ProtocolVersionsHelper.UP_1_19_4);
		register(NetworkEntityMetadataObjectOptionalUUID.class, 12, ProtocolVersionsHelper.RANGE__1_13__1_19_3);
		register(NetworkEntityMetadataObjectOptionalUUID.class, 11, ProtocolVersionsHelper.RANGE__1_9__1_12_2);

		register(NetworkEntityMetadataObjectBlockData.class, 14, ProtocolVersionsHelper.UP_1_19_4);
		register(NetworkEntityMetadataObjectBlockData.class, 13, ProtocolVersionsHelper.RANGE__1_13__1_19_3);
		register(NetworkEntityMetadataObjectBlockData.class, 12, ProtocolVersionsHelper.RANGE__1_9__1_12_2);

		register(NetworkEntityMetadataObjectOptionalBlockData.class, 15, ProtocolVersionsHelper.UP_1_19_4);

		register(NetworkEntityMetadataObjectNBT.class, 16, ProtocolVersionsHelper.UP_1_19_4);
		register(NetworkEntityMetadataObjectNBT.class, 14, ProtocolVersionsHelper.RANGE__1_13__1_19_3);
		register(NetworkEntityMetadataObjectNBT.class, 13, ProtocolVersionsHelper.RANGE__1_11__1_12_2);

		register(NetworkEntityMetadataObjectParticle.class, 17, ProtocolVersionsHelper.UP_1_19_4);
		register(NetworkEntityMetadataObjectParticle.class, 15, ProtocolVersionsHelper.RANGE__1_13__1_19_3);

		register(NetworkEntityMetadataObjectVillagerData.class, 18, ProtocolVersionsHelper.UP_1_19_4);
		register(NetworkEntityMetadataObjectVillagerData.class, 16, ProtocolVersionsHelper.RANGE__1_14__1_19_3);

		register(NetworkEntityMetadataObjectOptionalVarInt.class, 19, ProtocolVersionsHelper.UP_1_19_4);
		register(NetworkEntityMetadataObjectOptionalVarInt.class, 17, ProtocolVersionsHelper.RANGE__1_14__1_19_3);

		register(NetworkEntityMetadataObjectEntityPose.class, 20, ProtocolVersionsHelper.UP_1_19_4);
		register(NetworkEntityMetadataObjectEntityPose.class, 18, ProtocolVersionsHelper.RANGE__1_14__1_19_3);

		register(NetworkEntityMetadataObjectCatVariant.class, 21, ProtocolVersionsHelper.UP_1_19_4);
		register(NetworkEntityMetadataObjectCatVariant.class, 19, ProtocolVersionsHelper.RANGE__1_19__1_19_3);

		register(NetworkEntityMetadataObjectFrogVariant.class, 22, ProtocolVersionsHelper.UP_1_19_4);
		register(NetworkEntityMetadataObjectFrogVariant.class, 20, ProtocolVersionsHelper.RANGE__1_19__1_19_3);

		register(NetworkEntityMetadataObjectOptionalWorldPosition.class, 23, ProtocolVersionsHelper.UP_1_19_4);

		register(NetworkEntityMetadataObjectPaintingVariant.class, 24, ProtocolVersionsHelper.UP_1_19_4);
		register(NetworkEntityMetadataObjectPaintingVariant.class, 21, ProtocolVersionsHelper.RANGE__1_19__1_19_3);

		register(NetworkEntityMetadataObjectSnifferState.class, 25, ProtocolVersionsHelper.UP_1_19_4);

		register(NetworkEntityMetadataObjectVector3f.class, 26, ProtocolVersionsHelper.UP_1_19_4);

		register(NetworkEntityMetadataObjectVector4f.class, 27, ProtocolVersionsHelper.UP_1_19_4);

		register(NetworkEntityMetadataObjectShort.class, 1, ProtocolVersionsHelper.DOWN_1_8);
		register(NetworkEntityMetadataObjectInt.class, 2, ProtocolVersionsHelper.DOWN_1_8);
		register(NetworkEntityMetadataObjectVector3i.class, 6, ProtocolVersionsHelper.DOWN_1_8);
	}

	public static int getTypeId(@SuppressWarnings("rawtypes") @Nonnull Class<? extends NetworkEntityMetadataObject> clazz, @Nonnull ProtocolVersion version) {
		Object2IntMap<Class<? extends NetworkEntityMetadataObject<?>>> map = registry.get(version);
		if (map == null) {
			throw new IllegalArgumentException(MessageFormat.format("Missing legacy network entity metadata object id table for version {0}", version));
		}
		int id = map.getInt(clazz);
		if (id == map.defaultReturnValue()) {
			throw new IllegalArgumentException(MessageFormat.format("No id exists for network entity metadata object {0} and protocol version {1}", clazz, version));
		}
		return id;
	}

	public static int getTypeId(@Nonnull NetworkEntityMetadataObject<?> object, @Nonnull ProtocolVersion version) {
		return getTypeId(object.getClass(), version);
	}

}
