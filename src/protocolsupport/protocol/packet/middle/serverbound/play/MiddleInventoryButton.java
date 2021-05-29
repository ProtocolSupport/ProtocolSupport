package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleInventoryButton extends ServerBoundMiddlePacket {

	protected MiddleInventoryButton(MiddlePacketInit init) {
		super(init);
	}

	protected int windowId;
	protected int button;

	@Override
	protected void write() {
		codec.writeServerbound(create(windowId, button));
	}

	public static ServerBoundPacketData create(int windowId, int enchantment) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_ENCHANT_SELECT);
		creator.writeByte(windowId);
		creator.writeByte(enchantment);
		return creator;
	}

}
