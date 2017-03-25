package protocolsupport.protocol.utils.datawatcher;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBlockState;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBoolean;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectChat;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectDirection;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectFloat;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectItemStack;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectOptionalPosition;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectOptionalUUID;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectPosition;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectString;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVarInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVector3f;

public class DataWatcherDeserializer {

	@SuppressWarnings("unchecked")
	private static final Constructor<? extends DataWatcherObject<?>>[] registry = new Constructor[256];
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
		} catch (Exception e) {
			throw new RuntimeException("Exception in datawatcher init", e);
		}
	}

	private static void register(Class<? extends DataWatcherObject<?>> clazz) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Constructor<? extends DataWatcherObject<?>> constr = clazz.getConstructor();
		registry[DataWatcherObjectIdRegistry.getTypeId(clazz, ProtocolVersion.getLatest(ProtocolType.PC))] = constr;
	}

	public static TIntObjectMap<DataWatcherObject<?>> decodeData(ByteBuf from, ProtocolVersion version) {
		TIntObjectMap<DataWatcherObject<?>> map = new TIntObjectHashMap<>(10, 0.5f, -1);
		do {
			int key = from.readUnsignedByte();
			if (key == 0xFF) {
				break;
			}
			int type = from.readUnsignedByte();
			try {
				DataWatcherObject<?> object = registry[type].newInstance();
				object.readFromStream(from, version);
				map.put(key, object);
			} catch (Exception e) {
				throw new DecoderException("Unable to decode datawatcher object", e);
			}
		} while (true);
		return map;
	}

	public static void encodeData(ByteBuf to, ProtocolVersion version, TIntObjectMap<DataWatcherObject<?>> objects) {
		if (!objects.isEmpty()) {
			TIntObjectIterator<DataWatcherObject<?>> iterator = objects.iterator();
			while (iterator.hasNext()) {
				iterator.advance();
				DataWatcherObject<?> object = iterator.value();
				to.writeByte(iterator.key());
				to.writeByte(DataWatcherObjectIdRegistry.getTypeId(object, version));
				object.writeToStream(to, version);
			}
		} else {
			to.writeByte(31);
			to.writeByte(0);
			to.writeByte(0);
		}
		to.writeByte(0xFF);
	}

}
