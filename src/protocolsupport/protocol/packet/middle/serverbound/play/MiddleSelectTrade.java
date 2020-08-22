package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleSelectTrade extends ServerBoundMiddlePacket {

	public MiddleSelectTrade(MiddlePacketInit init) {
		super(init);
	}

	protected int slot;

	@Override
	protected void writeToServer() {
		codec.read(create(slot));
	}

	public static ServerBoundPacketData create(int slot) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_SELECT_TRADE);
		VarNumberSerializer.writeVarInt(creator, slot);
		return creator;
	}

}
