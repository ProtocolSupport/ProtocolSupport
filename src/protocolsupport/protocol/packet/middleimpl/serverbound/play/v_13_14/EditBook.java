package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13_14;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleEditBook;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.types.UsedHand;

public class EditBook extends MiddleEditBook {

	public EditBook(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		book = ItemStackSerializer.readItemStack(clientdata, version, cache.getAttributesCache().getLocale());
		signing = clientdata.readBoolean();
		hand = MiscSerializer.readVarIntEnum(clientdata, UsedHand.CONSTANT_LOOKUP);
	}

}
