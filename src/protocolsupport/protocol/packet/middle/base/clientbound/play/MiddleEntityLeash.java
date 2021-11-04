package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.base.clientbound.ClientBoundMiddlePacket;

public abstract class MiddleEntityLeash extends ClientBoundMiddlePacket {

	protected MiddleEntityLeash(IMiddlePacketInit init) {
		super(init);
	}

	protected int entityId;
	protected int vehicleId;

	@Override
	protected void decode(ByteBuf serverdata) {
		entityId = serverdata.readInt();
		vehicleId = serverdata.readInt();
	}

}
