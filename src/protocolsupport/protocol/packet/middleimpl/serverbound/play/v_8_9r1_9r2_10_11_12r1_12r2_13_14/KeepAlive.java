package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleKeepAlive;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class KeepAlive extends MiddleKeepAlive {

	public KeepAlive(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_12_1)) {
			keepAliveId = VarNumberSerializer.readVarInt(clientdata);
		} else {
			keepAliveId = clientdata.readLong();
		}
		keepAliveId = cache.getKeepAliveCache().tryConfirmKeepAlive((int) keepAliveId);
	}

}
