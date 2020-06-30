package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2_15_16;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleSetViewCenter;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;

public class SetViewCenter extends MiddleSetViewCenter {

	public SetViewCenter(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	protected void writeToClient() {
		ClientBoundPacketData setviewcenter = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_SET_VIEW_CENTER);
		PositionSerializer.writeVarIntChunkCoord(setviewcenter, chunk);
		codec.write(setviewcenter);
	}

}
