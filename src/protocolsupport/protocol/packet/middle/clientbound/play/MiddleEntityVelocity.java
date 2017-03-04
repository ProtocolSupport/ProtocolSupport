package protocolsupport.protocol.packet.middle.clientbound.play;

import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleEntityVelocity extends MiddleEntity {

	protected int motX;
	protected int motY;
	protected int motZ;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) {
		super.readFromServerData(serializer);
		motX = serializer.readShort();
		motY = serializer.readShort();
		motZ = serializer.readShort();
	}

}
