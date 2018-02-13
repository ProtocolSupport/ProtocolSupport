package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleHeldSlot;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class HeldSlot extends MiddleHeldSlot {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		VarNumberSerializer.readVarLong(clientdata);
		cache.setItemInHand(ItemStackSerializer.readItemStack(clientdata, connection.getVersion(), cache.getLocale(), true));
		clientdata.readByte();
		cache.setSelectedSlot(slot = clientdata.readByte());
		clientdata.readByte();
	}

}
