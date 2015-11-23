package protocolsupport.protocol.transformer.mcpe.packet.raknet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;

public class EncapsulatedPacket {

	public int reliability;
	public boolean hasSplit;

	public int messageIndex;

	public int orderChannel;
	public int orderIndex;

	public int splitCount;
	public int splitID;
	public int splitIndex;

	public ByteBuf data = Unpooled.buffer();

	public EncapsulatedPacket() {
	}

	public EncapsulatedPacket(ByteBuf data) {
		this.reliability = 0;
		this.data.writeBytes(data);
	}

	public EncapsulatedPacket(ByteBuf data, int splitID, int splitCount, int splitIndex) {
		this(data);
		this.hasSplit = true;
		this.splitID = splitID;
		this.splitCount = splitCount;
		this.splitIndex = splitIndex;
	}

	public EncapsulatedPacket(ByteBuf data, int messageIndex) {
		this.reliability = 2;
		this.data.writeBytes(data);
		this.messageIndex = messageIndex;
	}

	public EncapsulatedPacket(ByteBuf data, int messageIndex, int splitID, int splitCount, int splitIndex) {
		this(data, messageIndex);
		this.hasSplit = true;
		this.splitID = splitID;
		this.splitCount = splitCount;
		this.splitIndex = splitIndex;
	}

	public EncapsulatedPacket(ByteBuf data, int messageIndex, int orderIndex) {
		this.reliability = 3;
		this.data.writeBytes(data);
		this.messageIndex = messageIndex;
		this.orderIndex = orderIndex;
	}

	public EncapsulatedPacket(ByteBuf data, int messageIndex, int orderIndex, int splitID, int splitCount, int splitIndex) {
		this(data, messageIndex, orderIndex);
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

		//yes, all this shit is important, because you can have a length of 273 bits or so, how does this even happen?
		int length = (int) Math.ceil(((double) serializer.readUnsignedShort()) / 8.0D);

		if (reliability > 0) {
			if (reliability >= 2 && reliability != 5) {
				messageIndex = RakNetDataSerializer.readTriad(buf);
			}
			if (reliability <= 4 && reliability != 2) {
				orderIndex = RakNetDataSerializer.readTriad(buf);
				orderChannel = serializer.readUnsignedByte();
			}
		}

		if (hasSplit) {
			splitCount = serializer.readInt();
			splitID = serializer.readShort();
			splitIndex = serializer.readInt();
		}

		data.writeBytes(serializer, length);
	}

	public void encode(ByteBuf buf) {
		data.readerIndex(0);

		byte flag = 0;
		flag = (byte) (flag | reliability << 5);
		if (hasSplit) {
			flag = (byte) ((flag & 0xFF) | 0x10);
		}
		buf.writeByte(flag);

		buf.writeShort((data.readableBytes() << 3) & 0xFFFF);

		if (reliability > 0) {
			if (reliability >= 2 && reliability != 5) {
				RakNetDataSerializer.writeTriad(buf, messageIndex);
			}
			if (reliability <= 4 && reliability != 2) {
				RakNetDataSerializer.writeTriad(buf, orderIndex);
				buf.writeByte(orderChannel);
			}
		}

		if (hasSplit) {
			buf.writeInt(splitCount);
			buf.writeShort(splitID & 0xFFFF);
			buf.writeInt(splitIndex);
		}

		buf.writeBytes(data);
	}

}
