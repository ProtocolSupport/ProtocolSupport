package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_4_5_6;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockBreakAnimation;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;

public class BlockBreakAnimation extends MiddleBlockBreakAnimation {

	public BlockBreakAnimation(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData blockbreakanimation = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_BLOCK_BREAK_ANIMATION);
		blockbreakanimation.writeInt(entityId);
		PositionSerializer.writeLegacyPositionI(blockbreakanimation, position);
		blockbreakanimation.writeByte(stage);
		codec.write(blockbreakanimation);
	}

}
