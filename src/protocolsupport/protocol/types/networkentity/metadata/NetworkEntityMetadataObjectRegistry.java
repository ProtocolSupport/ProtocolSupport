package protocolsupport.protocol.types.networkentity.metadata;

import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.HashMap;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.types.networkentity.metadata.objects.*;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupportbuildprocessor.Preload;

@Preload
public class NetworkEntityMetadataObjectRegistry {

	private static final HashMap<Class<? extends NetworkEntityMetadataObject<?>>, EnumMap<ProtocolVersion, Integer>> registry = new HashMap<>();

	private static void register(Class<? extends NetworkEntityMetadataObject<?>> clazz, int id, ProtocolVersion... versions) {
		EnumMap<ProtocolVersion, Integer> mmap = registry.computeIfAbsent(clazz, k -> new EnumMap<>(ProtocolVersion.class));
		for (ProtocolVersion version : versions) {
			mmap.put(version, id);
		}
	}

	static {
		register(NetworkEntityMetadataObjectByte.class, 0, ProtocolVersionsHelper.ALL_PC);
		register(NetworkEntityMetadataObjectVarInt.class, 1, ProtocolVersionsHelper.UP_1_9);
		register(NetworkEntityMetadataObjectFloat.class, 2, ProtocolVersionsHelper.UP_1_9);
		register(NetworkEntityMetadataObjectFloat.class, 3, ProtocolVersionsHelper.BEFORE_1_9);
		register(NetworkEntityMetadataObjectString.class, 3, ProtocolVersionsHelper.UP_1_9);
		register(NetworkEntityMetadataObjectString.class, 4, ProtocolVersionsHelper.BEFORE_1_9);
		register(NetworkEntityMetadataObjectChat.class, 4, ProtocolVersionsHelper.UP_1_9);
		register(NetworkEntityMetadataObjectOptionalChat.class, 5, ProtocolVersionsHelper.UP_1_13);
		register(NetworkEntityMetadataObjectItemStack.class, 6, ProtocolVersionsHelper.UP_1_13);
		register(NetworkEntityMetadataObjectItemStack.class, 5, ProtocolVersionsHelper.BEFORE_1_13);
		register(NetworkEntityMetadataObjectBoolean.class, 7, ProtocolVersionsHelper.UP_1_13);
		register(NetworkEntityMetadataObjectBoolean.class, 6, ProtocolVersionsHelper.RANGE__1_9__1_12_2);
		register(NetworkEntityMetadataObjectVector3f.class, 8, ProtocolVersionsHelper.UP_1_13);
		register(NetworkEntityMetadataObjectVector3f.class, 7, ProtocolVersionsHelper.BEFORE_1_13);
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
		register(NetworkEntityMetadataObjectNBTTagCompound.class, 14, ProtocolVersionsHelper.UP_1_13);
		register(NetworkEntityMetadataObjectNBTTagCompound.class, 13, ProtocolVersionsHelper.RANGE__1_11__1_12_2);
		register(NetworkEntityMetadataObjectParticle.class, 15, ProtocolVersionsHelper.UP_1_13);
		register(NetworkEntityMetadataObjectVillagerData.class, 16, ProtocolVersionsHelper.UP_1_14);
		register(NetworkEntityMetadataObjectOptionalVarInt.class, 17, ProtocolVersionsHelper.UP_1_14);
		register(NetworkEntityMetadataObjectEntityPose.class, 18, ProtocolVersionsHelper.UP_1_14);
		register(NetworkEntityMetadataObjectShort.class, 1, ProtocolVersionsHelper.BEFORE_1_9);
		register(NetworkEntityMetadataObjectInt.class, 2, ProtocolVersionsHelper.BEFORE_1_9);
		register(NetworkEntityMetadataObjectVector3i.class, 6, ProtocolVersionsHelper.BEFORE_1_9);
		register(NetworkEntityMetadataObjectByte.class, 0, ProtocolVersionsHelper.ALL_PE);
		register(NetworkEntityMetadataShortLe.class, 1, ProtocolVersionsHelper.ALL_PE);
		register(NetworkEntityMetadataObjectVarInt.class, 2, ProtocolVersionsHelper.ALL_PE);
		register(NetworkEntityMetadataObjectSVarInt.class, 2, ProtocolVersionsHelper.ALL_PE);
		register(NetworkEntityMetadataObjectFloatLe.class, 3, ProtocolVersionsHelper.ALL_PE);
		register(NetworkEntityMetadataObjectString.class, 4, ProtocolVersionsHelper.ALL_PE);
		register(NetworkEntityMetadataObjectItemStack.class, 5, ProtocolVersionsHelper.ALL_PE);
		register(NetworkEntityMetadataObjectVector3vi.class, 6, ProtocolVersionsHelper.ALL_PE);
		register(NetworkEntityMetadataObjectVarLong.class, 7, ProtocolVersionsHelper.ALL_PE);
		register(NetworkEntityMetadataObjectSVarLong.class, 7, ProtocolVersionsHelper.ALL_PE);
		register(NetworkEntityMetadataObjectVector3fLe.class, 8, ProtocolVersionsHelper.ALL_PE);
	}

	public static int getTypeId(@SuppressWarnings("rawtypes") Class<? extends NetworkEntityMetadataObject> clazz, ProtocolVersion version) {
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

	public static int getTypeId(NetworkEntityMetadataObject<?> object, ProtocolVersion version) {
		return getTypeId(object.getClass(), version);
	}

}
