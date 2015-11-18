package protocolsupport.protocol.transformer.mcpe.packet.raknet;

import java.net.InetSocketAddress;

import io.netty.buffer.ByteBuf;

public class NACK extends RakNetPacket {

	public NACK(InetSocketAddress address) {
		super(address);
	}

	@Override
	public int getId() {
		return RakNetConstants.NACK;
	}

	public int idstart;
	public int idfinish;

	@Override
	public void encode(ByteBuf buf) {
		buf.writeShort(1);
		buf.writeByte(0);
		RakNetDataSerializer.writeTriad(buf, idstart);
		RakNetDataSerializer.writeTriad(buf, idfinish);
	}

}
