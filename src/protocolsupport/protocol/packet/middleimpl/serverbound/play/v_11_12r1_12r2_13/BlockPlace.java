package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_11_12r1_12r2_13;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.types.UsedHand;

public class BlockPlace extends MiddleBlockPlace {

	public BlockPlace(ConnectionImpl connection) {
		super(connection);
		insideblock = false;
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		PositionSerializer.readLegacyPositionLTo(clientdata, position);
		face = VarNumberSerializer.readVarInt(clientdata);
		hand = MiscSerializer.readVarIntEnum(clientdata, UsedHand.CONSTANT_LOOKUP);
		cX = clientdata.readFloat();
		cY = clientdata.readFloat();
		cZ = clientdata.readFloat();
	}

}