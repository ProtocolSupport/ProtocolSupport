package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.LinkedHashMap;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.Utils;

public abstract class MiddleEntitySetAttributes extends MiddleEntity {

	public MiddleEntitySetAttributes(ConnectionImpl connection) {
		super(connection);
	}

	protected final LinkedHashMap<String, Attribute> attributes = new LinkedHashMap<>();

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		attributes.clear();
		int attributesCount = serverdata.readInt();
		for (int i = 0; i < attributesCount; i++) {
			Attribute attribute = new Attribute();
			attribute.key = StringSerializer.readVarIntUTF8String(serverdata);
			attribute.value = serverdata.readDouble();
			attribute.modifiers = new Modifier[VarNumberSerializer.readVarInt(serverdata)];
			for (int j = 0; j < attribute.modifiers.length; j++) {
				Modifier modifier = new Modifier();
				modifier.uuid = MiscSerializer.readUUID(serverdata);
				modifier.amount = serverdata.readDouble();
				modifier.operation = serverdata.readByte();
				attribute.modifiers[j] = modifier;
			}
			attributes.put(attribute.key, attribute);
		}
	}

	@Override
	public boolean postFromServerRead() {
		for (Attribute attr : attributes.values()) {
			if (attr.value == 0.0D) {
				attr.value = 0.00000001;
			}
		}
		if (entityId == cache.getWatchedEntityCache().getSelfPlayerEntityId()) {
			Attribute attr = attributes.get("generic.maxHealth");
			if (attr != null) {
				cache.getAttributesCache().setMaxHealth((float) attr.value);
			}
		}
		return true;
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
