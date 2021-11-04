package protocolsupport.protocol.packet.middle.base.serverbound.play;

import protocolsupport.protocol.packet.ServerBoundPacketData;
import protocolsupport.protocol.packet.ServerBoundPacketType;
import protocolsupport.protocol.packet.middle.base.serverbound.ServerBoundMiddlePacket;

public abstract class MiddleLook extends ServerBoundMiddlePacket {

	protected MiddleLook(IMiddlePacketInit init) {
		super(init);
	}

	protected float yaw;
	protected float pitch;
	protected boolean onGround;

	@Override
	protected void write() {
		io.writeServerbound(create(yaw, pitch, onGround));
	}

	public static ServerBoundPacketData create(float yaw, float pitch, boolean onGround) {
		ServerBoundPacketData creator = ServerBoundPacketData.create(ServerBoundPacketType.PLAY_LOOK);
		creator.writeFloat(yaw);
		creator.writeFloat(pitch);
		creator.writeBoolean(onGround);
		return creator;
	}

}
