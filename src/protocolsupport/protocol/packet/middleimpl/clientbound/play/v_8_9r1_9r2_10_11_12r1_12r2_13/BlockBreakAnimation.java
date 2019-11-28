package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_8_9r1_9r2_10_11_12r1_12r2_13;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleBlockBreakAnimation;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class BlockBreakAnimation extends MiddleBlockBreakAnimation {

	public BlockBreakAnimation(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void writeToClient() {
		ClientBoundPacketData blockbreakanimation = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_BLOCK_BREAK_ANIMATION);
		VarNumberSerializer.writeVarInt(blockbreakanimation, entityId);
		PositionSerializer.writeLegacyPositionL(blockbreakanimation, position);
		blockbreakanimation.writeByte(stage);
		codec.write(blockbreakanimation);
	}

}
