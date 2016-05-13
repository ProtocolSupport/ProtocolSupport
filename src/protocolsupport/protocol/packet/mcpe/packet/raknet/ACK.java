package protocolsupport.protocol.packet.mcpe.packet.raknet;

import io.netty.buffer.ByteBuf;

import java.net.InetSocketAddress;

public class ACK extends RakNetPacket {

	public ACK(InetSocketAddress address) {
		super(address);
	}

	@Override
	public int getId() {
		return RakNetConstants.ACK;
	}

	public int id;

	@Override
	public void encode(ByteBuf buf) {
		buf.writeShort(1);
		buf.writeByte(1);
		RakNetDataSerializer.writeTriad(buf, id);
	}

}
