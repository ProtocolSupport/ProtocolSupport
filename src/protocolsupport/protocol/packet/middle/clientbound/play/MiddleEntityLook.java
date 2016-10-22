package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleEntityLook<T> extends MiddleEntity<T> {

	protected int yaw;
	protected int pitch;
	protected boolean onGround;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		super.readFromServerData(serializer);
		yaw = serializer.readByte();
		pitch = serializer.readByte();
		onGround = serializer.readBoolean();
	}

}
