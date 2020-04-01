package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_beta;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockAction;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;

public class BlockAction extends MiddleBlockAction {

	public BlockAction(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData blockaction = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_BLOCK_ACTION);
		PositionSerializer.writeLegacyPositionS(blockaction, position);
		blockaction.writeByte(actionId);
		blockaction.writeByte(actionParam);
		codec.write(blockaction);
	}

}
