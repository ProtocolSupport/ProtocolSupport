package protocolsupport.protocol.packet.middle.clientbound.play;

import java.io.IOException;

import protocolsupport.protocol.serializer.ProtocolSupportPacketDataSerializer;

public abstract class MiddleEntityRelMove<T> extends MiddleEntity<T> {

	protected int relX;
	protected int relY;
	protected int relZ;
	protected boolean onGround;

	@Override
	public void readFromServerData(ProtocolSupportPacketDataSerializer serializer) throws IOException {
		super.readFromServerData(serializer);
		relX = serializer.readShort();
		relY = serializer.readShort();
		relZ = serializer.readShort();
		onGround = serializer.readBoolean();
	}

}
