package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_beta;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.types.UsedHand;

public class BlockPlace extends MiddleBlockPlace {

	public BlockPlace(ConnectionImpl connection) {
		super(connection);
		hand = UsedHand.MAIN;
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		PositionSerializer.readLegacyPositionBTo(clientdata, position);
		face = clientdata.readByte();
		ItemStackSerializer.readItemStack(clientdata, version);
	}

}
