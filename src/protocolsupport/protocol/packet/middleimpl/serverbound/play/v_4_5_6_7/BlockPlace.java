package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.CancelMiddlePacketException;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.types.UsedHand;

public class BlockPlace extends MiddleBlockPlace {

	public BlockPlace(ConnectionImpl connection) {
		super(connection);
		hand = UsedHand.MAIN;
		insideblock = false;
	}

	@Override
	protected void readClientData(ByteBuf clientdata) {
		PositionSerializer.readLegacyPositionBTo(clientdata, position);
		face = clientdata.readByte();
		ItemStackSerializer.readItemStack(clientdata, version);

		byte cursorX = clientdata.readByte();
		byte cursorY = clientdata.readByte();
		byte cursorZ = clientdata.readByte();

		// Cancel "special case" packets because they are not sent on 1.9
		if(cursorX == -1 && cursorY == -1 && cursorZ == -1 && face == -1) {
			throw CancelMiddlePacketException.INSTANCE;
		}

		cX = Byte.toUnsignedInt(cursorX) / 16.0F;
		cY = Byte.toUnsignedInt(cursorY) / 16.0F;
		cZ = Byte.toUnsignedInt(cursorZ) / 16.0F;
	}

}
