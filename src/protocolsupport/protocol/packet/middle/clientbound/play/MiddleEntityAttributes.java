package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.LinkedHashMap;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.UUIDSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.utils.Utils;

public abstract class MiddleEntityAttributes extends MiddleEntity {

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	public MiddleEntityAttributes(ConnectionImpl connection) {
		super(connection);
	}

	protected final LinkedHashMap<String, Attribute> attributes = new LinkedHashMap<>();

	@Override
	protected void readServerData(ByteBuf serverdata) {
		super.readServerData(serverdata);
		int attributesCount = serverdata.readInt();
		for (int i = 0; i < attributesCount; i++) {
			Attribute attribute = new Attribute();
			attribute.key = StringSerializer.readVarIntUTF8String(serverdata);
			attribute.value = serverdata.readDouble();
			if (attribute.value == 0.0D) {
				attribute.value = 0.00000001;
			}
			attribute.modifiers = new Modifier[VarNumberSerializer.readVarInt(serverdata)];
			for (int j = 0; j < attribute.modifiers.length; j++) {
				Modifier modifier = new Modifier();
				modifier.uuid = UUIDSerializer.readUUID2L(serverdata);
				modifier.amount = serverdata.readDouble();
				modifier.operation = serverdata.readByte();
				attribute.modifiers[j] = modifier;
			}
			attributes.put(attribute.key, attribute);
		}
	}

	@Override
	public void handleReadData() {
		if (entityId == entityCache.getSelfId()) {
			Attribute attr = attributes.get("generic.max_health");
			if (attr != null) {
				cache.getClientCache().setMaxHealth((float) attr.value);
			}
		}
	}

	@Override
	protected void cleanup() {
		attributes.clear();
	}

	protected static class Attribute {
		public String key;
		public double value;
		public Modifier[] modifiers;
		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}
	}

	protected static class Modifier {
		public UUID uuid;
		public double amount;
		public int operation;
		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}
	}

}
