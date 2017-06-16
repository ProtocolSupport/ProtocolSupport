package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class UseItem extends MiddleBlockPlace{
	
	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		position = PositionSerializer.readPEPosition(clientdata);
		//Hotbar-slot (not needed cuz the server does that)
		VarNumberSerializer.readVarInt(clientdata);
		face = VarNumberSerializer.readVarInt(clientdata);
		//No second hand in PE yet.
		usedHand = 0;
		//Where on the block is clicked.
		cX = MiscSerializer.readLFloat(clientdata);
		cY = MiscSerializer.readLFloat(clientdata);
		cZ = MiscSerializer.readLFloat(clientdata);
		clientdata.skipBytes(0);
		/*//Entity-Position, but we don't care.
		MiscSerializer.readLFloat(clientdata);
		MiscSerializer.readLFloat(clientdata);
		MiscSerializer.readLFloat(clientdata);
		//Don't need it. Server inventory!
		VarNumberSerializer.readVarInt(clientdata);
		//Don't have inventory yet, so item is always air or 0. Read that id to clear the buf.
		VarNumberSerializer.readVarInt(clientdata);*/
		
	}
}
