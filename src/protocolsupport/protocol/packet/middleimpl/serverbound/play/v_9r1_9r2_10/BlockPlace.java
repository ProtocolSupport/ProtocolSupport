package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class BlockPlace extends MiddleBlockPlace {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		position = PositionSerializer.readPosition(clientdata);
		face = VarNumberSerializer.readVarInt(clientdata);
		usedHand = VarNumberSerializer.readVarInt(clientdata);
		cX = clientdata.readUnsignedByte() / 16.0F;
		cY = clientdata.readUnsignedByte() / 16.0F;
		cZ = clientdata.readUnsignedByte() / 16.0F;
	}

}
