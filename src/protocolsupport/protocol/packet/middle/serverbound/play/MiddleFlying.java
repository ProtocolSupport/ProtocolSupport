package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleFlying extends ServerBoundMiddlePacket {

	public MiddleFlying(MiddlePacketInit init) {
		super(init);
	}

	protected boolean onGround;

	@Override
	protected void write() {
		ServerBoundPacketData flying = ServerBoundPacketData.create(PacketType.SERVERBOUND_PLAY_PLAYER);
		flying.writeBoolean(onGround);
		codec.writeServerbound(flying);
	}

}
