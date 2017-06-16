package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_11_12;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class BlockPlace extends MiddleBlockPlace {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		position = PositionSerializer.readPosition(clientdata);
		face = VarNumberSerializer.readVarInt(clientdata);
		usedHand = VarNumberSerializer.readVarInt(clientdata);
		cX = clientdata.readFloat();
		cY = clientdata.readFloat();
		cZ = clientdata.readFloat();
	}

}