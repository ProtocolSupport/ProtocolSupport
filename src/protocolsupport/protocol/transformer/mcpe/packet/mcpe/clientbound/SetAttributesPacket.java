package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.serializer.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;

public class SetAttributesPacket implements ClientboundPEPacket {

	protected int entityId;
	protected AttributeRecord[] records;

	public SetAttributesPacket(int entityId, AttributeRecord... records) {
		this.entityId = entityId;
		this.records = records;
	}

	@Override
	public int getId() {
		return PEPacketIDs.UPDATE_ATTRIBUTES_PACKET;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		serializer.writeLong(entityId);
		serializer.writeShort(records.length);
		for (AttributeRecord record : records) {
			serializer.writeFloat(record.minValue);
			serializer.writeFloat(record.maxValue);
			serializer.writeFloat(record.value);
			serializer.writeString(record.name);
		}
		return this;
	}

	public static class AttributeRecord {
		protected String name;
		protected float minValue;
		protected float maxValue;
		protected float value;

		public AttributeRecord(String name, float minValue, float maxValue, float value) {
			this.name = name;
			this.minValue = minValue;
			this.maxValue = maxValue;
			this.value = value;
		}
	}

}
