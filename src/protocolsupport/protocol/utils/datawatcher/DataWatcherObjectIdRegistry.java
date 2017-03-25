package protocolsupport.protocol.utils.datawatcher;

import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.HashMap;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBlockState;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBoolean;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectChat;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectDirection;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectFloat;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectItemStack;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectOptionalPosition;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectOptionalUUID;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectPosition;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectShort;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectString;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVarInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVector3f;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVector3i;
import protocolsupport.utils.ProtocolVersionsHelper;
import protocolsupport.utils.Utils;

public class DataWatcherObjectIdRegistry {

	public static void init() {
	}

	private static final HashMap<Class<? extends DataWatcherObject<?>>, EnumMap<ProtocolVersion, Integer>> registry = new HashMap<>();

	private static void register(Class<? extends DataWatcherObject<?>> clazz, int id, ProtocolVersion... versions) {
		EnumMap<ProtocolVersion, Integer> mmap = Utils.getOrCreateDefault(registry, clazz, new EnumMap<>(ProtocolVersion.class));
		for (ProtocolVersion version : versions) {
			mmap.put(version, id);
		}
	}

	static {
		register(DataWatcherObjectBlockState.class, 12, ProtocolVersionsHelper.AFTER_1_8);
		register(DataWatcherObjectBoolean.class, 6, ProtocolVersionsHelper.AFTER_1_8);
		register(DataWatcherObjectByte.class, 0, ProtocolVersionsHelper.ALL);
		register(DataWatcherObjectChat.class, 4, ProtocolVersionsHelper.AFTER_1_8);
		register(DataWatcherObjectDirection.class, 10, ProtocolVersionsHelper.AFTER_1_8);
		register(DataWatcherObjectFloat.class, 2, ProtocolVersionsHelper.AFTER_1_8);
		register(DataWatcherObjectFloat.class, 3, ProtocolVersionsHelper.BEFORE_1_9);
		register(DataWatcherObjectInt.class, 2, ProtocolVersionsHelper.BEFORE_1_9);
		register(DataWatcherObjectItemStack.class, 5, ProtocolVersionsHelper.ALL);
		register(DataWatcherObjectOptionalPosition.class, 9, ProtocolVersionsHelper.AFTER_1_8);
		register(DataWatcherObjectOptionalUUID.class, 11, ProtocolVersionsHelper.AFTER_1_8);
		register(DataWatcherObjectPosition.class, 8, ProtocolVersionsHelper.AFTER_1_8);
		register(DataWatcherObjectShort.class, 1, ProtocolVersionsHelper.BEFORE_1_9);
		register(DataWatcherObjectString.class, 3, ProtocolVersionsHelper.AFTER_1_8);
		register(DataWatcherObjectString.class, 4, ProtocolVersionsHelper.BEFORE_1_9);
		register(DataWatcherObjectVarInt.class, 1, ProtocolVersionsHelper.AFTER_1_8);
		register(DataWatcherObjectVector3f.class, 7, ProtocolVersionsHelper.ALL);
		register(DataWatcherObjectVector3i.class, 6, ProtocolVersionsHelper.BEFORE_1_9);
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
