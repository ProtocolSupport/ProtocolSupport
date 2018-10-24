package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_4_5_6_7;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleBlockPlace;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.protocol.utils.types.UsedHand;

public class BlockPlace extends MiddleBlockPlace {

	public BlockPlace(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		PositionSerializer.readLegacyPositionBTo(clientdata, position);
		face = clientdata.readByte();
		hand = UsedHand.MAIN;
		ItemStackSerializer.readItemStack(clientdata, connection.getVersion(), I18NData.DEFAULT_LOCALE, false);
		cX = clientdata.readUnsignedByte() / 16.0F;
		cY = clientdata.readUnsignedByte() / 16.0F;
		cZ = clientdata.readUnsignedByte() / 16.0F;
	}

}
