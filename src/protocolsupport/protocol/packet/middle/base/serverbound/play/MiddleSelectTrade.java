package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleSelectTrade extends ServerBoundMiddlePacket {

	protected MiddleSelectTrade(IMiddlePacketInit init) {
		super(init);
	}

	protected int slot;

	@Override
	protected void write() {
		io.writeServerbound(create(slot));
	}

	public static ServerBoundPacketData create(int slot) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_SELECT_TRADE);
		VarNumberCodec.writeVarInt(creator, slot);
		return creator;
	}

}
