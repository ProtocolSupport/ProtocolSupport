package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.LinkedHashMap;
import java.util.UUID;

import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleEntitySetAttributes<T> extends MiddleEntity<T> {

	protected final LinkedHashMap<String, Attribute> attributes = new LinkedHashMap<>();

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		super.readFromServerData(serializer);
		attributes.clear();
		int attributesCount = serializer.readInt();
		for (int i = 0; i < attributesCount; i++) {
			Attribute attribute = new Attribute();
			attribute.key = serializer.readString(64);
			attribute.value = serializer.readDouble();
			attribute.modifiers = new Modifier[serializer.readVarInt()];
			for (int j = 0; j < attribute.modifiers.length; j++) {
				Modifier modifier = new Modifier();
				modifier.uuid = serializer.readUUID();
				modifier.amount = serializer.readDouble();
				modifier.operation = serializer.readByte();
				attribute.modifiers[j] = modifier;
			}
			attributes.put(attribute.key, attribute);
		}
	}

	@Override
	public void handle() {
		for (Attribute attr : attributes.values()) {
			if (attr.value == 0.0D) {
				attr.value = 0.00000001;
			}
		}
		if (entityId == cache.getSelfPlayerEntityId()) {
			Attribute attr = attributes.get("generic.maxHealth");
			if (attr != null) {
				cache.setMaxHealth((float) attr.value);
			}
		}
	}

	protected static class Attribute {
		public String key;
		public double value;
		public Modifier[] modifiers;
	}

	protected static class Modifier {
		public UUID uuid;
		public double amount;
		public int operation;
	}

}
