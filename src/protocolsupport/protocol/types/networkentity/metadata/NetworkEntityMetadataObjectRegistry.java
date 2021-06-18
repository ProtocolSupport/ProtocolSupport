package protocolsupport.protocol.types.networkentity.metadata;

import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.HashMap;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBlockData;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBoolean;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectChat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectDirection;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectEntityPose;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectFloat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectItemStack;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectNBT;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalChat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalPosition;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalUUID;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalVarInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectParticle;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectPosition;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectShort;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectString;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVector3f;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVector3i;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVillagerData;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupportbuildprocessor.Preload;

@Preload
public class NetworkEntityMetadataObjectRegistry {

	private NetworkEntityMetadataObjectRegistry() {
	}

	private static final HashMap<Class<? extends NetworkEntityMetadataObject<?>>, EnumMap<ProtocolVersion, Integer>> registry = new HashMap<>();

	private static void register(@Nonnull Class<? extends NetworkEntityMetadataObject<?>> clazz, @Nonnegative int id, @Nonnull ProtocolVersion... versions) {
		EnumMap<ProtocolVersion, Integer> mmap = registry.computeIfAbsent(clazz, k -> new EnumMap<>(ProtocolVersion.class));
		for (ProtocolVersion version : versions) {
			mmap.put(version, id);
		}
	}

	static {
		register(NetworkEntityMetadataObjectByte.class, 0, ProtocolVersionsHelper.ALL_PC);
		register(NetworkEntityMetadataObjectVarInt.class, 1, ProtocolVersionsHelper.UP_1_9);
		register(NetworkEntityMetadataObjectFloat.class, 2, ProtocolVersionsHelper.UP_1_9);
		register(NetworkEntityMetadataObjectFloat.class, 3, ProtocolVersionsHelper.DOWN_1_8);
		register(NetworkEntityMetadataObjectString.class, 3, ProtocolVersionsHelper.UP_1_9);
		register(NetworkEntityMetadataObjectString.class, 4, ProtocolVersionsHelper.DOWN_1_8);
		register(NetworkEntityMetadataObjectChat.class, 4, ProtocolVersionsHelper.UP_1_9);
		register(NetworkEntityMetadataObjectOptionalChat.class, 5, ProtocolVersionsHelper.UP_1_13);
		register(NetworkEntityMetadataObjectItemStack.class, 6, ProtocolVersionsHelper.UP_1_13);
		register(NetworkEntityMetadataObjectItemStack.class, 5, ProtocolVersionsHelper.DOWN_1_12_2);
		register(NetworkEntityMetadataObjectBoolean.class, 7, ProtocolVersionsHelper.UP_1_13);
		register(NetworkEntityMetadataObjectBoolean.class, 6, ProtocolVersionsHelper.RANGE__1_9__1_12_2);
		register(NetworkEntityMetadataObjectVector3f.class, 8, ProtocolVersionsHelper.UP_1_13);
		register(NetworkEntityMetadataObjectVector3f.class, 7, ProtocolVersionsHelper.DOWN_1_12_2);
		register(NetworkEntityMetadataObjectPosition.class, 9, ProtocolVersionsHelper.UP_1_13);
		register(NetworkEntityMetadataObjectPosition.class, 8, ProtocolVersionsHelper.RANGE__1_9__1_12_2);
		register(NetworkEntityMetadataObjectOptionalPosition.class, 10, ProtocolVersionsHelper.UP_1_13);
		register(NetworkEntityMetadataObjectOptionalPosition.class, 9, ProtocolVersionsHelper.RANGE__1_9__1_12_2);
		register(NetworkEntityMetadataObjectDirection.class, 11, ProtocolVersionsHelper.UP_1_13);
		register(NetworkEntityMetadataObjectDirection.class, 10, ProtocolVersionsHelper.RANGE__1_9__1_12_2);
		register(NetworkEntityMetadataObjectOptionalUUID.class, 12, ProtocolVersionsHelper.UP_1_13);
		register(NetworkEntityMetadataObjectOptionalUUID.class, 11, ProtocolVersionsHelper.RANGE__1_9__1_12_2);
		register(NetworkEntityMetadataObjectBlockData.class, 13, ProtocolVersionsHelper.UP_1_13);
		register(NetworkEntityMetadataObjectBlockData.class, 12, ProtocolVersionsHelper.RANGE__1_9__1_12_2);
		register(NetworkEntityMetadataObjectNBT.class, 14, ProtocolVersionsHelper.UP_1_13);
		register(NetworkEntityMetadataObjectNBT.class, 13, ProtocolVersionsHelper.RANGE__1_11__1_12_2);
		register(NetworkEntityMetadataObjectParticle.class, 15, ProtocolVersionsHelper.UP_1_13);
		register(NetworkEntityMetadataObjectVillagerData.class, 16, ProtocolVersionsHelper.UP_1_14);
		register(NetworkEntityMetadataObjectOptionalVarInt.class, 17, ProtocolVersionsHelper.UP_1_14);
		register(NetworkEntityMetadataObjectEntityPose.class, 18, ProtocolVersionsHelper.UP_1_14);
		register(NetworkEntityMetadataObjectShort.class, 1, ProtocolVersionsHelper.DOWN_1_8);
		register(NetworkEntityMetadataObjectInt.class, 2, ProtocolVersionsHelper.DOWN_1_8);
		register(NetworkEntityMetadataObjectVector3i.class, 6, ProtocolVersionsHelper.DOWN_1_8);
	}

	public static int getTypeId(@SuppressWarnings("rawtypes") @Nonnull Class<? extends NetworkEntityMetadataObject> clazz, @Nonnull ProtocolVersion version) {
		EnumMap<ProtocolVersion, Integer> mmap = registry.get(clazz);
		if (mmap == null) {
			throw new IllegalStateException(MessageFormat.format("No type id registry exists for object {0}", clazz));
		}
		Integer id = mmap.get(version);
		if (id == null) {
			throw new IllegalArgumentException(MessageFormat.format("No type id exists for object {0} for protocol version {1}", clazz, version));
		}
		return id;
	}

	public static int getTypeId(@Nonnull NetworkEntityMetadataObject<?> object, @Nonnull ProtocolVersion version) {
		return getTypeId(object.getClass(), version);
	}

}
