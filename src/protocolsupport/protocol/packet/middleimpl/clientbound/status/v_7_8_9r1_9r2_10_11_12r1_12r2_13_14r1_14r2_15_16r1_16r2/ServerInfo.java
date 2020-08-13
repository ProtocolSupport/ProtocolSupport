package protocolsupport.protocol.packet.middleimpl.clientbound.status.v_7_8_9r1_9r2_10_11_12r1_12r2_13_14r1_14r2_15_16r1_16r2;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.status.MiddleServerInfo;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.StringSerializer;
import protocolsupport.protocol.types.pingresponse.PingResponse;

public class ServerInfo extends MiddleServerInfo {

	public ServerInfo(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData serverinfo = ClientBoundPacketData.create(PacketType.CLIENTBOUND_STATUS_SERVER_INFO);
		StringSerializer.writeVarIntUTF8String(serverinfo, PingResponse.toJson(version, ping));
		codec.write(serverinfo);
	}

}
