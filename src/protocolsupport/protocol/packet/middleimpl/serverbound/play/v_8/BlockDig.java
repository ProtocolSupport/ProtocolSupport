package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_8;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockDig;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;

public class BlockDig extends MiddleBlockDig {

	public BlockDig(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		status = MiscSerializer.readByteEnum(clientdata, Action.CONSTANT_LOOKUP);
		PositionSerializer.readLegacyPositionLTo(clientdata, position);
		face = clientdata.readUnsignedByte();
	}

}
