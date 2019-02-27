package protocolsupport.protocol.utils.datawatcher;

import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.HashMap;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBlockData;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBoolean;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectChat;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectDirection;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectFloat;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectFloatLe;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectItemStack;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectNBTTagCompound;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectOptionalChat;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectOptionalPosition;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectOptionalUUID;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectParticle;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectPosition;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectSVarInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectSVarLong;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectShort;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectShortLe;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectString;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVarInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVarLong;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVector3f;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVector3fLe;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVector3i;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVector3vi;
import protocolsupportbuildprocessor.Preload;

@Preload
public class DataWatcherObjectIdRegistry {

	private static final HashMap<Class<? extends DataWatcherObject<?>>, EnumMap<ProtocolVersion, Integer>> registry = new HashMap<>();

	private static void register(Class<? extends DataWatcherObject<?>> clazz, int id, ProtocolVersion... versions) {
		EnumMap<ProtocolVersion, Integer> mmap = registry.computeIfAbsent(clazz, k -> new EnumMap<>(ProtocolVersion.class));
		for (ProtocolVersion version : versions) {
			mmap.put(version, id);
		}
	}

	static {
		//PC
		register(DataWatcherObjectByte.class, 0, ProtocolVersionsHelper.ALL_PC);
		register(DataWatcherObjectVarInt.class, 1, ProtocolVersionsHelper.UP_1_9);
		register(DataWatcherObjectFloat.class, 2, ProtocolVersionsHelper.UP_1_9);
		register(DataWatcherObjectFloat.class, 3, ProtocolVersionsHelper.BEFORE_1_9);
		register(DataWatcherObjectString.class, 3, ProtocolVersionsHelper.UP_1_9);
		register(DataWatcherObjectString.class, 4, ProtocolVersionsHelper.BEFORE_1_9);
		register(DataWatcherObjectChat.class, 4, ProtocolVersionsHelper.UP_1_9);
		register(DataWatcherObjectOptionalChat.class, 5, ProtocolVersionsHelper.UP_1_13);
		register(DataWatcherObjectItemStack.class, 6, ProtocolVersionsHelper.UP_1_13);
		register(DataWatcherObjectItemStack.class, 5, ProtocolVersionsHelper.BEFORE_1_13);
		register(DataWatcherObjectBoolean.class, 7, ProtocolVersionsHelper.UP_1_13);
		register(DataWatcherObjectBoolean.class, 6, ProtocolVersionsHelper.RANGE__1_9__1_12_2);
		register(DataWatcherObjectVector3f.class, 8, ProtocolVersionsHelper.UP_1_13);
		register(DataWatcherObjectVector3f.class, 7, ProtocolVersionsHelper.BEFORE_1_13);
		register(DataWatcherObjectPosition.class, 9, ProtocolVersionsHelper.UP_1_13);
		register(DataWatcherObjectPosition.class, 8, ProtocolVersionsHelper.RANGE__1_9__1_12_2);
		register(DataWatcherObjectOptionalPosition.class, 10, ProtocolVersionsHelper.UP_1_13);
		register(DataWatcherObjectOptionalPosition.class, 9, ProtocolVersionsHelper.RANGE__1_9__1_12_2);
		register(DataWatcherObjectDirection.class, 11, ProtocolVersionsHelper.UP_1_13);
		register(DataWatcherObjectDirection.class, 10, ProtocolVersionsHelper.RANGE__1_9__1_12_2);
		register(DataWatcherObjectOptionalUUID.class, 12, ProtocolVersionsHelper.UP_1_13);
		register(DataWatcherObjectOptionalUUID.class, 11, ProtocolVersionsHelper.RANGE__1_9__1_12_2);
		register(DataWatcherObjectBlockData.class, 13, ProtocolVersionsHelper.UP_1_13);
		register(DataWatcherObjectBlockData.class, 12, ProtocolVersionsHelper.RANGE__1_9__1_12_2);
		register(DataWatcherObjectNBTTagCompound.class, 14, ProtocolVersionsHelper.UP_1_13);
		register(DataWatcherObjectNBTTagCompound.class, 13, ProtocolVersionsHelper.RANGE__1_11__1_12_2);
		register(DataWatcherObjectParticle.class, 15, ProtocolVersionsHelper.UP_1_13);
		register(DataWatcherObjectShort.class, 1, ProtocolVersionsHelper.BEFORE_1_9);
		register(DataWatcherObjectInt.class, 2, ProtocolVersionsHelper.BEFORE_1_9);
		register(DataWatcherObjectVector3i.class, 6, ProtocolVersionsHelper.BEFORE_1_9);
		register(DataWatcherObjectByte.class, 0, ProtocolVersionsHelper.ALL_PE);
		register(DataWatcherObjectShortLe.class, 1, ProtocolVersionsHelper.ALL_PE);
		register(DataWatcherObjectVarInt.class, 2, ProtocolVersionsHelper.ALL_PE);
		register(DataWatcherObjectSVarInt.class, 2, ProtocolVersionsHelper.ALL_PE);
		register(DataWatcherObjectFloatLe.class, 3, ProtocolVersionsHelper.ALL_PE);
		register(DataWatcherObjectString.class, 4, ProtocolVersionsHelper.ALL_PE);
		register(DataWatcherObjectItemStack.class, 5, ProtocolVersionsHelper.ALL_PE);
		register(DataWatcherObjectVector3vi.class, 6, ProtocolVersionsHelper.ALL_PE);
		register(DataWatcherObjectVarLong.class, 7, ProtocolVersionsHelper.ALL_PE);
		register(DataWatcherObjectSVarLong.class, 7, ProtocolVersionsHelper.ALL_PE);
		register(DataWatcherObjectVector3fLe.class, 8, ProtocolVersionsHelper.ALL_PE);
	}

	public static int getTypeId(@SuppressWarnings("rawtypes") Class<? extends DataWatcherObject> clazz, ProtocolVersion version) {
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

	public static int getTypeId(DataWatcherObject<?> object, ProtocolVersion version) {
		return getTypeId(object.getClass(), version);
	}

}
