package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

public abstract class MiddleEntityLeash extends ClientBoundMiddlePacket {

	protected MiddleEntityLeash(MiddlePacketInit init) {
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
