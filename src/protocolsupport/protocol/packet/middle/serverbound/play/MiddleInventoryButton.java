package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.IPacketData;
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
	public RecyclableCollection<? extends IPacketData> toNative() {
		return RecyclableSingletonList.create(create(windowId, button));
	}

	public static ServerBoundPacketData create(int windowId, int enchantment) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_ENCHANT_SELECT);
		creator.writeByte(windowId);
		creator.writeByte(enchantment);
		return creator;
	}

}
