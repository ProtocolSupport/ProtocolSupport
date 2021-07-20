package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;

public abstract class MiddleEntityRelMove extends MiddleEntityData {

	protected MiddleEntityRelMove(MiddlePacketInit init) {
		super(init);
	}

	protected short relX;
	protected short relY;
	protected short relZ;
	protected boolean onGround;

	@Override
	protected void decodeData(ByteBuf serverdata) {
		relX = serverdata.readShort();
		relY = serverdata.readShort();
		relZ = serverdata.readShort();
		onGround = serverdata.readBoolean();
	}

	@Override
	protected void decodeDataLast(ByteBuf serverdata) {
		entity.getDataCache().addLocation(relX, relY, relZ);
	}

}
