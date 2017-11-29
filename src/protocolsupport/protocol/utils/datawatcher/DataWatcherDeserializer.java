package protocolsupport.protocol.utils.datawatcher;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBlockState;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBoolean;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectChat;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectDirection;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectFloat;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectItemStack;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectNBTTagCompound;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectOptionalPosition;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectOptionalUUID;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectPosition;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectString;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVarInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVector3f;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class DataWatcherDeserializer {

	//while meta indexes can be now up to 255, we actually use up to 31, but we use upto 76 for PE
	public static final int MAX_USED_META_INDEX = 76;

	@SuppressWarnings("unchecked")
	private static final Constructor<? extends ReadableDataWatcherObject<?>>[] registry = new Constructor[256];
	static {
		try {
			register(DataWatcherObjectByte.class);
			register(DataWatcherObjectVarInt.class);
			register(DataWatcherObjectFloat.class);
			register(DataWatcherObjectString.class);
			register(DataWatcherObjectChat.class);
			register(DataWatcherObjectItemStack.class);
			register(DataWatcherObjectBoolean.class);
			register(DataWatcherObjectVector3f.class);
			register(DataWatcherObjectPosition.class);
			register(DataWatcherObjectOptionalPosition.class);
			register(DataWatcherObjectDirection.class);
			register(DataWatcherObjectOptionalUUID.class);
			register(DataWatcherObjectBlockState.class);
			register(DataWatcherObjectNBTTagCompound.class);
		} catch (Exception e) {
			throw new RuntimeException("Exception in datawatcher init", e);
		}
	}

	private static void register(Class<? extends ReadableDataWatcherObject<?>> clazz) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<? extends ReadableDataWatcherObject<?>> constr = clazz.getConstructor();
		registry[DataWatcherObjectIdRegistry.getTypeId(clazz, ProtocolVersionsHelper.LATEST_PC)] = constr;
	}

	public static void decodeDataTo(ByteBuf from, ProtocolVersion version, String locale, ArrayMap<DataWatcherObject<?>> to) {
		do {
			int key = from.readUnsignedByte();
			if (key == 0xFF) {
				break;
			}
			int type = from.readUnsignedByte();
			try {
				ReadableDataWatcherObject<?> object = registry[type].newInstance();
				object.readFromStream(from, version, locale);
				to.put(key, object);
			} catch (Exception e) {
				throw new DecoderException("Unable to decode datawatcher object", e);
			}
		} while (true);
	}

	public static void encodeData(ByteBuf to, ProtocolVersion version, String locale, ArrayMap<DataWatcherObject<?>> objects) {
		boolean hadObject = false;
		for (int key = objects.getMinKey(); key < objects.getMaxKey(); key++) {
			DataWatcherObject<?> object = objects.get(key);
			if (object != null) {
				hadObject = true;
				to.writeByte(key);
				to.writeByte(DataWatcherObjectIdRegistry.getTypeId(object, version));
				object.writeToStream(to, version, locale);
			}
		}
		if (!hadObject) {
			to.writeByte(31);
			to.writeByte(0);
			to.writeByte(0);
		}
		to.writeByte(0xFF);
	}

}
