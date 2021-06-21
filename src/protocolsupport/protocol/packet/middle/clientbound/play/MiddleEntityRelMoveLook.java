package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.types.networkentity.NetworkEntityDataCache;

public abstract class MiddleEntityRelMoveLook extends MiddleEntityData {

	protected MiddleEntityRelMoveLook(MiddlePacketInit init) {
		super(init);
	}

	protected short relX;
	protected short relY;
	protected short relZ;
	protected byte yaw;
	protected byte pitch;
	protected boolean onGround;

	@Override
	protected void decodeData(ByteBuf serverdata) {
		relX = serverdata.readShort();
		relY = serverdata.readShort();
		relZ = serverdata.readShort();
		yaw = serverdata.readByte();
		pitch = serverdata.readByte();
		onGround = serverdata.readBoolean();
	}

	@Override
	protected void handle() {
		NetworkEntityDataCache ecache = entity.getDataCache();
		ecache.addLocation(relX, relY, relZ);
		ecache.setLook(pitch, yaw);
	}

}
