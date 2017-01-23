package protocolsupport.protocol.utils.datawatcher;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import gnu.trove.iterator.TIntObjectIterator;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;
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
		registry[constr.newInstance().getTypeId(ProtocolVersion.getLatest())] = constr;
	}

	public static TIntObjectMap<DataWatcherObject<?>> decodeData(ProtocolSupportPacketDataSerializer serializer) {
		TIntObjectMap<DataWatcherObject<?>> map = new TIntObjectHashMap<>(10, 0.5f, -1);
		do {
			int key = serializer.readUnsignedByte();
			if (key == 0xFF) {
				break;
			}
			int type = serializer.readUnsignedByte();
			try {
				DataWatcherObject<?> object = registry[type].newInstance();
				object.readFromStream(serializer);
				map.put(key, object);
			} catch (Exception e) {
				throw new DecoderException("Unable to decode datawatcher object", e);
			}
		} while (true);
		return map;
	}

	public static void encodeData(TIntObjectMap<DataWatcherObject<?>> objects, ProtocolSupportPacketDataSerializer serializer) {
		if (!objects.isEmpty()) {
			TIntObjectIterator<DataWatcherObject<?>> iterator = objects.iterator();
			while (iterator.hasNext()) {
				iterator.advance();
				DataWatcherObject<?> object = iterator.value();
				serializer.writeByte(iterator.key());
				serializer.writeByte(object.getTypeId(serializer.getVersion()));
				object.writeToStream(serializer);
			}
		} else {
			serializer.writeByte(31);
			serializer.writeByte(0);
			serializer.writeByte(0);
		}
		serializer.writeByte(0xFF);
	}

}
