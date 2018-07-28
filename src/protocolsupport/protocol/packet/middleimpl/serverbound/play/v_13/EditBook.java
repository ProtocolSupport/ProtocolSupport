package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_13;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleEditBook;
import protocolsupport.protocol.serializer.ItemStackSerializer;

public class EditBook extends MiddleEditBook {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		book = ItemStackSerializer.readItemStack(clientdata, connection.getVersion(), cache.getAttributesCache().getLocale(), true);
		signing = clientdata.readBoolean();
	}

}
