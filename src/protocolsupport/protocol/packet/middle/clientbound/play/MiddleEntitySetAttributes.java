package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.UUID;

import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleEntitySetAttributes<T> extends MiddleEntity<T> {

	protected Attribute[] attributes;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		super.readFromServerData(serializer);
		attributes = new Attribute[serializer.readInt()];
		for (int i = 0; i < attributes.length; i++) {
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
			attributes[i] = attribute;
		}
	}

	@Override
	public void handle() {
		if (entityId == cache.getSelfPlayerEntityId()) {
			for (Attribute attr : attributes) {
				if (attr.key.equals("generic.maxHealth")) {
					cache.setMaxHealth((float) attr.value);
				}
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
