package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ClientBoundPacket;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.ServerPlatform;

public class KeepAlive extends ClientBoundMiddlePacket {

	public KeepAlive(ConnectionImpl connection) {
		super(connection);
	}

	protected long keepAliveId;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		keepAliveId = serverdata.readLong();
	}

	@Override
	public RecyclableCollection<ClientBoundPacketData> toData() {
		connection.receivePacket(ServerPlatform.get().getPacketFactory().createInboundKeepAlivePacket(keepAliveId));
		return RecyclableSingletonList.create(ClientBoundPacketData.create(ClientBoundPacket.PLAY_KEEP_ALIVE_ID));
	}

}
