package protocolsupport.protocol.packet.middle.clientbound.play;

import java.util.LinkedHashMap;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolType;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleEntitySetAttributes extends MiddleEntity {

	protected final LinkedHashMap<String, Attribute> attributes = new LinkedHashMap<>();

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		super.readFromServerData(serverdata);
		attributes.clear();
		int attributesCount = serverdata.readInt();
		for (int i = 0; i < attributesCount; i++) {
			Attribute attribute = new Attribute();
			attribute.key = StringSerializer.readString(serverdata, ProtocolVersion.getLatest(ProtocolType.PC), 64);
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

	public static class Attribute {
		public String key;
		public double value;
		public Modifier[] modifiers;
	}

	public static class Modifier {
		public UUID uuid;
		public double amount;
		public int operation;
	}

}
