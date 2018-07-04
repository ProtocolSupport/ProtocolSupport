package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleInventoryEnchant extends ServerBoundMiddlePacket {

	protected int windowId;
	protected int enchantment;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(create(windowId, enchantment));
	}

	public static ServerBoundPacketData create(int windowId, int enchantment) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_ENCHANT_SELECT);
		creator.writeByte(windowId);
		creator.writeByte(enchantment);
		return creator;
	}

}
