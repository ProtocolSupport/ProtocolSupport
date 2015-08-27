package protocolsupport.protocol.transformer.mcpe.packet.mcpe.clientbound;

import io.netty.buffer.ByteBuf;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ClientboundPEPacket;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;

public class ServerHandshakePacket implements ClientboundPEPacket {

	protected InetSocketAddress addr;
	protected long session1;
	protected long session2;

	public ServerHandshakePacket(InetSocketAddress addr, long session1, long session2) {
		this.addr = addr;
		this.session1 = session1;
		this.session2 = session2;
	}

	@Override
	public int getId() {
		return PEPacketIDs.SERVER_HANDSHAKE;
	}

	@Override
	public ClientboundPEPacket encode(ByteBuf buf) throws Exception {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		serializer.writeAddress(addr);
		serializer.writeShort(0);
		serializer.writeAddress(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 0));
		serializer.writeAddress(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), 0));
		serializer.writeAddress(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), 0));
		serializer.writeAddress(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), 0));
		serializer.writeAddress(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), 0));
		serializer.writeAddress(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), 0));
		serializer.writeAddress(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), 0));
		serializer.writeAddress(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), 0));
		serializer.writeAddress(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), 0));
		serializer.writeAddress(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), 0));
		serializer.writeLong(session1);
		serializer.writeLong(session2);
        return this;
	}

}
