package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.LinkedHashMap;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.UUIDSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.NetworkEntityCache;
import protocolsupport.utils.Utils;

public abstract class MiddleEntityAttributes extends MiddleEntity {

	protected final NetworkEntityCache entityCache = cache.getEntityCache();

	protected MiddleEntityAttributes(MiddlePacketInit init) {
		super(init);
	}

	protected final LinkedHashMap<String, Attribute> attributes = new LinkedHashMap<>();

	@Override
	protected void decode(ByteBuf serverdata) {
		super.decode(serverdata);
		int attributesCount = serverdata.readInt();
		for (int i = 0; i < attributesCount; i++) {
			String key = StringSerializer.readVarIntUTF8String(serverdata);
			double value = serverdata.readDouble();
			if (value == 0.0D) {
				value = 0.00000001;
			}
			AttributeModifier[] modifiers = new AttributeModifier[VarNumberSerializer.readVarInt(serverdata)];
			for (int modifierIndex = 0; modifierIndex < modifiers.length; modifierIndex++) {
				UUID uuid = UUIDSerializer.readUUID2L(serverdata);
				double amount = serverdata.readDouble();
				int operation = serverdata.readByte();
				modifiers[modifierIndex] = new AttributeModifier(uuid, amount, operation);
			}
			attributes.put(key, new Attribute(key, value, modifiers));
		}
	}

	@Override
	protected void handle() {
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

		protected final String key;
		protected final double value;
		protected final AttributeModifier[] modifiers;

		public String getKey() {
			return key;
		}

		public double getValue() {
			return value;
		}

		public AttributeModifier[] getModifiers() {
			return modifiers;
		}

		public Attribute(String key, double value, AttributeModifier[] modifiers) {
			this.key = key;
			this.value = value;
			this.modifiers = modifiers;
		}

		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}
	}

	protected static class AttributeModifier {

		protected final UUID uuid;
		protected final double amount;
		protected final int operation;

		public AttributeModifier(UUID uuid, double amount, int operation) {
			this.uuid = uuid;
			this.amount = amount;
			this.operation = operation;
		}

		public UUID getUUID() {
			return uuid;
		}

		public double getAmount() {
			return amount;
		}

		public int getOperation() {
			return operation;
		}

		@Override
		public String toString() {
			return Utils.toStringAllFields(this);
		}
	}

}
