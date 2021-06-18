package protocolsupport.protocol.serializer;

import java.text.MessageFormat;
import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectDeserializer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectRegistry;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBlockData;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBoolean;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectChat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectDirection;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectEntityPose;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectFloat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectItemStack;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectNBT;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalChat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalPosition;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalUUID;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectOptionalVarInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectParticle;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectPosition;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectString;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVector3f;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVillagerData;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class NetworkEntityMetadataSerializer {

	private NetworkEntityMetadataSerializer() {
	}

	@SuppressWarnings("unchecked")
	private static final NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObject<?>>[] registry = new NetworkEntityMetadataObjectDeserializer[256];
	static {
		register(NetworkEntityMetadataObjectByte.class, NetworkEntityMetadataObjectDeserializer.BYTE);
		register(NetworkEntityMetadataObjectVarInt.class, NetworkEntityMetadataObjectDeserializer.VARINT);
		register(NetworkEntityMetadataObjectFloat.class, NetworkEntityMetadataObjectDeserializer.FLOAT);
		register(NetworkEntityMetadataObjectString.class, NetworkEntityMetadataObjectDeserializer.STRING);
		register(NetworkEntityMetadataObjectChat.class, NetworkEntityMetadataObjectDeserializer.CHAT);
		register(NetworkEntityMetadataObjectOptionalChat.class, NetworkEntityMetadataObjectDeserializer.OPT_CHAT);
		register(NetworkEntityMetadataObjectItemStack.class, NetworkEntityMetadataObjectDeserializer.ITEMSTACK);
		register(NetworkEntityMetadataObjectBoolean.class, NetworkEntityMetadataObjectDeserializer.BOOLEAN);
		register(NetworkEntityMetadataObjectVector3f.class, NetworkEntityMetadataObjectDeserializer.VECTOR3F);
		register(NetworkEntityMetadataObjectPosition.class, NetworkEntityMetadataObjectDeserializer.POSITION);
		register(NetworkEntityMetadataObjectOptionalPosition.class, NetworkEntityMetadataObjectDeserializer.OPT_POSITION);
		register(NetworkEntityMetadataObjectDirection.class, NetworkEntityMetadataObjectDeserializer.DIRECTION);
		register(NetworkEntityMetadataObjectOptionalUUID.class, NetworkEntityMetadataObjectDeserializer.OPT_UUID);
		register(NetworkEntityMetadataObjectBlockData.class, NetworkEntityMetadataObjectDeserializer.BLOCKDATA);
		register(NetworkEntityMetadataObjectNBT.class, NetworkEntityMetadataObjectDeserializer.NBT);
		register(NetworkEntityMetadataObjectParticle.class, NetworkEntityMetadataObjectDeserializer.PARTICLE);
		register(NetworkEntityMetadataObjectVillagerData.class, NetworkEntityMetadataObjectDeserializer.VILLAGER_DATA);
		register(NetworkEntityMetadataObjectOptionalVarInt.class, NetworkEntityMetadataObjectDeserializer.OPT_VARINT);
		register(NetworkEntityMetadataObjectEntityPose.class, NetworkEntityMetadataObjectDeserializer.ENTITY_POSE);
	}

	@SuppressWarnings("unchecked")
	private static <T extends NetworkEntityMetadataObject<?>> void register(Class<T> clazz, NetworkEntityMetadataObjectDeserializer<T> deserializer) {
		registry[NetworkEntityMetadataObjectRegistry.getTypeId(clazz, ProtocolVersionsHelper.LATEST_PC)] = (NetworkEntityMetadataObjectDeserializer<NetworkEntityMetadataObject<?>>) deserializer;
	}

	public static void readDataTo(ByteBuf from, ArrayMap<NetworkEntityMetadataObject<?>> to) {
		do {
			int key = from.readUnsignedByte();
			if (key == 0xFF) {
				break;
			}
			int type = from.readUnsignedByte();
			try {
				to.put(key, registry[type].read(from));
			} catch (Exception e) {
				throw new DecoderException(MessageFormat.format("Unable to decode datawatcher object (type: {0}, index: {1})", type, key), e);
			}
		} while (true);
	}

	public static void writeData(ByteBuf to, ProtocolVersion version, String locale, NetworkEntityMetadataList objects) {
		if (objects.getSize() > 0) {
			for (int index = 0; index < objects.getSize(); index++) {
				NetworkEntityMetadataList.Entry entry = objects.get(index);
				NetworkEntityMetadataObject<?> object = entry.getObject();
				to.writeByte(entry.getObjectIndex());
				to.writeByte(NetworkEntityMetadataObjectRegistry.getTypeId(object, version));
				object.writeToStream(to, version, locale);
			}
		} else {
			to.writeByte(31);
			to.writeByte(0);
			to.writeByte(0);
		}
		to.writeByte(0xFF);
	}

	public static void writeLegacyData(ByteBuf to, ProtocolVersion version, String locale, NetworkEntityMetadataList objects) {
		if (objects.getSize() > 0) {
			for (int index = 0; index < objects.getSize(); index++) {
				NetworkEntityMetadataList.Entry entry = objects.get(index);
				NetworkEntityMetadataObject<?> object = entry.getObject();
				int tk = ((NetworkEntityMetadataObjectRegistry.getTypeId(object, version) << 5) | (entry.getObjectIndex() & 0x1F)) & 0xFF;
				to.writeByte(tk);
				object.writeToStream(to, version, locale);
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
