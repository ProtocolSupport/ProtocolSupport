package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleSelectTrade extends ServerBoundMiddlePacket {

	protected MiddleSelectTrade(MiddlePacketInit init) {
		super(init);
	}

	protected int slot;

	@Override
	protected void write() {
		codec.writeServerbound(create(slot));
	}

	public static ServerBoundPacketData create(int slot) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_SELECT_TRADE);
		VarNumberCodec.writeVarInt(creator, slot);
		return creator;
	}

}
