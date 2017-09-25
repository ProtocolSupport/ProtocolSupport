package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleKeepAlive;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class KeepAlive extends MiddleKeepAlive {

	protected int clientKeepAliveId;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		clientKeepAliveId = clientdata.readInt();
		keepAliveId = cache.getKeepAliveId();
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		if (clientKeepAliveId == 0) {
			return RecyclableEmptyList.get();
		} else {
			return super.toNative();
		}
	}

}
