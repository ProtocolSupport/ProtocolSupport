package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleEntityRelMoveLook<T> extends MiddleEntity<T> {

	protected int relX;
	protected int relY;
	protected int relZ;
	protected int yaw;
	protected int pitch;
	protected boolean onGround;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		super.readFromServerData(serializer);
		relX = serializer.readShort();
		relY = serializer.readShort();
		relZ = serializer.readShort();
		yaw = serializer.readByte();
		pitch = serializer.readByte();
		onGround = serializer.readBoolean();
	}

}
