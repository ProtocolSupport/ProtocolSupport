package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_9r1_9r2_10_11_12;

import io.netty.buffer.ByteBuf;
import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockDig;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;

public class BlockDig extends MiddleBlockDig {

	@Override
	public void readFromClientData(ByteBuf clientdata, ProtocolVersion version) {
		status = MiscSerializer.readEnum(clientdata, Action.class);
		position = PositionSerializer.readPosition(clientdata);
		face = clientdata.readUnsignedByte();
	}

}
