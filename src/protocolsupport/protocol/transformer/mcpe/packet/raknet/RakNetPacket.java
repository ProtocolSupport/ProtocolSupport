package protocolsupport.protocol.transformer.mcpe.packet.raknet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class RakNetPacket {

	private InetSocketAddress address;
	private int id;
	private int sequenceNumber;
	private ArrayList<EncapsulatedPacket> encapsulatedPackets = new ArrayList<>();
	private ByteBuf data = Unpooled.buffer();

	public RakNetPacket(InetSocketAddress address) {
		this.address = address;
	}

	public RakNetPacket(EncapsulatedPacket epacket, InetSocketAddress address) {
		this(address);
		encapsulatedPackets.add(epacket);
		id = 0x84;
	}

	public InetSocketAddress getClientAddress() {
		return address;
	}

	public void encode(ByteBuf buf) {
		RakNetDataSerializer.writeTriad(buf, sequenceNumber);
		for (EncapsulatedPacket epacket : encapsulatedPackets) {
			epacket.encode(buf);
		}
	}

	public void decode(ByteBuf buf) {
		data.writeBytes(buf);
		id = data.readUnsignedByte();
	}

	public void decodeEncapsulated() {
		sequenceNumber = RakNetDataSerializer.readTriad(data);
		while (data.isReadable()) {
			EncapsulatedPacket packet = new EncapsulatedPacket();
			packet.decode(data);
			encapsulatedPackets.add(packet);
		}
	}

	public int getId() {
		return id;
	}

	public int getSeqNumber() {
		return sequenceNumber;
	}

	public void setSeqNumber(int number) {
		sequenceNumber = number;
	}

	public List<EncapsulatedPacket> getPackets() {
		return encapsulatedPackets;
	}

	public ByteBuf getData() {
		return data;
	}

}
