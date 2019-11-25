package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_14r1_14r2;

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
		ClientBoundPacketData blockbreakanimation = codec.allocClientBoundPacketData(PacketType.CLIENTBOUND_PLAY_BLOCK_BREAK_ANIMATION);
		VarNumberSerializer.writeVarInt(blockbreakanimation, entityId);
		PositionSerializer.writePosition(blockbreakanimation, position);
		blockbreakanimation.writeByte(stage);
		codec.write(blockbreakanimation);
	}

}
