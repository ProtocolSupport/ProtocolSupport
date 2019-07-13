package protocolsupport.protocol.serializer;

import java.text.MessageFormat;
import java.util.function.Supplier;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectRegistry;
import protocolsupport.protocol.types.networkentity.metadata.ReadableNetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBlockData;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectBoolean;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectByte;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectChat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectDirection;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectEntityPose;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectFloat;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectItemStack;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectNBTTagCompound;
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

public class DataWatcherSerializer {

	//while meta indexes can be now up to 255, we only use up to this amount for PE and PC
	public static final int MAX_USED_META_INDEX = 100;

	@SuppressWarnings("unchecked")
	protected static final Supplier<? extends ReadableNetworkEntityMetadataObject<?>>[] registry = new Supplier[256];
	static {
		register(NetworkEntityMetadataObjectByte::new);
		register(NetworkEntityMetadataObjectVarInt::new);
		register(NetworkEntityMetadataObjectFloat::new);
		register(NetworkEntityMetadataObjectString::new);
		register(NetworkEntityMetadataObjectChat::new);
		register(NetworkEntityMetadataObjectOptionalChat::new);
		register(NetworkEntityMetadataObjectItemStack::new);
		register(NetworkEntityMetadataObjectBoolean::new);
		register(NetworkEntityMetadataObjectVector3f::new);
		register(NetworkEntityMetadataObjectPosition::new);
		register(NetworkEntityMetadataObjectOptionalPosition::new);
		register(NetworkEntityMetadataObjectDirection::new);
		register(NetworkEntityMetadataObjectOptionalUUID::new);
		register(NetworkEntityMetadataObjectBlockData::new);
		register(NetworkEntityMetadataObjectNBTTagCompound::new);
		register(NetworkEntityMetadataObjectParticle::new);
		register(NetworkEntityMetadataObjectVillagerData::new);
		register(NetworkEntityMetadataObjectOptionalVarInt::new);
		register(NetworkEntityMetadataObjectEntityPose::new);
	}

	protected static void register(Supplier<? extends ReadableNetworkEntityMetadataObject<?>> supplier) {
		registry[NetworkEntityMetadataObjectRegistry.getTypeId(supplier.get().getClass(), ProtocolVersionsHelper.LATEST_PC)] = supplier;
	}

	public static void readDataTo(ByteBuf from, ArrayMap<NetworkEntityMetadataObject<?>> to) {
		do {
			int key = from.readUnsignedByte();
			if (key == 0xFF) {
				break;
			}
			int type = from.readUnsignedByte();
			try {
				ReadableNetworkEntityMetadataObject<?> object = registry[type].get();
				object.readFromStream(from);
				to.put(key, object);
			} catch (Exception e) {
				throw new DecoderException(MessageFormat.format("Unable to decode datawatcher object (type: {0}, index: {1})", type, key), e);
			}
		} while (true);
	}

	public static void writeData(ByteBuf to, ProtocolVersion version, String locale, ArrayMap<NetworkEntityMetadataObject<?>> objects) {
		boolean hadObject = false;
		for (int key = objects.getMinKey(); key < objects.getMaxKey(); key++) {
			NetworkEntityMetadataObject<?> object = objects.get(key);
			if (object != null) {
				hadObject = true;
				to.writeByte(key);
				to.writeByte(NetworkEntityMetadataObjectRegistry.getTypeId(object, version));
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

	public static void writeLegacyData(ByteBuf to, ProtocolVersion version, String locale, ArrayMap<NetworkEntityMetadataObject<?>> objects) {
		boolean hadObject = false;
		for (int key = objects.getMinKey(); key < objects.getMaxKey(); key++) {
			NetworkEntityMetadataObject<?> object = objects.get(key);
			if (object != null) {
				hadObject = true;
				int tk = ((NetworkEntityMetadataObjectRegistry.getTypeId(object, version) << 5) | (key & 0x1F)) & 0xFF;
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

	public static void writePEData(ByteBuf to, ProtocolVersion version, String locale, ArrayMap<NetworkEntityMetadataObject<?>> peMetadata) {
		int entries = 0;
		int writerPreIndex = to.writerIndex();
		//Fake fixed-varint length.
		to.writeZero(VarNumberSerializer.MAX_LENGTH);
		for (int key = peMetadata.getMinKey(); key < peMetadata.getMaxKey(); key++) {
			NetworkEntityMetadataObject<?> object = peMetadata.get(key);
			if (object != null) {
				VarNumberSerializer.writeVarInt(to, key);
				VarNumberSerializer.writeVarInt(to, NetworkEntityMetadataObjectRegistry.getTypeId(object, version));
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
