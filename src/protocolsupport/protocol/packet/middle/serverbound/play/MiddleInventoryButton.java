package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.ServerBoundPacket;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.utils.recyclable.RecyclableCollection;
import protocolsupport.utils.recyclable.RecyclableSingletonList;

public abstract class MiddleInventoryButton extends ServerBoundMiddlePacket {

	public MiddleInventoryButton(ConnectionImpl connection) {
		super(connection);
	}

	protected int windowId;
	protected int button;

	@Override
	public RecyclableCollection<ServerBoundPacketData> toNative() {
		return RecyclableSingletonList.create(create(windowId, button));
	}

	public static ServerBoundPacketData create(int windowId, int enchantment) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacket.PLAY_ENCHANT_SELECT);
		creator.writeByte(windowId);
		creator.writeByte(enchantment);
		return creator;
	}

}
