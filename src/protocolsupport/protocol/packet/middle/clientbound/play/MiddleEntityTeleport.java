package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleEntityTeleport<T> extends MiddleEntity<T> {

	protected double x;
	protected double y;
	protected double z;
	protected byte yaw;
	protected byte pitch;
	protected boolean onGround;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		super.readFromServerData(serializer);
		x = serializer.readDouble();
		y = serializer.readDouble();
		z = serializer.readDouble();
		yaw = serializer.readByte();
		pitch = serializer.readByte();
		onGround = serializer.readBoolean();
	}

}
