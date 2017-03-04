package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;

//TODO: Find out how and when it is used
public abstract class MiddleVehicleMove extends ClientBoundMiddlePacket {

	protected double x;
	protected double y;
	protected double z;
	protected float yaw;
	protected float pitch;

	@Override
	public void readFromServerData(ByteBuf serverdata) {
		x = serverdata.readDouble();
		y = serverdata.readDouble();
		z = serverdata.readDouble();
		yaw = serverdata.readFloat();
		pitch = serverdata.readFloat();
	}

}
