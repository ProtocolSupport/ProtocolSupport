package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;

//TODO: enum for status id
public abstract class MiddleEntityStatus extends MiddleEntityData {

	protected MiddleEntityStatus(MiddlePacketInit init) {
		super(init);
	}

	protected static final int STATUS_LIVING_DEATH = 3;

	protected int status;

	@Override
	protected int decodeEntityId(ByteBuf serverdata) {
		return serverdata.readInt();
	}

	@Override
	protected void decodeData(ByteBuf serverdata) {
		status = serverdata.readUnsignedByte();
	}

}
