package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;


public class UseItem extends MiddleBlockPlace {
	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		position = PositionSerializer.readPEPosition(clientdata);
		//Hotbar-slot (not needed cuz the server does that)
		VarNumberSerializer.readVarInt(clientdata);
		face = VarNumberSerializer.readSVarInt(clientdata);
		//No second hand in PE yet.
		usedHand = 0;
		//Where on the block is clicked.
		cX = MiscSerializer.readLFloat(clientdata);
		cY = MiscSerializer.readLFloat(clientdata);
		cZ = MiscSerializer.readLFloat(clientdata);
		//Don't care bout the remaining shizzle.
		clientdata.skipBytes(clientdata.readableBytes());
	}
}
