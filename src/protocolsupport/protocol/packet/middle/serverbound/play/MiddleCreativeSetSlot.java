package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;
import protocolsupport.zplatform.itemstack.ItemStackWrapper;

public abstract class MiddleCreativeSetSlot extends ServerBoundMiddlePacket {

	protected int slot;
	protected ItemStackWrapper itemstack;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(create(cache.getLocale(), slot, itemstack));
	}
	
	public static ServerBoundPacketData create(String locale, int slot, ItemStackWrapper itemstack) {
		System.err.println("What?!: " + slot + " - " + itemstack.toString());
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_CREATIVE_SET_SLOT);
		creator.writeShort(slot);
		ItemStackSerializer.writeItemStack(creator, ProtocolVersionsHelper.LATEST_PC, locale, itemstack, false);
		return creator;
	}

}
