package protocolsupport.protocol.packet.middle.base.clientbound.play;

import io.netty.buffer.ByteBuf;

public abstract class MiddleEntityHeadRotation extends MiddleEntityData {

	protected MiddleEntityHeadRotation(IMiddlePacketInit init) {
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
