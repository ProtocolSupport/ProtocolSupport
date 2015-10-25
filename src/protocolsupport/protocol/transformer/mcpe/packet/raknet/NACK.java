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
		int count = getCount();
		buf.writeShort(count);
		for (int i = 0; i < count; i++) {
			int current = idstart + i * 4096;
			buf.writeByte(0);
			RakNetDataSerializer.writeTriad(buf, current);
			if (idfinish - current < 4096) {
				RakNetDataSerializer.writeTriad(buf, idfinish);
			} else {
				RakNetDataSerializer.writeTriad(buf, current + 4096);
			}
		}
	}

	private int getCount() {
		return (idfinish - idstart) / 4096 + 1;
	}

}
