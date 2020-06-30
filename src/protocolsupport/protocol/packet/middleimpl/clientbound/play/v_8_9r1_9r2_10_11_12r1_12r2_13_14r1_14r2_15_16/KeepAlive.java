package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleKeepAlive;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class KeepAlive extends MiddleKeepAlive {

	public KeepAlive(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData keepalive = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_KEEP_ALIVE);
		if (version.isBeforeOrEq(ProtocolVersion.MINECRAFT_1_12_1)) {
			VarNumberSerializer.writeVarInt(keepalive, keepAliveId);
		} else {
			keepalive.writeLong(keepAliveId);
		}
		codec.write(keepalive);
	}

}
