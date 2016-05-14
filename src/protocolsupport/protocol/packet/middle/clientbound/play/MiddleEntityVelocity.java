package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleEntityVelocity<T> extends MiddleEntity<T> {

	protected int motX;
	protected int motY;
	protected int motZ;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		super.readFromServerData(serializer);
		motX = serializer.readShort();
		motY = serializer.readShort();
		motZ = serializer.readShort();
	}

}
