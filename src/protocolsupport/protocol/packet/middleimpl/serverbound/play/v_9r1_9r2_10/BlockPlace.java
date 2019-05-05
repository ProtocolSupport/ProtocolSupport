package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10;

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
		cX = clientdata.readUnsignedByte() / 16.0F;
		cY = clientdata.readUnsignedByte() / 16.0F;
		cZ = clientdata.readUnsignedByte() / 16.0F;
	}

}
