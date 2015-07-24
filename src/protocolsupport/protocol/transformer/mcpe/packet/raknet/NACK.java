package protocolsupport.protocol.transformer.mcpe.packet.raknet;

import java.net.InetSocketAddress;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;

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
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		int count = getCount();
		serializer.writeShort(count);
		for (int i = 0; i < count; i++) {
			int current = idstart + i * 4096;
			serializer.writeByte(0);
			serializer.writeLTriad(current);
			if (idfinish - current < 4096) {
				serializer.writeLTriad(idfinish);
			} else {
				serializer.writeLTriad(current + 4096);
			}
		}
	}

	private int getCount() {
		return (idfinish - idstart) / 4096 + 1;
	}

}
