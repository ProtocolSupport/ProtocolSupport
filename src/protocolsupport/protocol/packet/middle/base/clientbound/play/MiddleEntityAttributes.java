package protocolsupport.protocol.packet.middle.base.clientbound.play;

import java.util.LinkedHashMap;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.codec.StringCodec;
import protocolsupport.protocol.codec.UUIDCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.typeremapper.basic.GenericIdSkipper;
import protocolsupport.protocol.typeremapper.utils.SkippingTable.GenericSkippingTable;
import protocolsupport.utils.reflection.ReflectionUtils;

public abstract class MiddleEntityAttributes extends MiddleEntityData {

	protected MiddleEntityAttributes(IMiddlePacketInit init) {
		super(init);
	}

	protected final GenericSkippingTable<String> attributeSkipTable = GenericIdSkipper.ATTRIBUTES.getTable(version);

	protected final LinkedHashMap<String, Attribute> attributes = new LinkedHashMap<>();

	@Override
	protected void decodeData(ByteBuf serverdata) {
		int attributesCount = VarNumberCodec.readVarInt(serverdata);
		for (int attributeIndex = 0; attributeIndex < attributesCount; attributeIndex++) {
			String key = StringCodec.readVarIntUTF8String(serverdata);
			double value = serverdata.readDouble();
			if (value == 0.0D) {
				value = 0.00000001;
			}
			AttributeModifier[] modifiers = new AttributeModifier[VarNumberCodec.readVarInt(serverdata)];
			for (int modifierIndex = 0; modifierIndex < modifiers.length; modifierIndex++) {
				UUID uuid = UUIDCodec.readUUID2L(serverdata);
				double amount = serverdata.readDouble();
				int operation = serverdata.readByte();
				modifiers[modifierIndex] = new AttributeModifier(uuid, amount, operation);
			}
			attributes.put(key, new Attribute(key, value, modifiers));
		}
	}

	@Override
	protected void handle() {
		if (entity == entityCache.getSelf()) {
			Attribute attr = attributes.get("generic.max_health");
			if (attr != null) {
				cache.getClientCache().setMaxHealth((float) attr.value);
			}
		}

		attributes.entrySet().removeIf(entry -> attributeSkipTable.isSet(entry.getKey()));
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
			return ReflectionUtils.toStringAllFields(this);
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
			return ReflectionUtils.toStringAllFields(this);
		}

	}

}
