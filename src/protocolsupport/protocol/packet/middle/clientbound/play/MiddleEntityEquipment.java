package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public abstract class MiddleEntityEquipment extends ClientBoundMiddlePacket {

	protected int entityId;
	protected int slot;
	protected ItemStackWrapper itemstack;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		entityId = VarNumberSerializer.readVarInt(serverdata);
		slot = VarNumberSerializer.readVarInt(serverdata);
		itemstack = ItemStackSerializer.readItemStack(serverdata, ProtocolVersionsHelper.LATEST_PC, cache.getLocale(), false);
	}

}
