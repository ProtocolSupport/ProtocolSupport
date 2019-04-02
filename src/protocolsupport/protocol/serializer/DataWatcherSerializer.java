package protocolsupport.protocol.serializer;

import java.text.MessageFormat;
import java.util.function.Supplier;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.DataWatcherObjectIdRegistry;
import protocolsupport.protocol.utils.datawatcher.ReadableDataWatcherObject;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBlockData;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectBoolean;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectByte;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectChat;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectDirection;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectFloat;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectItemStack;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectNBTTagCompound;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectOptionalChat;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectOptionalPosition;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectOptionalUUID;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectParticle;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectPosition;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectString;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVarInt;
import protocolsupport.protocol.utils.datawatcher.objects.DataWatcherObjectVector3f;
import protocolsupport.utils.CollectionsUtils.ArrayMap;

public class DataWatcherSerializer {

	//while meta indexes can be now up to 255, we only use up to this amount for PE and PC
	public static final int MAX_USED_META_INDEX = 100;

	@SuppressWarnings("unchecked")
	private static final Supplier<? extends ReadableDataWatcherObject<?>>[] registry = new Supplier[256];
	static {
		register(DataWatcherObjectByte::new);
		register(DataWatcherObjectVarInt::new);
		register(DataWatcherObjectFloat::new);
		register(DataWatcherObjectString::new);
		register(DataWatcherObjectChat::new);
		register(DataWatcherObjectOptionalChat::new);
		register(DataWatcherObjectItemStack::new);
		register(DataWatcherObjectBoolean::new);
		register(DataWatcherObjectVector3f::new);
		register(DataWatcherObjectPosition::new);
		register(DataWatcherObjectOptionalPosition::new);
		register(DataWatcherObjectDirection::new);
		register(DataWatcherObjectOptionalUUID::new);
		register(DataWatcherObjectBlockData::new);
		register(DataWatcherObjectNBTTagCompound::new);
		register(DataWatcherObjectParticle::new);
	}

	private static void register(Supplier<? extends ReadableDataWatcherObject<?>> supplier) {
		registry[DataWatcherObjectIdRegistry.getTypeId(supplier.get().getClass(), ProtocolVersionsHelper.LATEST_PC)] = supplier;
	}

	public static void readDataTo(ByteBuf from, ArrayMap<DataWatcherObject<?>> to) {
		do {
			int key = from.readUnsignedByte();
			if (key == 0xFF) {
				break;
			}
			int type = from.readUnsignedByte();
			try {
				ReadableDataWatcherObject<?> object = registry[type].get();
				object.readFromStream(from);
				to.put(key, object);
			} catch (Exception e) {
				throw new DecoderException(MessageFormat.format("Unable to decode datawatcher object (type: {0}, index: {1})", type, key), e);
			}
		} while (true);
	}

	public static void writeData(ByteBuf to, ProtocolVersion version, String locale, ArrayMap<DataWatcherObject<?>> objects) {
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

	public static void writeLegacyData(ByteBuf to, ProtocolVersion version, String locale, ArrayMap<DataWatcherObject<?>> objects) {
		boolean hadObject = false;
		for (int key = objects.getMinKey(); key < objects.getMaxKey(); key++) {
			DataWatcherObject<?> object = objects.get(key);
			if (object != null) {
				hadObject = true;
				int tk = ((DataWatcherObjectIdRegistry.getTypeId(object, version) << 5) | (key & 0x1F)) & 0xFF;
				to.writeByte(tk);
				object.writeToStream(to, version, locale);
			}
		}
		if (!hadObject) {
			to.writeByte(31);
			to.writeByte(0);
		}
		to.writeByte(127);
	}

	public static void writePEData(ByteBuf to, ProtocolVersion version, String locale, ArrayMap<DataWatcherObject<?>> peMetadata) {
		int entries = 0;
		int writerPreIndex = to.writerIndex();
		//Fake fixed-varint length.
		to.writeZero(VarNumberSerializer.MAX_LENGTH);
		for (int key = peMetadata.getMinKey(); key < peMetadata.getMaxKey(); key++) {
			DataWatcherObject<?> object = peMetadata.get(key);
			if (object != null) {
				VarNumberSerializer.writeVarInt(to, key);
				VarNumberSerializer.writeVarInt(to, DataWatcherObjectIdRegistry.getTypeId(object, version));
				object.writeToStream(to, version, locale);
				entries++;
			}
		}
		int writerPostIndex = to.writerIndex();
		//Overwrite fake length.
		to.writerIndex(writerPreIndex);
		VarNumberSerializer.writeFixedSizeVarInt(to, entries);
		//Return writer.
		to.writerIndex(writerPostIndex);
	}

}
