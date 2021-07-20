package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;

public abstract class MiddleEntityHeadRotation extends MiddleEntityData {

	protected MiddleEntityHeadRotation(MiddlePacketInit init) {
		super(init);
	}

	protected byte headRot;

	@Override
	protected void decodeData(ByteBuf serverdata) {
		headRot = serverdata.readByte();
	}

	@Override
	protected void decodeDataLast(ByteBuf serverdata) {
		entity.getDataCache().setHeadYaw(headRot);
	}

}
