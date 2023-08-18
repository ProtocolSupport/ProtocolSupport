package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;

public abstract class MiddleEntityLeash extends MiddleEntityData {

	protected MiddleEntityLeash(IMiddlePacketInit init) {
		super(init);
	}

	protected int vehicleId;

	@Override
	protected int decodeEntityId(ByteBuf serverdata) {
		return serverdata.readInt();
	}

	@Override
	protected void decodeData(ByteBuf serverdata) {
		vehicleId = serverdata.readInt();
	}

}
