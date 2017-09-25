package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleKeepAlive;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableEmptyList;

public class KeepAlive extends MiddleKeepAlive {

	protected int clientKeepAliveId;

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		if (connection.getVersion().isBeforeOrEq(ProtocolVersion.MINECRAFT_1_12_1)) {
			clientKeepAliveId = VarNumberSerializer.readVarInt(clientdata);
			keepAliveId = cache.getKeepAliveId();
		} else {
			keepAliveId = clientdata.readLong();
		}
	}

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		if (connection.getVersion().isBeforeOrEq(ProtocolVersion.MINECRAFT_1_12_1) && clientKeepAliveId == 0) {
			return RecyclableEmptyList.get();
		} else {
			return super.toNative();
		}
	}

}
