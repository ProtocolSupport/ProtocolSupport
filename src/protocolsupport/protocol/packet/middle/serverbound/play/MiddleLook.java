package protocolsupport.protocol.packet.middle.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.ServerBoundMiddlePacket;
import protocolsupport.protocol.packet.middleimpl.ServerBoundPacketData;

public abstract class MiddleLook extends ServerBoundMiddlePacket {

	protected MiddleLook(MiddlePacketInit init) {
		super(init);
	}

	protected float yaw;
	protected float pitch;
	protected boolean onGround;

	@Override
	protected void write() {
		codec.writeServerbound(create(yaw, pitch, onGround));
	}

	public static ServerBoundPacketData create(float yaw, float pitch, boolean onGround) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.SERVERBOUND_PLAY_LOOK);
		creator.writeFloat(yaw);
		creator.writeFloat(pitch);
		creator.writeBoolean(onGround);
		return creator;
	}

}
