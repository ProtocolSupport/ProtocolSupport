package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_beta;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleInventoryClick;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.utils.types.NetworkItemStack;

public class InventoryClick extends MiddleInventoryClick {

	public InventoryClick(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		windowId = clientdata.readUnsignedByte();
		slot = clientdata.readShort();
		button = clientdata.readUnsignedByte();
		actionNumber = clientdata.readShort();
		mode = clientdata.readUnsignedByte();
		itemstack = ItemStackSerializer.readItemStack(clientdata, version, cache.getAttributesCache().getLocale());
		if (button == 0 && mode == 1) {
			itemstack = NetworkItemStack.NULL;
		}
	}

}
