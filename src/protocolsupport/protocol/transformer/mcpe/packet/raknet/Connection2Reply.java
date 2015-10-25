package protocolsupport.protocol.transformer.mcpe.packet.raknet;

import io.netty.buffer.ByteBuf;

import java.net.InetSocketAddress;

import protocolsupport.protocol.transformer.mcpe.UDPNetworkManager;

public class Connection2Reply extends RakNetPacket {

	private int mtu;

	public Connection2Reply(InetSocketAddress address, int mtu) {
		super(address);
		this.mtu = mtu;
	}

	@Override
	public int getId() {
		return RakNetConstants.ID_OPEN_CONNECTION_REPLY_2;
	}

	@Override
	public void encode(ByteBuf buf) {
		buf.writeBytes(RakNetConstants.MAGIC);
		buf.writeLong(UDPNetworkManager.serverID);
		RakNetDataSerializer.writeAddress(buf, getClientAddress());
		buf.writeShort(mtu);
		buf.writeByte(0);
	}

}
