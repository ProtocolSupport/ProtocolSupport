package protocolsupport.protocol.codec;

import java.text.MessageFormat;
import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.NetworkEntityMetadataObjectIdRegistry;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectRegistry;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectSerializerRegistry;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class NetworkEntityMetadataCodec {

	private NetworkEntityMetadataCodec() {
	}

	public static void readDataTo(ByteBuf from, ArrayMap<NetworkEntityMetadataObject<?>> to) {
		do {
			int key = from.readUnsignedByte();
			if (key == 0xFF) {
				break;
			}
			int type = from.readUnsignedByte();
			try {
				to.put(key, NetworkEntityMetadataObjectRegistry.get(type).getValue().read(from));
			} catch (Exception e) {
				throw new DecoderException(MessageFormat.format("Unable to decode datawatcher object (type: {0}, index: {1})", type, key), e);
			}
		} while (true);
	}

	@SuppressWarnings("unchecked")
	public static void writeData(ByteBuf to, ProtocolVersion version, String locale, NetworkEntityMetadataList objects) {
		if (objects.getSize() > 0) {
			for (int index = 0; index < objects.getSize(); index++) {
				NetworkEntityMetadataList.Entry entry = objects.get(index);
				NetworkEntityMetadataObject<?> object = entry.getObject();
				to.writeByte(entry.getObjectIndex());
				to.writeByte(NetworkEntityMetadataObjectIdRegistry.getTypeId(object, version));
				NetworkEntityMetadataObjectSerializerRegistry.get((Class<NetworkEntityMetadataObject<?>>) object.getClass()).write(to, version, locale, object);
			}
		} else {
			to.writeByte(31);
			to.writeByte(0);
			to.writeByte(0);
		}
		to.writeByte(0xFF);
	}

	@SuppressWarnings("unchecked")
	public static void writeLegacyData(ByteBuf to, ProtocolVersion version, String locale, NetworkEntityMetadataList objects) {
		if (objects.getSize() > 0) {
			for (int index = 0; index < objects.getSize(); index++) {
				NetworkEntityMetadataList.Entry entry = objects.get(index);
				NetworkEntityMetadataObject<?> object = entry.getObject();
				int tk = ((NetworkEntityMetadataObjectIdRegistry.getTypeId(object, version) << 5) | (entry.getObjectIndex() & 0x1F)) & 0xFF;
				to.writeByte(tk);
				NetworkEntityMetadataObjectSerializerRegistry.get((Class<NetworkEntityMetadataObject<?>>) object.getClass()).write(to, version, locale, object);
			}
		} else {
			to.writeByte(31);
			to.writeByte(0);
		}
		to.writeByte(127);
	}


	public static class NetworkEntityMetadataList {

		public static final NetworkEntityMetadataList EMPTY = new NetworkEntityMetadataList() {
			@Override
			public int getSize() {
				return 0;
			}
			@Override
			public Entry get(int index) {
				throw new IllegalArgumentException("Invalid index");
			}
			@Override
			public void add(int index, NetworkEntityMetadataObject<?> object) {
			}
			@Override
			public void clear() {
			}
		};

		protected Entry[] array = new Entry[16];
		protected int size;

		public int getSize() {
			return size;
		}

		public void clear() {
			size = 0;
		}

		public Entry get(int index) {
			if ((index >= 0) && (index < size)) {
				return array[index];
			}
			throw new IllegalArgumentException("Invalid index");
		}

		public void add(int index, NetworkEntityMetadataObject<?> object) {
			if (size >= array.length) {
				array = Arrays.copyOfRange(array, 0, array.length * 2);
			}
			Entry entry = array[size];
			if (entry != null) {
				entry.set(index, object);
			} else {
				array[size] = new Entry(index, object);
			}
			size++;
		}

		public static class Entry {
			protected int index;
			protected NetworkEntityMetadataObject<?> object;
			protected Entry(int index, NetworkEntityMetadataObject<?> object) {
				set(index, object);
			}
			protected void set(int index, NetworkEntityMetadataObject<?> object) {
				this.index = index;
				this.object = object;
			}
			public int getObjectIndex() {
				return index;
			}
			public NetworkEntityMetadataObject<?> getObject() {
				return object;
			}
		}

	}

}
