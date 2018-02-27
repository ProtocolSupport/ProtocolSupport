package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleHeldSlot;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.pe.PEInventoryCache;

public class HeldSlot extends MiddleHeldSlot {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		PEInventoryCache invCache = cache.getPEDataCache().getInventoryCache();
		VarNumberSerializer.readVarLong(clientdata);
		invCache.setItemInHand(ItemStackSerializer.readItemStack(clientdata, connection.getVersion(), cache.getLocale(), true));
		clientdata.readByte();
		invCache.setSelectedSlot(slot = clientdata.readByte());
		clientdata.readByte();
	}

}
