package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleInventoryButton extends ServerBoundMiddlePacket {

	protected MiddleInventoryButton(IMiddlePacketInit init) {
		super(init);
	}

	protected int windowId;
	protected int button;

	@Override
	protected void write() {
		io.writeServerbound(create(windowId, button));
	}

	public static ServerBoundPacketData create(int windowId, int enchantment) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_ENCHANT_SELECT);
		creator.writeByte(windowId);
		creator.writeByte(enchantment);
		return creator;
	}

}
