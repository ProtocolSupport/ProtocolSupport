package protocolsupport.protocol.transformer.mcpe.packet.raknet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;

public class EncapsulatedPacket {

	public int reliability;
	public boolean hasSplit;

	public int messageIndex;
	public int orderIndex;
	public int orderChannel;

	public int splitCount;
	public int splitID;
	public int splitIndex;

	public ByteBuf data = Unpooled.buffer();

	public EncapsulatedPacket() {
	}

	public EncapsulatedPacket(ByteBuf data, int messageIndex) {
		this.reliability = 2;
		this.messageIndex = messageIndex;
		this.data.writeBytes(data);
	}

	public EncapsulatedPacket(ByteBuf data, int messageIndex, int splitID, int splitCount, int splitIndex) {
		this(data, messageIndex);
		this.hasSplit = true;
		this.splitID = splitID;
		this.splitCount = splitCount;
		this.splitIndex = splitIndex;
	}

	public void decode(ByteBuf buf) {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);

		int flags = serializer.readByte();
		reliability = (flags & 0b11100000) >> 5;
		hasSplit = (flags & 0b00010000) > 0;

		int length = (serializer.readShort() & 0xFFFF) / 8;
		if (reliability == 2 || reliability == 3 || reliability == 4 || reliability == 6 || reliability == 7) {
			messageIndex = serializer.readLTriad();
		}

		if (reliability == 1 || reliability == 3 || reliability == 4 || reliability == 7) {
			orderIndex = serializer.readLTriad();
			orderChannel = serializer.readByte() & 0xFF;
		}

		if (hasSplit) {
			splitCount = serializer.readInt();
			splitID = serializer.readShort();
			splitIndex = serializer.readInt();
		}
		data.writeBytes(serializer, length);
	}

	public void encode(ByteBuf buf) {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);

		byte flag = 0;
		flag = (byte) (flag | reliability << 5);
		if (hasSplit) {
			flag = (byte) ((flag & 0xFF) | 0x10);
		}
		serializer.writeByte(flag);
		serializer.writeShort((data.readableBytes() << 3) & 0xFFFF);
		if (reliability == 2 || reliability == 3 || reliability == 4 || reliability == 6 || reliability == 7) {
			serializer.writeLTriad(messageIndex);
		}
		if (reliability == 1 || reliability == 3 || reliability == 4 || reliability == 7) {
			serializer.writeLTriad(orderIndex);
			serializer.writeByte(orderChannel & 0xFF);
		}
		if (hasSplit) {
			serializer.writeInt(splitCount);
			serializer.writeShort(splitID & 0xFFFF);
			serializer.writeInt(splitIndex);
		}
		serializer.writeBytes(data);
	}

}
